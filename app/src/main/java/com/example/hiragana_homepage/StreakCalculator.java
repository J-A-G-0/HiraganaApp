package com.example.hiragana_homepage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Class for checking/calculating streaks and interacting with SharedPreferences.
 *
 * @author joelgodfrey
 */

public class StreakCalculator {

    private LocalDate dateToday;
    private DateTimeFormatter myFormatObj;
    private SharedPreferencesHandler sharedPreferencesHandler;

    public UserProgressHandler getUserProgressHandler() {
        return userProgressHandler;
    }

    public void setUserProgressHandler(UserProgressHandler userProgressHandler) {
        this.userProgressHandler = userProgressHandler;
    }

    private UserProgressHandler userProgressHandler;


    public LocalDate getDateToday() {
        return dateToday;
    }


    public DateTimeFormatter getMyFormatObj() {
        return myFormatObj;
    }

    public void setSharedPreferencesHandler(SharedPreferencesHandler sharedPreferencesHandler) {
        this.sharedPreferencesHandler = sharedPreferencesHandler;
    }

    // Prepare StreakCalculator obj for most situations.
    public void setupCalc(SharedPreferencesHandler up, UserProgressHandler user_prog_handler) {
        setUserProgressHandler(user_prog_handler);
        setSharedPreferencesHandler(up);
        updateDateToday();
        createDateFormatter();
    }

    // Set dateToday constructor as today's date.
    public void updateDateToday() {
        dateToday = LocalDate.now();
    }

    // Setup myFormatObj pattern.
    public void createDateFormatter() {
        myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
    }

    // Return true if the user has a deadline they have set.
    public boolean checkDeadlineSet() {
        return sharedPreferencesHandler.getSp().getBoolean("deadlineSet", false);
    }

    // Returns true if the user has a streak target they have set.
    public boolean checkStreakTargetSet() {
        return sharedPreferencesHandler.getSp().getBoolean("streakTargetSet", false);
    }

    // Returns the total number of the 46 Hiragana that the user has studied.
    public int get_num_hiragana_studied() {
        return userProgressHandler.getUserProgArrayLst().size();
    }

    // Returns the number of days the user set for their streak as an int.
    public int getStreakTarget() {
        return sharedPreferencesHandler.getSp().getInt("streakTargetVal", 999);
    }

    // Returns the user's current streak length as an int.
    public int getCurrentStreakLength() {
        return sharedPreferencesHandler.getSp().getInt("currentStreak", 998);
    }

    // Returns number of days until user's streak target reached as an int.
    public int getDaysLeftToStreakTarget() {
        int target = getStreakTarget();
        int currentStreak = getCurrentStreakLength();
        return target - currentStreak;
    }

    // Returns the number of days until user's deadline as a long.
    public long getDaysLeftToDeadlineTarget() {
        // First, we retrieve the str version of the date from SP().
        String strDeadlineDate = sharedPreferencesHandler.getSp()
                .getString("deadlineDate", "Err not found");
        // Next we convert the SP str of the date back to a LocalDate object.
        LocalDate deadlineDate = LocalDate.parse(strDeadlineDate, myFormatObj);
        return ChronoUnit.DAYS.between(dateToday, deadlineDate);
    }

}
