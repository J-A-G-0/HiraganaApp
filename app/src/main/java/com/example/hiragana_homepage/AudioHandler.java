package com.example.hiragana_homepage;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.View;

import androidx.loader.content.Loader;

/**
 * The class used to handle playing audio.
 *
 * The class sets up SoundPool, and can be passed audio files to play.
 *
 * @author joelgodfrey
 */

public class AudioHandler {

    Context context;
    private SoundPool soundPool;
    // int for storing the audio of a HiraganaCharacter.
    private int charAudio;

    /**
     * Sets up charAudio and soundPool so that audioHandler can be used.
     * @param hiragana_character
     */
    public void setUpAudioHandler(Hiragana_character hiragana_character) {
        setupSoundPool();
        passHiraganaAudioToSoundPool(hiragana_character);
    }


    /**
     * Get context of the current activity so that AudioHandler works correctly.
     * @param context
     */
    public AudioHandler(Context context) {
        this.context = context;
    }

    /**
     * Set Soundpool's audio file to the audio associated with pased character.
     * @param hiragana_character
     */
    public void passHiraganaAudioToSoundPool(Hiragana_character hiragana_character) {
        setCharAudio(soundPool.load(context, hiragana_character.getAudio(), 1));
    }

    /**
     * Set up Soundpool.
     * if/else branch ensures that SoundPool works on older versions of Android.
     */
    public void setupSoundPool() {
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
    }

    public void setCharAudio(int charAudio) {
        this.charAudio = charAudio;
    }

    /**
     * Play audio file loaded in Soundpool.
     * @param v
     */
    public void playAudio(View v){
        soundPool
                .play(charAudio, 1, 1, 0, 0, 1);
    }

}
