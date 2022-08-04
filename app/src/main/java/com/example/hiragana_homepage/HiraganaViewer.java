package com.example.hiragana_homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class HiraganaViewer extends AppCompatActivity {

    UserProgress up = new UserProgress(this);
    private FlashcardFragment flashcardFragment;
    // Hiragana_character object representing the Hiragana for this flashcard.
    Hiragana_character charToStudy = new Hiragana_character();
    //Integer giving the index of charToStudy in the up.getArrayLstHiragana();
    Integer charIndex;

    public SoundPool soundPool;
    public int sound1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana_viewer);

        up.setUpUp();
        flashcardFragment = new FlashcardFragment();



        Bundle extras = getIntent().getExtras();
        String receivedHiraganaChar = extras.getString("hiragana_char");
        // Here we set charToStudy
        charToStudy = getCharToStudy(receivedHiraganaChar);
        charIndex = getCharIndex(charToStudy);
        FrameLayout flashcardFrame = (FrameLayout) findViewById(R.id.container_flashcard);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FlashcardFragment.newInstance(charToStudy);
        ft.replace(R.id.container_flashcard, fragment);
        ft.commit();

        // This if/else ensures that this still works on older versions of Android.
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

        charToStudy.setAudioFile();
        sound1 = soundPool.load(this, charToStudy.getAudio(), 1);



        Button btn_right = (Button) findViewById(R.id.button_right);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNextFlashcard();
            }
        });

        Button btn_left = (Button) findViewById(R.id.button_left);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPreviousFlashcard();
            }
        });

    }

    private Hiragana_character getCharToStudy(String receivedChar) {
        Hiragana_character charToStudy = new Hiragana_character();
        for (Hiragana_character h : up.getArrayLstHiragana()) {
            if (h.getHiraganaCharacter().equals(receivedChar)) {
                charToStudy = h;
                break;
            }
        }
        return charToStudy;
    }

    private Integer getCharIndex(Hiragana_character h){
        return up.getArrayLstHiragana().indexOf(h);
    }

    private void loadNextFlashcard(){
        if(charIndex < 45){
            charIndex++;
            setUpCharAudio();
        } else {
            charIndex = 0;
            setUpCharAudio();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FlashcardFragment.newInstance(up.getArrayLstHiragana().get(charIndex));
        ft.replace(R.id.container_flashcard, fragment);
        ft.commit();
    }

    private void loadPreviousFlashcard(){
        if(charIndex > 0){
            charIndex--;
            setUpCharAudio();
        } else {
            charIndex = 45;
            setUpCharAudio();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = FlashcardFragment.newInstance(up.getArrayLstHiragana().get(charIndex));
        ft.replace(R.id.container_flashcard, fragment);
        ft.commit();
    }


    public void playAudio(View v){
        soundPool.play(sound1, 1, 1, 0, 0, 1);
    }

    private void setUpCharAudio() {
        charToStudy = up.getArrayLstHiragana().get(charIndex);
        charToStudy.setAudioFile();
        sound1 = soundPool.load(this, charToStudy.getAudio(), 1);
    }



}
