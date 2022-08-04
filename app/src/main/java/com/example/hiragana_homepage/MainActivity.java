package com.example.hiragana_homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.media.Image;
import android.renderscript.ScriptGroup;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserProgress up = new UserProgress(this);
        up.firstTimeUserSetUp();

        //Here we setup progress in SP for a first-time user.
        boolean firstStart = up.getSp().getBoolean("firstStart", true);


        /* ArrayList<String> firstFiveChars = new ArrayList<>();
        firstFiveChars.add("a");
        firstFiveChars.add("i");
        firstFiveChars.add("u");
        firstFiveChars.add("e");
        firstFiveChars.add("o");

         */

// here we initialise the values for a first time user. So the first 5 chars are all 0, others are 1.
        if (firstStart) {
            for (Hiragana_character h : up.getArrayLstHiragana()) {
                //if (firstFiveChars.contains(h.getLatinCharacter())) {
                    //up.getEditor().putString(h.getLatinCharacter(), "1");
                //} else {
                    up.getEditor().putString(h.getLatinCharacter(), "0");
                //}
            }
            up.getEditor().putBoolean("firstStart", false);

            // Check date now.
            LocalDate date = LocalDate.now();
            //Create a formatting obj to make it into a str.
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
            // Make it into a str named 'strMostRecentDate'
            String strMostRecentDate = date.format(myFormatObj);
            // Add strMostRecentDate as a KV in SP.
            up.getEditor().putString("mostRecentDate", strMostRecentDate);
            // Initialise current streak as 1.
            up.getEditor().putInt("currentStreak", 1);
            // Put a bool for whether they have set themselves a daily streak.
            up.getEditor().putBoolean("streakTargetSet", false);
            // Put a bool for whether they have set themselves a deadline to complete all 46 Hiragana by.
            up.getEditor().putBoolean("deadlineSet", false);
            up.getEditor().apply();

            Intent intent = new Intent(MainActivity.this, FirstLogonActivity.class);
            startActivity(intent);
        }

//Here we finish initialising the UP. This has to come after the first start bool, or a null ref error occurs.
        up.setUser_progress_map();
        up.setUpUserProgArrayLst();

        // Here is the stuff for recording streaks.
        // First, we need to convert the SP str of the date back to a date object.
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate mostRecentDate = LocalDate.parse(up.getSp().getString("mostRecentDate", "failed"), myFormatObj);
        LocalDate dateToday = LocalDate.now();

        //Here we update the mostRecentDate, since this needs to happen either way.
        String strDateToday = dateToday.format(myFormatObj);
        up.getEditor().putString("mostRecentDate", strDateToday);
        up.getEditor().apply();

        // We check if the most recent date recorded in SP is the same as today's date. If different:
        if(!mostRecentDate.equals(dateToday)) {
            if(mostRecentDate.plusDays(1).equals(dateToday)){
                int newStreak = up.getSp().getInt("currentStreak", 999);
                newStreak++;
                up.getEditor().putInt("currentStreak", newStreak);
            }
            else {
                up.getEditor().putInt("currentStreak", 1);
            }
            up.getEditor().apply();
        }
        // Check if there is a streak target set, and if so call checkUserStreakMet().
        if(up.getSp().getBoolean("streakTargetSet", false)) {
            checkUserStreakMet(up);
        }
        // Check if there is a deadline set, and if so call daysUntilDeadlneAlert().
        if(up.getSp().getBoolean("deadlineSet", false)){
            daysUntilDeadlineAlert(up);
        }


        Button button_2 = (Button) findViewById(R.id.goToHiraganaHomepage);
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HiraganaHomepage.class);
                startActivity(intent);
            }
        });
    }

    // Have an if statement to call this if the Boolean is true, rather than putting it inside.
    public void checkUserStreakMet(UserProgress user_prog) {
        int streakTarget = user_prog.getSp().getInt("streakTargetVal", 999);
        int currentStreak = user_prog.getSp().getInt("currentStreak", 998);
        int daysLeft = streakTarget - currentStreak;
        String keep_at_it = "Keep at it! Only " + String.valueOf(daysLeft) + " days to go to reach your streak goal!";
        if(streakTarget == currentStreak){
            Toast.makeText(MainActivity.this, "Congratulations! You've reached your streak goal!  Why not set another?", Toast.LENGTH_SHORT).show();
            // Rather than resetting the streak, user can simply select a higher number.
            // user_prog.getEditor().putInt("currentStreak", 1);
            //user_prog.getEditor().apply();
        }
        else {
            Toast.makeText(MainActivity.this, keep_at_it, Toast.LENGTH_SHORT).show();
        }
    }

    // Method to make a Toast if the user has a goal set, letting them know how many days they have left.
    public void daysUntilDeadlineAlert(UserProgress user_prog){
        // Remember to only call this if the bool is true!
        // First, we retrieve the str version of the date from SP().
        String strDeadlineDate = user_prog.getSp().getString("deadlineDate", "Err not found");
        // Next we convert the SP str of the date back to a LocalDate object.
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate deadlineDate = LocalDate.parse(strDeadlineDate, myFormatObj);
        LocalDate dateToday = LocalDate.now();
        if(dateToday.isAfter(deadlineDate)){
            // Print a toast telling them they didn't meet deadline. Delete the deadline.
            Toast.makeText(MainActivity.this, "Unfortunately you've not made your study goal this time. Why not set a new one?",
                    Toast.LENGTH_SHORT).show();
            user_prog.getEditor().remove("deadlineDate");
            user_prog.getEditor().putBoolean("deadlineSet", false);
            user_prog.getEditor().apply();

        }
        long daysBetween = ChronoUnit.DAYS.between(dateToday, deadlineDate);
        Toast.makeText(MainActivity.this, String.valueOf(daysBetween) + " days left to meet your study goal!", Toast.LENGTH_SHORT).show();
    }
}

