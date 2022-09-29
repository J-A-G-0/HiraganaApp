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


/**
 * Class for interactions with Shared Preferences.
 *
 * @author joelgodfrey
 */
public class SharedPreferencesHandler {
    private final Context context;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    // Used to get context of the current activity so that UP works correctly.
    public SharedPreferencesHandler(Context context) {
        this.context = context;
    }

    /** Sets the classes Shared Preferences 'sp' constructor.
     */
    public void setSp(){
        SharedPreferences sp =  context.getApplicationContext()
                .getSharedPreferences("UserProgress",
                        Context.MODE_PRIVATE);
        this.sp = sp;
    }

    /** Returns Shared Preferences.
     * @return Shared preferences.
     */
    public SharedPreferences getSp(){
        return sp;
    }

    /** Gets the Shared Preferences Editor.
     * @return Shared Preferences Editor.
     */
    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    /** Set the class's editor constructor to Shared Preferences Editor.
     */
    public void setEditor() {
        editor = getSp().edit();
    }


    /** Make a usable SPHandler obj by setting the sp and editor constructors.
     * Calls the setSP() method to set the sp constructor to SP().
     * Calls the setEditor() method to set the editor constructor to
     * Shared Preferences Editor.
     */
    public void setUpSp(){
        setSp();
        setEditor();
    }

    public Map<String, ?> getAllFromSP() {
        return getSp().getAll();
    }


}



