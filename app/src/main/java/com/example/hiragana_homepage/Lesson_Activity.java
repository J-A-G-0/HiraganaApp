package com.example.hiragana_homepage;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lesson_Activity extends AppCompatActivity {

    private static int lesson_size;
    private FlashcardFragment flashcardFragment;
    private QuizFragment quizFragment;
    private WellDoneFragment wellDoneFragment;


    ArrayList<Hiragana_character> lesson_chars = new ArrayList();
    Integer currentCharNumber;
    Integer page_num;
    ArrayList<ArrayList> charsForQuiz = new ArrayList<>();
    Parcelable_Array newPA = new Parcelable_Array(charsForQuiz);

    Integer quizFragmentCounter;
    Integer decrement_counter;

    UserProgress up = new UserProgress(this);

    public SoundPool soundPool;
    public int sound1;

    Boolean atEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_activity);

        up.setUpUp();

        flashcardFragment = new FlashcardFragment();
        quizFragment = new QuizFragment();
        wellDoneFragment = new WellDoneFragment();

        // initialised as 0, since this will call the first index in the arraylist.
        // This is incremented when a new flashcard is loaded.
        currentCharNumber = 0;
        // This is incremented when a new flashcard OR a new quiz is loaded.
        page_num = 0;
        //quizFragmentCounter = 0;

        atEnd = false;

        //This populates the lesson_chars Arraylst, so that it contains the next chars to be studied.
        // This is 5 for the most part.
        Integer index = up.getUserProgArrayLst().size();
        // So size would be 5 if we've seen all the first row.
        // index 5 would therefore indeed be the next number. That checks out fine.
        if(index == 35){
            lesson_chars.add(up.getArrayLstHiragana().get(index));
            lesson_chars.add(up.getArrayLstHiragana().get(36));
            lesson_chars.add(up.getArrayLstHiragana().get(37));
        }
        else if(index == 43){
            lesson_chars.add(up.getArrayLstHiragana().get(index));
            lesson_chars.add(up.getArrayLstHiragana().get(44));
            lesson_chars.add(up.getArrayLstHiragana().get(45));
        }
        //Is this increment line going to cause problems? Look out for it...
        else {
            for (int i = 0; i < 5; i++) {
                lesson_chars.add(up.getArrayLstHiragana().get(index));
                index++;
            }
        }

        // Here we call a method that adds each Hiragana_char to be studied to an arraylst
        // of arrays, where each is stored with three random 'padding' chars for the quiz.
        addCharsToArrayLst(up);

        //Here we make a parcelable array using our Parcelable_Array class, to pass them to the quiz.
        newPA = new Parcelable_Array(charsForQuiz);

        // This if/else ensures that this still works on older versions of Android.
        // This is all the stuff for the SoundPool
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        lesson_chars.get(currentCharNumber).setAudioFile();
        sound1 = soundPool.load(this, lesson_chars.get(currentCharNumber).getAudio(), 1);

        //Here we load the fragment with the first char from our next batch.
        // In the case of a new user, this should be aiueo, we'll have to change the bit in main.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // 0 is our a sound, 4 is our o sound.
                Fragment fragment = FlashcardFragment.newInstance(lesson_chars.get(currentCharNumber));
                ft.replace(R.id.container_a, fragment);
                ft.commit();



        Button btn_flashcard = (Button) findViewById(R.id.btn_load_flashcard);
        btn_flashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Here we reload the fragment with the next char_placeholder. Always make a new FragmentTransaction or it gets err 'commit already called'.
                //could do something with odd and even numbers for knowing whether to load a quiz or a flashcard.
                // i.e. make a counter, if it's odd load a flashcard. Iterate on load, then next click it would load the quiz.
                //loadNextPage(newPA);
                if(atEnd) {
                    Intent intent = new Intent(Lesson_Activity.this, HiraganaHomepage.class);
                    startActivity(intent);                }
                else {
                loadNextQuiz(newPA);}
            }
        });
    }


    /* This method takes the keychar and gets three random characters to accompany it. It adds
    these to an arraylist (new_list), which is then added to charsForQuiz. This happens until
    all of the characters for that lesson have been added, along with their padding.
     */
    public void addCharsToArrayLst(UserProgress up) {
        Randomiser r = new Randomiser();
        ArrayList<Hiragana_character> al = up.getArrayLstHiragana();
        for (Hiragana_character h : lesson_chars) {
            ArrayList<Hiragana_character> new_list = new ArrayList();
            // Add the key_char at index 0 in the new_list.
            new_list.add(h);
            while (new_list.size() < 4) {
                Hiragana_character newAddition = new Hiragana_character();
                newAddition = r.getRandomHiragana(al);
                if (!new_list.contains(newAddition)) {
                    new_list.add(newAddition);
                }
            }
            charsForQuiz.add(new_list);
        }
    }


    public void loadNextQuiz(Parcelable_Array newPA) {
        if (page_num % 2 == 0) {
            page_num++;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = QuizFragment.newInstance(newPA);
            ft.replace(R.id.container_a, fragment);
            ft.commit();
        }
    }

    public void loadNextFlashcard(){
        currentCharNumber++;
        page_num++;
        setUpCharAudio();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FlashcardFragment.newInstance(lesson_chars.get(currentCharNumber));
        ft.replace(R.id.container_a, fragment);
        ft.commit();
    }

    public void loadWellDoneScreen() {
        addCharsToSP(lesson_chars);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = WellDoneFragment.newInstance("Forza", "Milan");
        ft.replace(R.id.container_a, fragment);
        ft.commit();
    }

    // A method to add the next 3 / 5 characters to SP once they open this lesson activity.
    // Feed it lesson_chars
    public void addCharsToSP(ArrayList<Hiragana_character> charsLst){
        for(Hiragana_character h : charsLst){
            up.getEditor().putString(h.getLatinCharacter(), "1");
            up.getEditor().apply();
        }
    }

    // A method to check the user's current completion state. We can have an if statement above for 46.
    public int getNumberOfHiraganaStudied(){
        return up.getUserProgArrayLst().size();
    }

    //Audio-related methods:
    public void playAudio(View v){
        soundPool.play(sound1, 1, 1, 0, 0, 1);
    }

    private void setUpCharAudio() {
        lesson_chars.get(currentCharNumber).setAudioFile();
        sound1 = soundPool.load(this, lesson_chars.get(currentCharNumber).getAudio(), 1);
    }



}