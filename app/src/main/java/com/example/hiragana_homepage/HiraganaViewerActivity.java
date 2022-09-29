package com.example.hiragana_homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Class for viewing Flashcard Fragments.
 *
 * @author joelgodfrey
 */
public class HiraganaViewerActivity extends AppCompatActivity {

    private final SharedPreferencesHandler sharedPreferencesHandler =
            new SharedPreferencesHandler(this);
    private final UserProgressHandler userProgressHandler = new UserProgressHandler();
    private final HiraganaInitialiser hiraganaInitialiser = new HiraganaInitialiser(this);

    private final AudioHandler audioHandler = new AudioHandler(this);
    private FlashcardFragment flashcardFragment;
    // Hiragana_character object representing the Hiragana for this flashcard.
    private Hiragana_character charToStudy = new Hiragana_character();


    //Integer giving the index of charToStudy in the up.getArrayLstHiragana();
    private Integer charIndex;
    private Button btn_right;
    private Button btn_left;


    public Integer getCharIndex() {
        return charIndex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana_viewer);

        hiraganaInitialiser.setCompleteHiraganaArrayList(hiraganaInitialiser
                .getCompletedHiraganaArrayListFromJSON());
        sharedPreferencesHandler.setUpSp();
        userProgressHandler.initialiseAll(sharedPreferencesHandler
                .getAllFromSP(), hiraganaInitialiser.getCompleteHiraganaArrayList());

        flashcardFragment = new FlashcardFragment();

        // Get the string that was put as an extra when the intent was called.
        Bundle extras = getIntent().getExtras();
        String receivedHiraganaChar = extras.getString("hiragana_char");
        // Here we set charToStudy
        setupCharToStudy(receivedHiraganaChar);
        // Get the index of the current char, so that can setup the 'next' and 'previous' functions.
        charIndex = getIndexOfChar(charToStudy);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FlashcardFragment.newInstance(charToStudy);
        ft.replace(R.id.container_flashcard, fragment);
        ft.commit();

        // Set up soundpool and audio file.
        audioHandler.setUpAudioHandler(charToStudy);

        btn_right = (Button) findViewById(R.id.button_right);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNextFlashcard();
            }
        });

        btn_left = (Button) findViewById(R.id.button_left);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPreviousFlashcard();
            }
        });

    }

    public Hiragana_character getCharToStudy() {
        return charToStudy;
    }


    /**
     * Method to retrieve the assoicated HiraganaCharacter obj, based on passed latinchar string.
     * @param receivedChar
     */

    public void setupCharToStudy(String receivedChar) {
        for (Hiragana_character h : hiraganaInitialiser.getCompleteHiraganaArrayList()) {
            if (h.getHiraganaCharacter().equals(receivedChar)) {
                setCharToStudy(h);
                break;
            }
        }
    }


    /**
     * Method to return the ArraylstHiragana index of the given Hiragana character.
     * @param h
     * @return Integer
     */

    public Integer getIndexOfChar(Hiragana_character h){
        return hiraganaInitialiser.getCompleteHiraganaArrayList().indexOf(h);
    }


    /**
     * Method that replaces current fragment with fragment containing the next flashcard in list.
     */

    private void loadNextFlashcard(){
        if(charIndex < 45){
            charIndex++;
        } else {
            charIndex = 0;
        }
        setCharToStudy(getCharFromIndex(charIndex));
        audioHandler.passHiraganaAudioToSoundPool(charToStudy);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FlashcardFragment.newInstance(hiraganaInitialiser
                .getCompleteHiraganaArrayList().get(charIndex));
        ft.replace(R.id.container_flashcard, fragment);
        ft.commit();
    }


    /**
     * Method that replaces current fragment with fragment containing
     * the previous flashcard in list.
     */

    private void loadPreviousFlashcard(){
        if(charIndex > 0){
            charIndex--;
        } else {
            charIndex = 45;
        }
        setCharToStudy(getCharFromIndex(charIndex));
        audioHandler.passHiraganaAudioToSoundPool(charToStudy);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FlashcardFragment.newInstance(hiraganaInitialiser
                .getCompleteHiraganaArrayList().get(charIndex));
        ft.replace(R.id.container_flashcard, fragment);
        ft.commit();
    }


    /**
     * Method to play audio of HiraganaCharacter.
     * @param v
     */

    public void playAudio(View v) {
        audioHandler.playAudio(v);
    }


    /**
     * Method that returns a Hiragana_character object from the arraylist based on the given index.
     * @param index
     * @return
     */

    public Hiragana_character getCharFromIndex(Integer index) {
        return hiraganaInitialiser.getCompleteHiraganaArrayList().get(index);
    }


    public void setCharToStudy(Hiragana_character charToStudy) {
        this.charToStudy = charToStudy;
    }

    public AudioHandler getAudioHandler() {
        return audioHandler;
    }

}
