package com.example.hiragana_homepage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class StatsActivity extends AppCompatActivity {

    UserProgress up = new UserProgress(this);
    private Button btn_set_goal;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    int user_streak_target_val;
    int user_goal_val;

    public int getUser_streak_val() {
        return user_streak_target_val;
    }

    public void setUser_streak_val(int user_streak_val) {
        this.user_streak_target_val = user_streak_val;
    }

    public int getUser_goal_val() {
        return user_goal_val;
    }

    public void setUser_goal_val(int user_goal_val) {
        this.user_goal_val = user_goal_val;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        up.setUpUp();

        btn_set_goal = (Button) findViewById(R.id.btn_set_goal);
        btn_set_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        Button btn_progress_breakdown = (Button) findViewById(R.id.button_progress_breakdown);
        btn_progress_breakdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, UserProgressActivity.class);
                startActivity(intent);
            }
        });

        // Set the current streak textview.

        TextView tv_streak = (TextView) findViewById(R.id.tv_current_streak);
        int streakAsInt = up.getSp().getInt("currentStreak", 999);
        tv_streak.setText("Current Streak = " + String.valueOf(streakAsInt) + " days");


        // Set up the pair of textboxes for the user's streak goal and days left.

        TextView tv_streak_goal = (TextView) findViewById(R.id.tv_streak_goal);
        TextView tv_days_until_streak = (TextView) findViewById(R.id.tv_days_until_streak_goal);
        if(up.getSp().getBoolean("streakTargetSet", false)){

            // Set the streak goal textview.
            int streakGoalAsInt = up.getSp().getInt("streakTargetVal", 555);
            tv_streak_goal.setText("Goal = " + String.valueOf(streakGoalAsInt) + " days");

            // Calculate the days left until their streak goal and set the textview.
            int streakTarget = up.getSp().getInt("streakTargetVal", 999);
            int currentStreak = up.getSp().getInt("currentStreak", 998);
            int daysLeft = streakTarget - currentStreak;
            tv_days_until_streak.setText("Days To Go = " + String.valueOf(daysLeft));
        }
        else {
            tv_streak_goal.setText("No streak goal currently set!");
            tv_days_until_streak.setText("Set a new streak goal below!");

        }



        // Set up the second pair of textboxes, focused on the user's deadline goal.

        TextView tv_days_until_deadline = (TextView) findViewById(R.id.tv_days_until_deadline);
        TextView tv_current_progress = (TextView) findViewById(R.id.tv_current_progress);


        // Set the final textview to display progress / 46.

        int num_hiragana_studied = up.getUserProgArrayLst().size();
        tv_current_progress.setText("Hiragana Completion = " + String.valueOf(num_hiragana_studied) + " / 46");


        if(up.getSp().getBoolean("deadlineSet", false)) {
            // First, we retrieve the str version of the date from SP().
            String strDeadlineDate = up.getSp().getString("deadlineDate", "Err not found");
            // Next we convert the SP str of the date back to a LocalDate object.
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            LocalDate deadlineDate = LocalDate.parse(strDeadlineDate, myFormatObj);
            LocalDate dateToday = LocalDate.now();


            // Set the third textview to display days left until deadline.
            long daysBetween = ChronoUnit.DAYS.between(dateToday, deadlineDate);
            tv_days_until_deadline.setText("Days Until Deadline: " + String.valueOf(daysBetween));
        } else {
            tv_days_until_deadline.setText("No deadline currently set!");
            //tv_current_progress.setText("Set a new deadline below!");

        }

    }

    public void openDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View setGoalsPopupView = getLayoutInflater().inflate(R.layout.goals_popup, null);

        NumberPicker np_streak = (NumberPicker) setGoalsPopupView.findViewById(R.id.num_picker_streak);
        NumberPicker np_goal = (NumberPicker) setGoalsPopupView.findViewById(R.id.num_picker_goal);

        // Set the minimum streak to the user's current streak.
        np_streak.setMinValue(up.getSp().getInt("currentStreak", 66));
        np_streak.setMaxValue(99);
        np_goal.setMinValue(1);
        np_goal.setMaxValue(28);


        //Make these set a temp_val.
        // On save changes, this is saved into stone.
        // Doesn't matter if they spin it, because it'll only be if they press save that we start calling other methods.

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

    // Should the user's streak be reset to 1 here?
    public void updateUserStreakTarget() {
        up.getEditor().putBoolean("streakTargetSet", true);
        up.getEditor().putInt("streakTargetVal", user_streak_target_val);
        up.getEditor().apply();

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
        up.getEditor().putString("deadlineDate", strDeadlineDate);
        up.getEditor().putBoolean("deadlineSet", true);
        up.getEditor().apply();
    }
}