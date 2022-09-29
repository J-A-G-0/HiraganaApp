package com.example.hiragana_homepage;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

/**
 * Activity class for the Homescren.
 *
 * @author joelgodfrey
 */

public class HomeScreenActivity extends AppCompatActivity {

    private Button btn_next_lesson;
    private Button btn_review;
    private ImageButton btn_stats;
    private ImageButton btn_info;
    private ImageButton btn_home;

    private Button popup_btn_lesson;
    private Button popup_btn_home;
    private TextView popup_tv_advice;

    private LinearLayout linearLayout;
    private LinearLayout reviewLayout;
    private ImageView imv_header;

    private final SharedPreferencesHandler sharedPreferencesHandler =
            new SharedPreferencesHandler(this);
    private final HiraganaInitialiser hiraganaInitialiser = new HiraganaInitialiser(this);
    private final UserProgressHandler userProgressHandler = new UserProgressHandler();

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana_homepage);

        btn_next_lesson = (Button) findViewById(R.id.btn_next_lesson);
        btn_review = (Button) findViewById(R.id.btn_review);
        btn_stats = (ImageButton) findViewById(R.id.button_stats);
        btn_info = (ImageButton) findViewById(R.id.button_info);
        btn_home = (ImageButton) findViewById(R.id.button_home);
        linearLayout = (LinearLayout) findViewById(R.id.nextStudy_view);
        reviewLayout = (LinearLayout) findViewById(R.id.review_view);

        btn_stats.setOnClickListener(handleButtons);
        btn_info.setOnClickListener(handleButtons);
        btn_home.setOnClickListener(handleButtons);
        btn_next_lesson.setOnClickListener(handleButtons);
        btn_review.setOnClickListener(handleButtons);

        hiraganaInitialiser.setCompleteHiraganaArrayList(hiraganaInitialiser
                .getCompletedHiraganaArrayListFromJSON());

        // Set up the SP object.
        sharedPreferencesHandler.setUpSp();
        userProgressHandler.initialiseAll(sharedPreferencesHandler.getAllFromSP(),
                hiraganaInitialiser.getCompleteHiraganaArrayList());



        // Hide the Review button until user has completed a lesson.
        if(userProgressHandler.getUserProgArrayLst().size() < 4) {
            btn_review.setVisibility(View.GONE);
            reviewLayout.setVisibility(View.GONE);
        } else {
            btn_review.setVisibility(View.VISIBLE);
            reviewLayout.setVisibility(View.VISIBLE);
        }


        if(getNumberOfHiraganaStudied(userProgressHandler) == 46) {
            // Set the 'next lesson' button to disappear once the user has studied all 46 Hiragana.
            btn_next_lesson.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }

    }


    /**
     * Method to handle the function of the non-flashcard buttons.
     */

    private final View.OnClickListener handleButtons = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            switch (view.getId()) {
                case R.id.button_stats:
                    Intent intent = new Intent(HomeScreenActivity.this,
                            StatsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.button_info:
                    Intent intent_info = new Intent(HomeScreenActivity.this,
                            InfoActivity.class);
                    startActivity(intent_info);
                    break;
                case R.id.button_home:
                    Intent intent_home = new Intent(HomeScreenActivity.this,
                            MainActivity.class);
                    startActivity(intent_home);
                    break;
                case R.id.btn_next_lesson:
                    Intent intent_lesson = new Intent(HomeScreenActivity.this,
                            Lesson_Activity.class);
                    //Check if the user has not been previously warned about study style.
                    if(userHasNotBeenWarnedAboutStudyStyle()) {
                        // If lessonssincereview > 2, create an advice popup. Prevent future popups.
                        if(numberOfLessonsSinceReview() > 2)
                        {
                            sharedPreferencesHandler.getEditor().putBoolean
                                    ("userHasBeenWarnedAboutStudyStyle", true);
                            sharedPreferencesHandler.getEditor().apply();
                            createWarningDialog();
                            // Put the dialog for the popup here.
                        } else {
                            // Update the lesson count in SP() and load lesson.
                            sharedPreferencesHandler.getEditor().putInt(
                                    "numberOfLessonsWithoutReview", numberOfLessonsSinceReview());
                            sharedPreferencesHandler.getEditor().apply();
                            startActivity(intent_lesson);
                        }
                    } else {
                        // If user has been previously warned, start lesson.
                        startActivity(intent_lesson);
                    }
                    break;
                case R.id.btn_review:
                    Intent intent_review = new Intent(HomeScreenActivity.this,
                            ReviewActivity.class);
                    if(userHasNotBeenWarnedAboutStudyStyle()) {
                        // Reset the lessons without review count.
                        sharedPreferencesHandler.getEditor().putInt
                                ("numberOfLessonsWithoutReview", 0);
                        sharedPreferencesHandler.getEditor().apply();
                    }
                    startActivity(intent_review);
                    break;
            }
        }
    };

    /**
     * Checks if user has previously been warned about study style.
     * @return boolean
     */
    public boolean userHasNotBeenWarnedAboutStudyStyle() {
        return !sharedPreferencesHandler.getSp().getBoolean
                ("userHasBeenWarnedAboutStudyStyle", false);
    }

    /**
     * Checks number of lessons since last review.
     * Returns 999 if not found.
     * @return int
     */
    public int numberOfLessonsSinceReview() {
        int consecutiveLessonsWithoutReview = sharedPreferencesHandler.getSp()
                .getInt("numberOfLessonsWithoutReview", 999);
        consecutiveLessonsWithoutReview++;
        return consecutiveLessonsWithoutReview;
    }


    /**
     * Creates a warning dialogue about inefficient study.
     */
    public void createWarningDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View warningPopup = getLayoutInflater().inflate(R.layout.popup_warning, null);

        popup_tv_advice = (TextView) warningPopup.findViewById(R.id.tv_advice);
        popup_btn_lesson = (Button) warningPopup.findViewById(R.id.btn_continue_to_lesson);
        popup_btn_home = (Button) warningPopup.findViewById(R.id.btn_return_home);

        popup_tv_advice.setText(R.string.review_advice);

        popup_btn_lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity.this,
                        Lesson_Activity.class);
                startActivity(intent);
            }
        });

        popup_btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity.this,
                        HomeScreenActivity.class);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(warningPopup);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    /**
     * Method to handle loading the correct flashcard when a flaschard button is clicked.
     * @param v
     */
    public void onClickLoadFlashcard(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        Intent intent = new Intent(HomeScreenActivity.this,
                HiraganaViewerActivity.class);
        intent.putExtra("hiragana_char", buttonText);
        startActivity(intent);
    }

    /**
     * Get number of Hiragana studied.
     * @param user_prog
     * @return
     */
    public int getNumberOfHiraganaStudied(UserProgressHandler user_prog){
        return user_prog.getUserProgArrayLst().size();
    }

}



