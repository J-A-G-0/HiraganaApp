package com.example.hiragana_homepage;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to read JSON file and create an ArrayList of HiraganaCharacter objects.
 *
 * @author joelgodfrey
 */

public class HiraganaInitialiser {

    private final Context context;
    private ArrayList<Hiragana_character> completeHiraganaArrayList;

    // Used to get context of the current activity so that class works correctly.
    public HiraganaInitialiser(Context context) {
        this.context = context;
    }

    /**
     * Creates an ArrayList of HiraganaCharacter objects from a JSON file.
     * @return ArrayList.
     */
    public ArrayList<Hiragana_character> get_hiragana_arraylist_from_json() {
        String json;
        ArrayList<Hiragana_character> a1 = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("hiragana.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Hiragana_character[] char_list = gson.fromJson(json, Hiragana_character[].class);

            //Here, we cast it back to an arraylist to fit everywhere else that's using arraylist.
            a1.addAll(Arrays.asList(char_list));
            //setArrayLstHiragana(a1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return a1;
    }

    public ArrayList<Hiragana_character> getCompletedHiraganaArrayListFromJSON() {
        ArrayList<Hiragana_character> arrayListFromJson = get_hiragana_arraylist_from_json();
        return getHiraganaArrayListWithMedia(arrayListFromJson);
    }

    public ArrayList<Hiragana_character> getHiraganaArrayListWithMedia
            (ArrayList<Hiragana_character> hiraganaArray) {
        ArrayList<Hiragana_character> newArray = new ArrayList<>();
        newArray = assignHiraganaArrayListAudio(hiraganaArray);
        newArray = assignHiraganaArrayListDrawable(newArray);
        newArray = assignHiraganaArrayListMnemonicImage(newArray);
        return newArray;
    }

    public ArrayList<Hiragana_character> assignHiraganaArrayListAudio
            (ArrayList<Hiragana_character> hiraganaArray) {
        for(Hiragana_character h : hiraganaArray){
            h.setAudioFile();
        }
        return hiraganaArray;
    }

    public ArrayList<Hiragana_character> assignHiraganaArrayListDrawable
            (ArrayList<Hiragana_character> hiraganaArray) {
        for(Hiragana_character h : hiraganaArray){
            h.setDrawable();
        }
        return hiraganaArray;
    }

    public ArrayList<Hiragana_character> assignHiraganaArrayListMnemonicImage
            (ArrayList<Hiragana_character> hiraganaArray) {
        for(Hiragana_character h : hiraganaArray){
            h.addMnemonicImage();
        }
        return hiraganaArray;
    }

    public ArrayList<Hiragana_character> getCompleteHiraganaArrayList() {
        return completeHiraganaArrayList;
    }

    public void setCompleteHiraganaArrayList
            (ArrayList<Hiragana_character> completeHiraganaArrayList) {
        this.completeHiraganaArrayList = completeHiraganaArrayList;
    }

}
