package com.example.hiragana_homepage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Class for checking and updating ArrayList of characters user has studied.
 *
 * @author joelgodfrey
 */
public class UserProgressHandler {

    private Map user_progress_map;
    private ArrayList<Hiragana_character> userProgArrayLst;

    public void setUpUserProgArrayLst(ArrayList<Hiragana_character> initialisedArray){
        ArrayList<Hiragana_character> newArray = new ArrayList<>();
        for(Hiragana_character h : initialisedArray) {
            if (user_progress_map.get(h.getLatinCharacter()).equals("0")) {
                continue;
            }
            newArray.add(h);
        }
        setUserProgArrayLst(newArray);
    }


    public void setUser_progress_map(Map user_progress_map) {
        this.user_progress_map = user_progress_map;
    }

    public void initialiseAll(Map<String, ?> spCompleteMap,
                              ArrayList<Hiragana_character> initialisedArray) {
        setUser_progress_map(spCompleteMap);
        setUpUserProgArrayLst(initialisedArray);
    }

    public ArrayList<Hiragana_character> getUserProgArrayLst() {
        return userProgArrayLst;
    }

    public void setUserProgArrayLst(ArrayList<Hiragana_character> userProgArrayLst) {
        this.userProgArrayLst = userProgArrayLst;
    }
}
