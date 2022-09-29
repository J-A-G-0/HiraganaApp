package com.example.hiragana_homepage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Activity Class for displaying the user's progress stats.
 *
 * @author joelgodfrey
 */
public class StatsActivity extends AppCompatActivity {

    private final SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(this);
    private final UserProgressHandler userProgressHandler = new UserProgressHandler();
    private final HiraganaInitialiser hiraganaInitialiser = new HiraganaInitialiser(this);
    private final StreakCalculator calc = new StreakCalculator();
    private Button btn_set_goal;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private int user_streak_target_val;
    private int user_goal_val;

    private TextView tv_streak;
    private Button btn_progress_breakdown;
    private ImageButton btn_back;
    private TextView tv_streak_goal;
    private TextView tv_days_until_streak;
    private TextView tv_days_until_deadline;
    private TextView tv_current_progress;

    public void setUser_streak_val(int user_streak_val) {
        this.user_streak_target_val = user_streak_val;
    }

    public void setUser_goal_val(int user_goal_val) {
        this.user_goal_val = user_goal_val;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        hiraganaInitialiser.setCompleteHiraganaArrayList(hiraganaInitialiser.getCompletedHiraganaArrayListFromJSON());
        sharedPreferencesHandler.setUpSp();
        userProgressHandler.initialiseAll(sharedPreferencesHandler.getAllFromSP(), hiraganaInitialiser.getCompleteHiraganaArrayList());
        calc.setupCalc(sharedPreferencesHandler, userProgressHandler);

        btn_set_goal = (Button) findViewById(R.id.btn_set_goal);
        btn_progress_breakdown = (Button) findViewById(R.id.button_progress_breakdown);
        btn_back = (ImageButton) findViewById(R.id.btn_back);

        tv_streak = (TextView) findViewById(R.id.tv_current_streak);
        tv_streak_goal = (TextView) findViewById(R.id.tv_streak_goal);
        tv_days_until_streak = (TextView) findViewById(R.id.tv_days_until_streak_goal);
        tv_days_until_deadline = (TextView) findViewById(R.id.tv_days_until_deadline);
        tv_current_progress = (TextView) findViewById(R.id.tv_current_progress);

        btn_set_goal.setOnClickListener(handleButtons);
        btn_progress_breakdown.setOnClickListener(handleButtons);
        btn_back.setOnClickListener(handleButtons);

        populateTextViews();

    }

    private void populateTextViews() {
        // Set the current streak TV.
        tv_streak.setText("Current Streak = " + calc.getCurrentStreakLength() + " days");

        // Set up the pair of textview for the user's streak goal and days left.
        if(calc.checkStreakTargetSet()) {
            tv_streak_goal.setText("Goal = " + calc.getStreakTarget() + " days");
            tv_days_until_streak.setText("Days To Go = " + calc.getDaysLeftToStreakTarget());
        }
        else {
            tv_streak_goal.setText("No streak goal currently set!");
            tv_days_until_streak.setText("Set a new streak goal below!");
        }

        // Set the current Hiragana completion textview.
        tv_current_progress.setText("Hiragana Completion = " + calc.get_num_hiragana_studied() + " / 46");

        // Set the Days Until Deadline textview.
        if(calc.checkDeadlineSet()) {
            tv_days_until_deadline.setText("Days Until Deadline: " + calc.getDaysLeftToDeadlineTarget());
        } else {
            tv_days_until_deadline.setText("No deadline currently set!");
        }
    }

    private final View.OnClickListener handleButtons = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            switch (view.getId()) {
                case R.id.btn_set_goal:
                    openDialog();
                    break;
                case R.id.button_progress_breakdown:
                    Intent intent = new Intent(StatsActivity.this, ProgressBreakdownActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_back:
                    Intent intent2 = new Intent(StatsActivity.this, HomeScreenActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    };

    public void openDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View setGoalsPopupView = getLayoutInflater().inflate(R.layout.popup_goals, null);

        NumberPicker np_streak = (NumberPicker) setGoalsPopupView.findViewById(R.id.num_picker_streak);
        NumberPicker np_goal = (NumberPicker) setGoalsPopupView.findViewById(R.id.num_picker_goal);

        // Set the minimum streak to the user's current streak.
        np_streak.setMinValue(sharedPreferencesHandler.getSp().getInt("currentStreak", 66));
        np_streak.setMaxValue(99);
        np_goal.setMinValue(1);
        np_goal.setMaxValue(28);


        np_streak.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                setUser_streak_val(i1);
            }
        });

        np_goal.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                setUser_goal_val(i1);
            }
        });

        Button btn_save_streak = (Button) setGoalsPopupView.findViewById(R.id.btn_save_streak);
        btn_save_streak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserStreakTarget();
            }
        });

        Button btn_save_goal = (Button) setGoalsPopupView.findViewById(R.id.btn_save_goal);
        btn_save_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserGoalsTarget();
            }
        });

        ImageButton btn_back = (ImageButton) setGoalsPopupView.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatsActivity.this, StatsActivity.class);
                startActivity(intent);
            }
        });

        dialogBuilder.setView(setGoalsPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    public void updateUserStreakTarget() {
        sharedPreferencesHandler.getEditor().putBoolean("streakTargetSet", true);
        sharedPreferencesHandler.getEditor().putInt("streakTargetVal", user_streak_target_val);
        sharedPreferencesHandler.getEditor().apply();

    }

    public void updateUserGoalsTarget() {
        // Retrieve the current date.
        LocalDate dateNow = LocalDate.now();
        // Add the number of days (cast to a long) to get the deadline date.
        LocalDate deadlineDate = dateNow.plusDays((long) user_goal_val);
        // Create a formatter obj so that deadlineDate can be stored in SP as a string.
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        // Format the deadLine date as a string.
        String strDeadlineDate = deadlineDate.format(myFormatObj);
        sharedPreferencesHandler.getEditor().putString("deadlineDate", strDeadlineDate);
        sharedPreferencesHandler.getEditor().putBoolean("deadlineSet", true);
        sharedPreferencesHandler.getEditor().apply();
    }
}