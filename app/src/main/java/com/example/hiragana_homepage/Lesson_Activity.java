package com.example.hiragana_homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Class for creating Lessons.
 * Lessons are made up of Flashcard Fragments and Quiz Fragments.
 *
 * @author joelgodfrey
 */
public class Lesson_Activity extends AppCompatActivity {

    private FlashcardFragment flashcardFragment;
    private QuizFragment quizFragment;
    private WellDoneFragment wellDoneFragment;

    private final ArrayList<Hiragana_character> lesson_chars = new ArrayList();
    private Integer currentCharNumber;

    private Integer page_num;
    private final ArrayList<ArrayList> charsForQuiz = new ArrayList<>();
    private Parcelable_Array newPA = new Parcelable_Array(charsForQuiz);

    private Integer decrement_counter;
    private SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler
            (this);

    private HiraganaInitialiser hiraganaInitialiser = new HiraganaInitialiser(this);
    private final UserProgressHandler userProgressHandler = new UserProgressHandler();

    private final AudioHandler audioHandler = new AudioHandler(this);

    private Boolean atEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        hiraganaInitialiser.setCompleteHiraganaArrayList
                (hiraganaInitialiser.getCompletedHiraganaArrayListFromJSON());
        sharedPreferencesHandler.setUpSp();
        userProgressHandler.initialiseAll
                (sharedPreferencesHandler.getAllFromSP(),
                        hiraganaInitialiser.getCompleteHiraganaArrayList());


        flashcardFragment = new FlashcardFragment();
        quizFragment = new QuizFragment();
        wellDoneFragment = new WellDoneFragment();

        // initialised as 0, since this will call the first index in the arraylist.
        // This is incremented when a new flashcard is loaded.
        currentCharNumber = 0;

        // This is incremented when a new flashcard OR a new quiz is loaded.
        page_num = 0;

        atEnd = false;

        //This populates the lesson_chars Arraylst, so that it contains  next chars to be studied.
        // This is 5 for the most part.
        Integer index = userProgressHandler.getUserProgArrayLst().size();
        // So size would be 5 if we've seen all the first row.
        // index 5 would therefore indeed be the next number. That checks out fine.
        if(index == 35){
            lesson_chars.add(hiraganaInitialiser.getCompleteHiraganaArrayList().get(index));
            lesson_chars.add(hiraganaInitialiser.getCompleteHiraganaArrayList().get(36));
            lesson_chars.add(hiraganaInitialiser.getCompleteHiraganaArrayList().get(37));
        }
        else if(index == 43){
            lesson_chars.add(hiraganaInitialiser.getCompleteHiraganaArrayList().get(index));
            lesson_chars.add(hiraganaInitialiser.getCompleteHiraganaArrayList().get(44));
            lesson_chars.add(hiraganaInitialiser.getCompleteHiraganaArrayList().get(45));
        }
        //Is this increment line going to cause problems? Look out for it...
        else {
            for (int i = 0; i < 5; i++) {
                lesson_chars.add(hiraganaInitialiser.getCompleteHiraganaArrayList().get(index));
                index++;
            }
        }

        // Here we call a method that adds each Hiragana_char to be studied to an arraylst
        // of arrays, where each is stored with three random 'padding' chars for the quiz.
        addCharsToArrayLst(hiraganaInitialiser);

        //Makes a parcelable array using our Parcelable_Array class, to pass them to the quiz.
        newPA = new Parcelable_Array(charsForQuiz);

        // Set up soundpool and audio file.
        audioHandler.setUpAudioHandler(lesson_chars.get(currentCharNumber));




        //Load the Flashcard Fragment with the first char.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = FlashcardFragment.newInstance(lesson_chars
                        .get(currentCharNumber));
                ft.replace(R.id.container_a, fragment);
                ft.commit();



        Button btn_flashcard = (Button) findViewById(R.id.btn_load_flashcard);
        btn_flashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //Reload the fragment with the next char_placeholder.
                // Always make a new FragmentTransaction or it gets err 'commit already called'.
                if(atEnd) {
                    Intent intent = new Intent(Lesson_Activity.this,
                            HomeScreenActivity.class);
                    startActivity(intent);                }
                else {
                loadNextQuiz(newPA);}
            }
        });
    }

    /**
     * Creates an arrayist of chars for this Lesson.
     * Takes the keychar and gets three random characters to accompany it.
     * These are added to an arraylist, which is then added to charsForQuiz.
     * This happens until all of the characters for that lesson have been added,
     * along with their padding.
     * @param hiraganaInitialiser
     */

    public void addCharsToArrayLst(HiraganaInitialiser hiraganaInitialiser) {
        Randomiser r = new Randomiser();
        ArrayList<Hiragana_character> al = hiraganaInitialiser.getCompleteHiraganaArrayList();
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

    /**
     * Load next quiz fragment.
     * @param newPA
     */
    public void loadNextQuiz(Parcelable_Array newPA) {
        if (page_num % 2 == 0) {
            page_num++;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment fragment = QuizFragment.newInstance(newPA);
            ft.replace(R.id.container_a, fragment);
            ft.commit();
        }
    }

    /**
     * Load next Flashcard Fragment.
     */

    public void loadNextFlashcard(){
        currentCharNumber++;
        page_num++;
        audioHandler.passHiraganaAudioToSoundPool(lesson_chars.get(currentCharNumber));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FlashcardFragment.newInstance(lesson_chars.get(currentCharNumber));
        ft.replace(R.id.container_a, fragment);
        ft.commit();
    }


    /**
     * Load welldone Fragment.
     * @param up
     * @param hiraganaInitialiser
     */

    public void loadWellDoneScreen(SharedPreferencesHandler up,
                                   HiraganaInitialiser hiraganaInitialiser) {
        addCharsToSP(lesson_chars);
        updateUserProgressHandler(up, hiraganaInitialiser);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = WellDoneFragment.newInstance("Forza", "Milan");
        ft.replace(R.id.container_a, fragment);
        ft.commit();
    }

    /**
     * Method to add the next 3 / 5 characters to SP once they open this lesson activity.
     * @param charsLst
     */
    public void addCharsToSP(ArrayList<Hiragana_character> charsLst){
        for(Hiragana_character h : charsLst){
            sharedPreferencesHandler.getEditor().putString(h.getLatinCharacter(), "1");
            sharedPreferencesHandler.getEditor().apply();
        }
    }

    public void updateUserProgressHandler(SharedPreferencesHandler sharedPreferencesHandler,
                                          HiraganaInitialiser hiraganaInitialiser) {
        userProgressHandler.initialiseAll(sharedPreferencesHandler.getAllFromSP(),
                hiraganaInitialiser.getCompleteHiraganaArrayList());
    }

    /**
     * Method to check  user's current completion state.
     * @return
     */
    public int getNumberOfHiraganaStudied(){
        return userProgressHandler.getUserProgArrayLst().size();
    }

    public void playAudio(View v) {
        audioHandler.playAudio(v);
    }


    public SharedPreferencesHandler getSharedPreferencesHandler() {
        return sharedPreferencesHandler;
    }

    public void setSharedPreferencesHandler(SharedPreferencesHandler sharedPreferencesHandler) {
        this.sharedPreferencesHandler = sharedPreferencesHandler;
    }

    public Boolean getAtEnd() {
        return atEnd;
    }

    public void setAtEnd(Boolean atEnd) {
        this.atEnd = atEnd;
    }

    public Integer getDecrement_counter() {
        return decrement_counter;
    }

    public void setDecrement_counter(Integer decrement_counter) {
        this.decrement_counter = decrement_counter;
    }

    public HiraganaInitialiser getHiraganaInitialiser() {
        return hiraganaInitialiser;
    }

    public void setHiraganaInitialiser(HiraganaInitialiser hiraganaInitialiser) {
        this.hiraganaInitialiser = hiraganaInitialiser;
    }

    public ArrayList<Hiragana_character> getLesson_chars() {
        return lesson_chars;
    }
    public Integer getCurrentCharNumber() {
        return currentCharNumber;
    }
    public Integer getPage_num() {
        return page_num;
    }

}