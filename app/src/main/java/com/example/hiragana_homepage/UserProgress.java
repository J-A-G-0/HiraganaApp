package com.example.hiragana_homepage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class UserProgress {
    private Context context;
    Map user_progress_map;
    ArrayList<Hiragana_character> userProgArrayLst;
    Hiragana_character[] lstHiragana;
    ArrayList<Hiragana_character> arrayLstHiragana;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public ArrayList<Hiragana_character> getArrayLstHiragana() {
        return arrayLstHiragana;
    }

    public void setArrayLstHiragana(ArrayList<Hiragana_character> arrayLstHiragana) {
        this.arrayLstHiragana = arrayLstHiragana;
    }


    public Hiragana_character[] getLstHiragana() {
        return lstHiragana;
    }

    public void setLstHiragana(Hiragana_character[] lstHiragana) {
        this.lstHiragana = lstHiragana;
    }

    public void setUser_progress_map() {
        SharedPreferences sp = getSp();
        user_progress_map = sp.getAll();
    }

    public UserProgress(Context context) {
        this.context = context;
    }


    public Map getUser_progress_map() {
        return user_progress_map;
    }

    public void setSp(){
        SharedPreferences sp =  context.getApplicationContext()
                .getSharedPreferences("UserProgress", Context.MODE_PRIVATE);
        this.sp = sp;
    }

    public SharedPreferences getSp(){
        return sp;
    }

    /*public void get_progress_json()
    {
        String json;
        try
        {
            InputStream is = context.getAssets().open("progress.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
            Map<String,String> map = gson.fromJson(json, stringStringMap);
            setInitial_user_progress_map(map);

        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }

     */

    // This gets the Hiragana from the JSON, iut then also sets up all of their drawables
    // through the method .setDrawable() in HiraganaCharacter.

    public void get_hiragana_json() {
        String json;
        try {
            InputStream is = context.getAssets().open("hiragana.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Hiragana_character[] char_list = gson.fromJson(json, Hiragana_character[].class);
            setLstHiragana(char_list);

            for(Hiragana_character h : lstHiragana){
                h.setDrawable();
            }

            //Here, we cast it back to an arraylist to fit everywhere else that's using arraylist.
            ArrayList<Hiragana_character> a1 = new ArrayList<>(Arrays.asList(lstHiragana));
            setArrayLstHiragana(a1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor() {
        editor = getSp().edit();
    }


    // Here, we add all of the Hiragana with a progress val > 0 to userProgArrayLst.
    public void setUpUserProgArrayLst(){
        ArrayList<Hiragana_character> newArray = new ArrayList<>();
        for(Hiragana_character h : arrayLstHiragana) {
            if (user_progress_map.get(h.getLatinCharacter()).equals("0")) {
                continue;
            }
            newArray.add(h);
        }
        setUserProgArrayLst(newArray);
    }

    public ArrayList<Hiragana_character> getUserProgArrayLst() {
        return userProgArrayLst;
    }

    public void setUserProgArrayLst(ArrayList<Hiragana_character> userProgArrayLst) {
        this.userProgArrayLst = userProgArrayLst;
    }

    public void firstTimeUserSetUp(){
        setSp();
        setEditor();
        get_hiragana_json();
    }

    public void setUpUp(){
        setSp();
        setEditor();
        get_hiragana_json();
        // So here we look at SP, and all of its dic values are added to a new map user_progress_map.
        // If there are none, it will just remain empty, but not null. However, it would contain null values when those are called if they've not been copied in.
        setUser_progress_map();
        setUpUserProgArrayLst();
        //ArraylstHiragana is setup within get_hiragana_json, so no need to do it again.
        }

        /*
        SetupUserProgArraylst is breaking everything because it is inside SetupUP(),
        so it is being called almost immediately on the app starting. Then there is no Map
        or something? Was a null object exception, possibly there was no SP, so it couldn't
        get latinChar etc..?
        Or maybe it couldn't do 'get all' to initialise the progress map. Will have to break up this
        big setup thing into a couple of differnet functions.
        One with the first three, and then one with the second two. That second one doesn't need to be called in the
        main activity? Or at least not until after that boolean has initialised things for the firs time.
         */


}



