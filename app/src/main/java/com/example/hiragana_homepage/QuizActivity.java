package com.example.hiragana_homepage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    ArrayList<Hiragana_character> lstOfFour;
    String optionSelectedByUser = "";
    Hiragana_character keyChar = new Hiragana_character();
    Hiragana_character top_left_char = new Hiragana_character();
    Hiragana_character top_right_char = new Hiragana_character();
    Hiragana_character bottom_left_char = new Hiragana_character();
    Hiragana_character bottom_right_char = new Hiragana_character();

    ImageView top_left_img;
    ImageView top_right_img;
    ImageView bottom_left_img;
    ImageView bottom_right_img;
    TextView charBeingStudied;
    TextView tv_question_num;

    LinearLayout top_left;
    LinearLayout top_right;
    LinearLayout bottom_left;
    LinearLayout bottom_right;

    int questionCount = 0;
    ArrayList<String> lstCorrectAnswers;
    ArrayList<String> lstIncorrectAnswers;

    private Handler mHandler = new Handler();
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button newWellDoneDialog_rtnButton;
    private TextView newWellDoneDialog_wellDoneTV;
    private TextView newWellDoneDialog_incorrect;

    UserProgress up = new UserProgress(this);

    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        lstOfFour = new ArrayList<>();
        lstCorrectAnswers = new ArrayList<>();
        lstIncorrectAnswers = new ArrayList<>();


        final ImageView backBtn = findViewById(R.id.backBtn);

        top_left = findViewById(R.id.top_left);
        top_right = findViewById(R.id.top_right);
        bottom_left = findViewById(R.id.bottom_left);
        bottom_right = findViewById(R.id.bottom_right);

        bottom_right_img = findViewById(R.id.bottom_right_img);
        bottom_left_img = findViewById(R.id.bottom_left_img);
        top_right_img = findViewById(R.id.top_right_img);
        top_left_img = findViewById(R.id.top_left_img);

        charBeingStudied = findViewById(R.id.charBeingStudied);
        tv_question_num = findViewById(R.id.tv_q_count);
        final Button continue_button = findViewById(R.id.continueButton);

        up.setUpUp();



        //Setup the green border highlighting on click when user selects an answer.
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                top_left.setBackgroundResource(R.drawable.rounded_back_white_stroke);

                top_right.setBackgroundResource(R.drawable.round_back_white_10);
                bottom_left.setBackgroundResource(R.drawable.round_back_white_10);
                bottom_right.setBackgroundResource(R.drawable.round_back_white_10);

                optionSelectedByUser = top_left_char.getLatinCharacter();
            }
        });

        top_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                top_right.setBackgroundResource(R.drawable.rounded_back_white_stroke);

                top_left.setBackgroundResource(R.drawable.round_back_white_10);
                bottom_left.setBackgroundResource(R.drawable.round_back_white_10);
                bottom_right.setBackgroundResource(R.drawable.round_back_white_10);

                optionSelectedByUser = top_right_char.getLatinCharacter();
            }
        });

        bottom_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_left.setBackgroundResource(R.drawable.rounded_back_white_stroke);

                top_right.setBackgroundResource(R.drawable.round_back_white_10);
                top_left.setBackgroundResource(R.drawable.round_back_white_10);
                bottom_right.setBackgroundResource(R.drawable.round_back_white_10);

                optionSelectedByUser = bottom_left_char.getLatinCharacter();
            }
        });

        bottom_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottom_right.setBackgroundResource(R.drawable.rounded_back_white_stroke);

                top_right.setBackgroundResource(R.drawable.round_back_white_10);
                bottom_left.setBackgroundResource(R.drawable.round_back_white_10);
                top_left.setBackgroundResource(R.drawable.round_back_white_10);

                optionSelectedByUser = bottom_right_char.getLatinCharacter();
            }
        });

        //Setup the back button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizActivity.this, HiraganaHomepage.class));
                finish();
                //createNewWellDoneDialog();
            }
        });

        //The isEmpty is top stop people clicking continue and increasing the questionCount.
        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!optionSelectedByUser.isEmpty()) {
                    // These lines prevent the user clicking several times and skipping characters.
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1700){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    // TextView test_tv = findViewById(R.id.textView_test);
                    // test_tv.setText("keyChar is: " + keyChar.getLatinCharacter() + ", user choice is: " + optionSelectedByUser + " question number is: " + questionCount);
                    checkCorrect();
                    revealAnswer(top_left, top_right, bottom_left, bottom_right);
                    mHandler.postDelayed(mResetPage, 1500);
                }
            }
        });

        //Here, we populate everything the first time for the quiz.
        setUpImages(top_left_img, top_right_img, bottom_left_img, bottom_right_img, charBeingStudied, keyChar);

    }

    public void setUpImages(ImageView iv_1, ImageView iv_2, ImageView iv_3, ImageView iv_4, TextView tv, Hiragana_character key_c){
        Randomiser rando = new Randomiser();
        ArrayList fourNumbers;
        //Before, this was just "arraylst" i think, so giving the size
        fourNumbers = rando.generateNumbers(up.getUserProgArrayLst());
        iv_1.setImageResource(((Hiragana_character) fourNumbers.get(0)).getThumbnail());
        iv_2.setImageResource(((Hiragana_character) fourNumbers.get(1)).getThumbnail());
        iv_3.setImageResource(((Hiragana_character) fourNumbers.get(2)).getThumbnail());
        iv_4.setImageResource(((Hiragana_character) fourNumbers.get(3)).getThumbnail());
        top_left_char= (Hiragana_character) fourNumbers.get(0);
        top_right_char=(Hiragana_character) fourNumbers.get(1);
        bottom_left_char=(Hiragana_character) fourNumbers.get(2);
        bottom_right_char=(Hiragana_character) fourNumbers.get(3);
        Hiragana_character temp_char = rando.generateKeyChar(fourNumbers);
        key_c.setLatinCharacter(temp_char.getLatinCharacter());
        key_c.setThumbnail(temp_char.getThumbnail());
        tv.setText(key_c.getLatinCharacter());
        optionSelectedByUser = "";
        questionCount++;
    }

    private Runnable mResetPage = new Runnable() {
        @Override
        public void run() {
            //Fixed breaking at 10 by using add all, rather than removing objects from the original list until there were none less to use.
            if (questionCount < 10) {
                setUpImages(top_left_img, top_right_img, bottom_left_img, bottom_right_img, charBeingStudied, keyChar);
                setQuestionCount(tv_question_num);
                resetViews();
            } else {
                createNewWellDoneDialog();
            }
        }
    };

    private void revealAnswer(LinearLayout topLeft, LinearLayout topRight, LinearLayout bottomLeft, LinearLayout bottomRight){
        int correctGreen = Color.parseColor("#00C853");
        ColorFilter correctGreenFilter = new PorterDuffColorFilter(correctGreen, PorterDuff.Mode.MULTIPLY);
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
        tv.setText(String.valueOf(questionCount) + " / 10");
    }

    private void resetViews() {
        top_left.setBackgroundResource(R.drawable.round_back_white_10);
        top_right.setBackgroundResource(R.drawable.round_back_white_10);
        bottom_left.setBackgroundResource(R.drawable.round_back_white_10);
        bottom_right.setBackgroundResource(R.drawable.round_back_white_10);
        top_left_img.clearColorFilter();
        top_right_img.clearColorFilter();
        bottom_left_img.clearColorFilter();
        bottom_right_img.clearColorFilter();
    }

    public void createNewWellDoneDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View wellDonePopupView = getLayoutInflater().inflate(R.layout.popup, null);
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
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void checkCorrect(){
        if(keyChar.getLatinCharacter().equals(optionSelectedByUser)){
            lstCorrectAnswers.add(keyChar.getLatinCharacter());
        } else {
            lstIncorrectAnswers.add(optionSelectedByUser);
        }
    }

    public void updateSP(){
        /*UserProgress up = new UserProgress(this);
        up.setSp();
        up.setEditor();

         */

        //Check if they appear in the lstCorrectAnswers, where they're held as just strings.

        for(String s : lstCorrectAnswers){
            Integer prog_num = Integer.valueOf(up.getSp().getString(s, ""));
            //sp.getString("s", "");
            if(prog_num < 10) {
                prog_num++;
                up.getEditor().putString(s, prog_num.toString());
                up.getEditor().apply();
            }
        }
        for(String s : lstIncorrectAnswers){
            Integer prog_num = Integer.valueOf(up.getSp().getString(s, ""));
            if(prog_num > 1) {
                prog_num--;
                up.getEditor().putString(s, prog_num.toString());
                up.getEditor().apply();
            }
        }
    }
}

