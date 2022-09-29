package com.example.hiragana_homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

/**
 * MainActivity. Displays key stat info, and initialises first time users.
 *
 * @author joelgodfrey
 */

public class MainActivity extends AppCompatActivity {

    private TextView tv_num_studied;
    private TextView tv_current_streak;
    private TextView tv_days_to_goal;
    boolean firstStart;

    private final SharedPreferencesHandler sharedPreferencesHandler =
            new SharedPreferencesHandler(this);
    private final HiraganaInitialiser hiraganaInitialiser = new HiraganaInitialiser(this);
    private final UserProgressHandler userProgressHandler = new UserProgressHandler();
    private final StreakCalculator calc = new StreakCalculator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Complete first Time user setup.
        sharedPreferencesHandler.setUpSp();
        hiraganaInitialiser.setCompleteHiraganaArrayList(
                hiraganaInitialiser.getCompletedHiraganaArrayListFromJSON());

        // Checks if this is first opening of the app, by searching for firstStart boolean in SP().
        firstStart = sharedPreferencesHandler.getSp().getBoolean("firstStart", true);

        // Set up calc, assigning Up, getting date today and creating date formatter.
        calc.setupCalc(sharedPreferencesHandler, userProgressHandler);

        tv_num_studied = (TextView) findViewById(R.id.tv_hiragana_studied_num);
        tv_current_streak = (TextView) findViewById(R.id.tv_current_streak_num);
        tv_days_to_goal = (TextView) findViewById(R.id.tv_days_to_goal_num);

        // here we initialise the values for a first time user.
        if (firstStart) {
            setUpFirstTimeUser(sharedPreferencesHandler, hiraganaInitialiser);
        }

        // Initialise UserprogressHandler
        userProgressHandler.initialiseAll(sharedPreferencesHandler.getAllFromSP(),
                hiraganaInitialiser.getCompleteHiraganaArrayList());


        // Set figures in SP for streaks.
        setUpStreaks(sharedPreferencesHandler);
        // Check whether a streak target has been set, and if it has update the TOAST.
        checkStreakSet(sharedPreferencesHandler);
        // Chek whether a deadline target has been set, and if it has update the TOAST.
        checkDeadline(sharedPreferencesHandler);
        // Updates all textviews to match user's progress.
        setAllTextViews(sharedPreferencesHandler);


        Button btn_start_learning = (Button) findViewById(R.id.goToHiraganaHomepage);
        btn_start_learning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        HomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }



    public void checkDeadline(SharedPreferencesHandler up) {
        if(calc.checkDeadlineSet()) {
            daysUntilDeadlineAlert(up);
        }
    }

    //
    public void checkStreakSet(SharedPreferencesHandler up) {
        if(calc.checkStreakTargetSet()) {
            checkUserStreakMet();
        }
    }

    public void setAllTextViews(SharedPreferencesHandler up) {
        // Set the current num studied textview.
        tv_num_studied.setText(String.valueOf(calc.get_num_hiragana_studied()));

        // Set the current streak textview.
        tv_current_streak.setText(String.valueOf(calc.getCurrentStreakLength()));

        // Set the deadline textview.
        if(calc.checkDeadlineSet()) {
            setupDeadlineTexview();
        }
    }

    public void setupDeadlineTexview() {
        tv_days_to_goal.setText(String.valueOf(calc.getDaysLeftToDeadlineTarget()));
    }

    /**
     * Method to create the required entries in SP for a first time user.
     * @param sharedPreferencesHandler
     * @param initialiser
     */
    public void setUpFirstTimeUser(SharedPreferencesHandler sharedPreferencesHandler,
                                   HiraganaInitialiser initialiser) {
        //for (Hiragana_character h : up.getArrayLstHiragana()) {
        for(Hiragana_character h : initialiser.getCompleteHiraganaArrayList()) {
            sharedPreferencesHandler.getEditor().putString(h.getLatinCharacter(), "0");
        }
        sharedPreferencesHandler.getEditor().putBoolean("firstStart", false);

        // Get date today from Calc.
        // Turn into a str named 'strMostRecentDate' using the formatter obj created in onCreate().
        String strMostRecentDate = calc.getDateToday().format(calc.getMyFormatObj());
        // Add strMostRecentDate as a KV in SP.
        sharedPreferencesHandler.getEditor().putString("mostRecentDate", strMostRecentDate);
        // Initialise current streak as 1.
        sharedPreferencesHandler.getEditor().putInt("currentStreak", 1);
        // Put a bool for whether they have set themselves a daily streak.
        sharedPreferencesHandler.getEditor().putBoolean("streakTargetSet", false);
        // Put a bool for whether they've set themselves a deadline to complete all 46 Hiragana by.
        sharedPreferencesHandler.getEditor().putBoolean("deadlineSet", false);
        // Put a bool for whether user has been alerted to inefficient study.
        sharedPreferencesHandler.getEditor().putBoolean("userHasBeenWarnedAboutStudyStyle", false);
        // Put a counter for number of Lessons completed consecutively without completing a Review.
        sharedPreferencesHandler.getEditor().putInt("numberOfLessonsWithoutReview", 0);
        sharedPreferencesHandler.getEditor().apply();

        Intent intent = new Intent(MainActivity.this, FirstLogonActivity.class);
        startActivity(intent);
    }

    /**
     * Method to update streaks.
     * @param up
     */
    public void setUpStreaks(SharedPreferencesHandler up) {
        // First, we need to convert the SP str of the date back to a date object.
        LocalDate mostRecentDate = LocalDate.parse(up.getSp().getString
                ("mostRecentDate", "failed"), calc.getMyFormatObj());
        //Here we update the mostRecentDate.
        String strDateToday = calc.getDateToday().format(calc.getMyFormatObj());
        up.getEditor().putString("mostRecentDate", strDateToday);
        up.getEditor().apply();

        // Check if the most recent date recorded in SP is the same as today's date. If different:
        if(!mostRecentDate.equals(calc.getDateToday())) {
            if(mostRecentDate.plusDays(1).equals(calc.getDateToday())){
                int newStreak = up.getSp().getInt("currentStreak", 999);
                newStreak++;
                up.getEditor().putInt("currentStreak", newStreak);
            }
            //If not different:
            else {
                up.getEditor().putInt("currentStreak", 1);
            }
            up.getEditor().apply();
        }
    }

    /**
     * Class to check if the user has met their streak goal.
     */
    public void checkUserStreakMet() {
        // Set Encouragement message for Toast. Prints when user is on their way to their goal.
        String keep_at_it = "Keep at it! Only " + calc.getDaysLeftToStreakTarget()
                + " days to go to reach your streak goal!";
        if(calc.getStreakTarget() == calc.getCurrentStreakLength()){
            Toast.makeText
                    (MainActivity.this,
                            "Congratulations! You've reached your streak goal!  " +
                                    "Why not set another?", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, keep_at_it, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to create a Toast alterting user to days left / having reached their goal.
     * @param user_prog
     */
    // Method to make a Toast if the user has a goal set, alerting them to days left.
    public void daysUntilDeadlineAlert(SharedPreferencesHandler user_prog){
        // First, we retrieve the str version of the date from SP().
        String strDeadlineDate = user_prog.getSp().getString
                ("deadlineDate", "Err not found");
        // Next we convert the SP str of the date back to a LocalDate object.
        LocalDate deadlineDate = LocalDate.parse(strDeadlineDate, calc.getMyFormatObj());


        if(calc.getDateToday().isAfter(deadlineDate)){
            // Print a toast telling them they didn't meet deadline. Delete the deadline.
            Toast.makeText(MainActivity.this,
                    "Unfortunately you've not made your study goal this time. " +
                            "Why not set a new one?",
                    Toast.LENGTH_SHORT).show();
            user_prog.getEditor().remove("deadlineDate");
            user_prog.getEditor().putBoolean("deadlineSet", false);
            user_prog.getEditor().apply();
        }
    }
}

