package com.example.hiragana_homepage;


import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;


public class HiraganaHomepage extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hiragana_homepage);

        Button btn_next_lesson = (Button) findViewById(R.id.btn_next_lesson);
        Button btn_review = (Button) findViewById(R.id.btn_review);
        LinearLayout linLay = (LinearLayout) findViewById(R.id.nextStudy_view);

        UserProgress up = new UserProgress(this);
        //Here we grab the progress JSON file
        //up.get_progress_json();
        //Here we grab the Hiragana JSON file
        up.get_hiragana_json();

        ImageView imv = (ImageView) findViewById(R.id.image_header);
        imv.setImageResource(up.getArrayLstHiragana().get(4).getThumbnail());

        up.setUpUp();
        if(getNumberOfHiraganaStudied(up) == 46) {
            // Set the 'next lesson' button to disappear once the user has studied all 46 Hiragana.
            btn_next_lesson.setVisibility(View.GONE);
            linLay.setVisibility(View.GONE);
        }


        ImageButton btn_stats = (ImageButton) findViewById(R.id.button_stats);
        btn_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HiraganaHomepage.this, StatsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btn_info = (ImageButton) findViewById(R.id.button_info);
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HiraganaHomepage.this, InfoActivity.class);
                startActivity(intent);
            }
        });

        btn_next_lesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HiraganaHomepage.this, Lesson_Activity.class);
                startActivity(intent);
            }
        });

        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HiraganaHomepage.this, QuizActivity.class);
                startActivity(intent);
            }
        });

    }

    //Change to public if this doesn't work.
    public void onClickLoadFlashcard(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        Intent intent = new Intent(HiraganaHomepage.this, HiraganaViewer.class);
        intent.putExtra("hiragana_char", buttonText);
        startActivity(intent);
    }

    public int getNumberOfHiraganaStudied(UserProgress user_prog){
        return user_prog.getUserProgArrayLst().size();
    }

}



