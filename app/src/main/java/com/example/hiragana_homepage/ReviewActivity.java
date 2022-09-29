package com.example.hiragana_homepage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Activity Class for review quizzes, testing users on characters they have already studied.
 * and updating their progress in 'Progress Breakdown' based on their results.
 *
 * @author joelgodfrey
 */
public class ReviewActivity extends AppCompatActivity {

    private String optionSelectedByUser = "";

    private Hiragana_character keyChar = new Hiragana_character();
    private Hiragana_character top_left_char = new Hiragana_character();
    private Hiragana_character top_right_char = new Hiragana_character();
    private Hiragana_character bottom_left_char = new Hiragana_character();
    private Hiragana_character bottom_right_char = new Hiragana_character();

    private ImageView top_left_img;
    private ImageView top_right_img;
    private ImageView bottom_left_img;
    private ImageView bottom_right_img;
    private TextView charBeingStudied;
    private TextView tv_question_num;

    private LinearLayout btn_top_left;
    private LinearLayout btn_top_right;
    private LinearLayout btn_bottom_left;
    private LinearLayout btn_bottom_right;

    private int questionCount = 0;
    private ArrayList<String> lstCorrectAnswers;
    private ArrayList<String> lstIncorrectAnswers;

    private final Handler mHandler = new Handler();
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button newWellDoneDialog_rtnButton;
    private TextView newWellDoneDialog_wellDoneTV;
    private TextView newWellDoneDialog_incorrect;

    private final SharedPreferencesHandler sharedPreferencesHandler =
            new SharedPreferencesHandler(this);
    private final HiraganaInitialiser hiraganaInitialiser = new HiraganaInitialiser(this);
    private final UserProgressHandler userProgressHandler = new UserProgressHandler();

    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        lstCorrectAnswers = new ArrayList<>();
        lstIncorrectAnswers = new ArrayList<>();


        final ImageView backBtn = findViewById(R.id.backBtn);

        btn_top_left = findViewById(R.id.top_left);
        btn_top_right = findViewById(R.id.top_right);
        btn_bottom_left = findViewById(R.id.bottom_left);
        btn_bottom_right = findViewById(R.id.bottom_right);

        bottom_right_img = findViewById(R.id.bottom_right_img);
        bottom_left_img = findViewById(R.id.bottom_left_img);
        top_right_img = findViewById(R.id.top_right_img);
        top_left_img = findViewById(R.id.top_left_img);

        charBeingStudied = findViewById(R.id.charBeingStudied);
        tv_question_num = findViewById(R.id.tv_q_count);
        final Button continue_button = findViewById(R.id.continueButton);

        hiraganaInitialiser.setCompleteHiraganaArrayList(hiraganaInitialiser
                .getCompletedHiraganaArrayListFromJSON());
        sharedPreferencesHandler.setUpSp();
        userProgressHandler.initialiseAll(sharedPreferencesHandler.getAllFromSP(),
                hiraganaInitialiser.getCompleteHiraganaArrayList());


        btn_top_left.setOnClickListener(highlightCorrectAnswer);
        btn_bottom_left.setOnClickListener(highlightCorrectAnswer);
        btn_top_right.setOnClickListener(highlightCorrectAnswer);
        btn_bottom_right.setOnClickListener(highlightCorrectAnswer);


        //Setup the back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviewActivity.this,
                        HomeScreenActivity.class));
                finish();
            }
        });

        //The isEmpty is top stop people clicking continue and increasing the questionCount.
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleContinue(v);
            }
        });

        //Here, we populate everything the first time for the quiz.
        setUpImages(top_left_img, top_right_img, bottom_left_img, bottom_right_img,
                charBeingStudied);
    }

    private void handleContinue(View v) {
        if(!optionSelectedByUser.isEmpty()) {
            // These lines prevent the user clicking several times and skipping characters.
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1200){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
            checkCorrect();
            revealAnswer(btn_top_left, btn_top_right, btn_bottom_left, btn_bottom_right);
            mHandler.postDelayed(mResetPage, 1000);
        }
    }


    private void setUpImages(ImageView iv_1, ImageView iv_2, ImageView iv_3,
                             ImageView iv_4, TextView tv){
        Randomiser rando = new Randomiser();
        ArrayList fourNumbers;
        fourNumbers = rando.generateNumbers(userProgressHandler.getUserProgArrayLst());
        Hiragana_character key_c = rando.generateKeyChar(fourNumbers);
        setKeyChar(key_c);
        iv_1.setImageResource(((Hiragana_character) fourNumbers.get(0)).getThumbnail());
        iv_2.setImageResource(((Hiragana_character) fourNumbers.get(1)).getThumbnail());
        iv_3.setImageResource(((Hiragana_character) fourNumbers.get(2)).getThumbnail());
        iv_4.setImageResource(((Hiragana_character) fourNumbers.get(3)).getThumbnail());
        top_left_char= (Hiragana_character) fourNumbers.get(0);
        top_right_char=(Hiragana_character) fourNumbers.get(1);
        bottom_left_char=(Hiragana_character) fourNumbers.get(2);
        bottom_right_char=(Hiragana_character) fourNumbers.get(3);
        tv.setText(key_c.getLatinCharacter());
        optionSelectedByUser = "";
        questionCount++;
    }


    private final View.OnClickListener highlightCorrectAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.top_left:
                    btn_top_left.setBackgroundResource(R.drawable.rounded_back_white_stroke);
                    btn_top_right.setBackgroundResource(R.drawable.round_back_white_10);
                    btn_bottom_left.setBackgroundResource(R.drawable.round_back_white_10);
                    btn_bottom_right.setBackgroundResource(R.drawable.round_back_white_10);
                    optionSelectedByUser = top_left_char.getLatinCharacter();
                    break;
                case R.id.top_right:
                    btn_top_right.setBackgroundResource(R.drawable.rounded_back_white_stroke);
                    btn_top_left.setBackgroundResource(R.drawable.round_back_white_10);
                    btn_bottom_left.setBackgroundResource(R.drawable.round_back_white_10);
                    btn_bottom_right.setBackgroundResource(R.drawable.round_back_white_10);
                    optionSelectedByUser = top_right_char.getLatinCharacter();
                    break;
                case R.id.bottom_left:
                    btn_bottom_left.setBackgroundResource(R.drawable.rounded_back_white_stroke);
                    btn_top_right.setBackgroundResource(R.drawable.round_back_white_10);
                    btn_top_left.setBackgroundResource(R.drawable.round_back_white_10);
                    btn_bottom_right.setBackgroundResource(R.drawable.round_back_white_10);
                    optionSelectedByUser = bottom_left_char.getLatinCharacter();
                    break;
                case R.id.bottom_right:
                    btn_bottom_right.setBackgroundResource(R.drawable.rounded_back_white_stroke);
                    btn_top_right.setBackgroundResource(R.drawable.round_back_white_10);
                    btn_bottom_left.setBackgroundResource(R.drawable.round_back_white_10);
                    btn_top_left.setBackgroundResource(R.drawable.round_back_white_10);
                    optionSelectedByUser = bottom_right_char.getLatinCharacter();
                    break;
            }
        }
    };


    private final Runnable mResetPage = new Runnable() {
        @Override
        public void run() {
            if (questionCount < 10) {
                setUpImages(top_left_img, top_right_img, bottom_left_img,
                        bottom_right_img, charBeingStudied);
                setQuestionCount(tv_question_num);
                resetViews();
            } else {
                createNewWellDoneDialog();
            }
        }
    };

    private void revealAnswer(LinearLayout topLeft, LinearLayout topRight,
                              LinearLayout bottomLeft, LinearLayout bottomRight){
        int correctGreen = Color.parseColor("#00C853");
        ColorFilter correctGreenFilter = new PorterDuffColorFilter(correctGreen,
                PorterDuff.Mode.MULTIPLY);
        if(top_left_char.getLatinCharacter().equals(keyChar.getLatinCharacter())){
            topLeft.setBackgroundResource(R.drawable.round_back_green10);
            top_left_img.setColorFilter(correctGreenFilter);
        }
        else if(top_right_char.getLatinCharacter().equals(keyChar.getLatinCharacter())){
            topRight.setBackgroundResource(R.drawable.round_back_green10);
            top_right_img.setColorFilter(correctGreenFilter);
        }
        else if(bottom_left_char.getLatinCharacter().equals(keyChar.getLatinCharacter())){
            bottomLeft.setBackgroundResource(R.drawable.round_back_green10);
            bottom_left_img.setColorFilter(correctGreenFilter);
        }
        else if(bottom_right_char.getLatinCharacter().equals(keyChar.getLatinCharacter())){
            bottomRight.setBackgroundResource(R.drawable.round_back_green10);
            bottom_right_img.setColorFilter(correctGreenFilter);
        }
    }

    private void setQuestionCount(TextView tv){
        tv.setText(questionCount + " / 10");
    }

    private void resetViews() {
        btn_top_left.setBackgroundResource(R.drawable.round_back_white_10);
        btn_top_right.setBackgroundResource(R.drawable.round_back_white_10);
        btn_bottom_left.setBackgroundResource(R.drawable.round_back_white_10);
        btn_bottom_right.setBackgroundResource(R.drawable.round_back_white_10);
        top_left_img.clearColorFilter();
        top_right_img.clearColorFilter();
        bottom_left_img.clearColorFilter();
        bottom_right_img.clearColorFilter();
    }

    private void createNewWellDoneDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View wellDonePopupView = getLayoutInflater().inflate(R.layout.popup_well_done, null);
        newWellDoneDialog_rtnButton = (Button) wellDonePopupView.findViewById(R.id.rtn_button);
        newWellDoneDialog_wellDoneTV = (TextView) wellDonePopupView.findViewById(R.id.correct_tv);
        newWellDoneDialog_incorrect = (TextView) wellDonePopupView.findViewById(R.id.incorrect_tv);

        dialogBuilder.setView(wellDonePopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        newWellDoneDialog_wellDoneTV.setText("Correct Answers = " + lstCorrectAnswers.size());
        newWellDoneDialog_incorrect.setText("Incorrect Answers = " + lstIncorrectAnswers.size());

        updateSP();

        newWellDoneDialog_rtnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkCorrect(){
        if(keyChar.getLatinCharacter().equals(optionSelectedByUser)){
            lstCorrectAnswers.add(keyChar.getLatinCharacter());
        } else {
            lstIncorrectAnswers.add(optionSelectedByUser);
        }
    }

    private void updateSP(){
        for(String s : lstCorrectAnswers){
            Integer prog_num = Integer.valueOf(sharedPreferencesHandler.
                    getSp().getString(s, ""));
            if(prog_num < 10) {
                prog_num++;
                sharedPreferencesHandler.getEditor().putString(s, prog_num.toString());
                sharedPreferencesHandler.getEditor().apply();
            }
        }
        for(String s : lstIncorrectAnswers){
            Integer prog_num = Integer.valueOf(sharedPreferencesHandler
                    .getSp().getString(s, ""));
            if(prog_num > 1) {
                prog_num--;
                sharedPreferencesHandler.getEditor().putString(s, prog_num.toString());
                sharedPreferencesHandler.getEditor().apply();
            }
        }
    }
    public void setKeyChar(Hiragana_character keyChar) {
        this.keyChar = keyChar;
    }
}

