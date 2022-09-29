package com.example.hiragana_homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

/**
 * Class to display the user's progress breakdown on each character.
 *
 * @author joelgodfrey
 */

public class ProgressBreakdownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progress);

        SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler
                (this);
        HiraganaInitialiser hiraganaInitialiser = new HiraganaInitialiser(this);
        //Here we grab the Hiragana JSON file
        hiraganaInitialiser.setCompleteHiraganaArrayList(hiraganaInitialiser
                .getCompletedHiraganaArrayListFromJSON());


        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter
                (this, hiraganaInitialiser.getCompleteHiraganaArrayList());
        myrv.setLayoutManager(new GridLayoutManager(this, 3));
        myrv.setAdapter(myAdapter);
    }
}