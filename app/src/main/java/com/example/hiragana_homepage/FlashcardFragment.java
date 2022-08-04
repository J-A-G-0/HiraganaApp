package com.example.hiragana_homepage;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FlashcardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FlashcardFragment extends Fragment {

    private static final String CHAR_KEY = "char_key";
    private Hiragana_character mCharForFlashcard;


    public FlashcardFragment() {
        // Required empty public constructor
    }

    //This is the blueprint for making a Flashcardfragment, it takes a Hiragana character 'h', creates a fragment, and returns it.
    public static FlashcardFragment newInstance(Hiragana_character h) {
        FlashcardFragment fragment = new FlashcardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CHAR_KEY, h);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCharForFlashcard = getArguments().getParcelable(CHAR_KEY);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Here, the Fragment view is created and retrieves the bundle, char_key.
        mCharForFlashcard = (Hiragana_character) getArguments().getParcelable(CHAR_KEY);
        View view = inflater.inflate(R.layout.fragment_flashcard, container, false);
        ImageView imv = (ImageView) view.findViewById(R.id.flashcardImage);
        Button btn_hiragana = (Button) view.findViewById(R.id.button_hiragana);
        Button btn_drawing = (Button) view.findViewById(R.id.button_drawing);
        Button btn_audio = (Button) view.findViewById(R.id.audio_button);
        TextView tv_mnemonic = (TextView) view.findViewById(R.id.textView_mnemonic);

        imv.setImageResource(mCharForFlashcard.getThumbnail());
        tv_mnemonic.setText(mCharForFlashcard.getMnemonic());

        return view;
    }


}
