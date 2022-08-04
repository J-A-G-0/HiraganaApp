package com.example.hiragana_homepage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    private static final String ARRAY_KEY = "array_key";
    private Parcelable_Array mParcelable_array;

    String optionSelectedByUser = "";
    private Handler mHandler = new Handler();
    Hiragana_character top_left_char = new Hiragana_character();
    Hiragana_character top_right_char = new Hiragana_character();
    Hiragana_character bottom_left_char = new Hiragana_character();
    Hiragana_character bottom_right_char = new Hiragana_character();

    LinearLayout top_left;
    LinearLayout top_right;
    LinearLayout bottom_left;
    LinearLayout bottom_right;
    TextView charBeingStudied;

    ImageView bottom_right_img;
    ImageView bottom_left_img;
    ImageView top_right_img;
    ImageView top_left_img;

    ArrayList<ArrayList> arrayBank;
    ArrayList<Hiragana_character> currentArray;
    Hiragana_character keyChar = new Hiragana_character();

    Lesson_Activity lesson_activity;

    private long mLastClickTime = 0;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(Parcelable_Array parcelable_array) {
        QuizFragment fragment = new QuizFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARRAY_KEY, parcelable_array);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParcelable_array = getArguments().getParcelable(ARRAY_KEY);
        }
    }

    public void setUpImages(ImageView iv_1, ImageView iv_2, ImageView iv_3, ImageView iv_4, TextView tv, ArrayList al){
        // Here we feed it the parcelable'd four numbers, which includes keychar at index [0].
        Hiragana_character key_c = (Hiragana_character) al.get(0);
        //Here we add the four to a new ArrayList and shuffle them so that they appear in random positions.
        ArrayList<Hiragana_character> fourNumbers = new ArrayList();
        fourNumbers.addAll(al);
        Collections.shuffle(fourNumbers);
        iv_1.setImageResource(((Hiragana_character) fourNumbers.get(0)).getThumbnail());
        iv_2.setImageResource(((Hiragana_character) fourNumbers.get(1)).getThumbnail());
        iv_3.setImageResource(((Hiragana_character) fourNumbers.get(2)).getThumbnail());
        iv_4.setImageResource(((Hiragana_character) fourNumbers.get(3)).getThumbnail());
        top_left_char = (Hiragana_character) fourNumbers.get(0);
        top_right_char=(Hiragana_character) fourNumbers.get(1);
        bottom_left_char=(Hiragana_character) fourNumbers.get(2);
        bottom_right_char=(Hiragana_character) fourNumbers.get(3);
        key_c.setDrawable();
        tv.setText(key_c.getLatinCharacter());
        optionSelectedByUser = "";
        lesson_activity.decrement_counter--;
        keyChar = key_c;
    }

    private Runnable mResetPage = new Runnable() {
        @Override
        public void run() {
            if(lesson_activity.decrement_counter >= 0) {
                currentArray = arrayBank.get(lesson_activity.decrement_counter);
                setUpImages(top_left_img, top_right_img, bottom_left_img, bottom_right_img, charBeingStudied, currentArray);
                resetViews();
            } else if (lesson_activity.page_num == lesson_activity.lesson_chars.size()*2-1) {
                lesson_activity.atEnd = true;
                lesson_activity.loadWellDoneScreen();
            }
             else {
                lesson_activity.loadNextFlashcard();
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




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mParcelable_array = (Parcelable_Array) getArguments().getParcelable(ARRAY_KEY);
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        top_left = view.findViewById(R.id.top_left);
        top_right = view.findViewById(R.id.top_right);
        bottom_left = view.findViewById(R.id.bottom_left);
        bottom_right = view.findViewById(R.id.bottom_right);

        bottom_right_img = view.findViewById(R.id.bottom_right_img);
        bottom_left_img = view.findViewById(R.id.bottom_left_img);
        top_right_img = view.findViewById(R.id.top_right_img);
        top_left_img = view.findViewById(R.id.top_left_img);

        charBeingStudied = view.findViewById(R.id.charBeingStudied);
        Button continue_button = view.findViewById(R.id.continueButton);

        lesson_activity = (Lesson_Activity)getActivity();

        arrayBank = mParcelable_array.getmParcelableArray();
        // Need to iterate this each time! When they press continue.
        // greyING OUT FOR A SEONCcurrentArray = arrayBank.get(lesson_activity.quizFragmentCounter);
        //decrementCounter = lesson_activity.currentCharNumber;
        //currentArray = arrayBank.get(decrementCounter);
        lesson_activity.decrement_counter = lesson_activity.currentCharNumber;
        currentArray = arrayBank.get(lesson_activity.decrement_counter);


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

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!optionSelectedByUser.isEmpty()) {
                    // These lines prevent the user clicking several times and skipping characters.
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1700){
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    revealAnswer(top_left, top_right, bottom_left, bottom_right);
                    mHandler.postDelayed(mResetPage, 1500);
                }
            }
        });

        mResetPage.run();
        return view;
    }
}