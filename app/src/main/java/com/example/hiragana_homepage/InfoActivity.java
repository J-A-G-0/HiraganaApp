package com.example.hiragana_homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity class for displaying the Info page and all InfoEntry objects.
 *
 * @author joelgodfrey
 */

public class InfoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<InfoEntry> entriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        recyclerView = findViewById(R.id.recyclerView_info);

        initData();
        initRecyclerView();

        ImageButton back_btn = (ImageButton) findViewById(R.id.btn_back);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this,
                        HomeScreenActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerView() {
        RecyclerAdapterInfo infoAdapter = new RecyclerAdapterInfo(entriesList);
        recyclerView.setAdapter(infoAdapter);
    }

    private void initData() {
        entriesList = new ArrayList<>();
        entriesList.add(new InfoEntry("Recommended Materials:",
                getString(R.string.infoRecommendedMaterials)));
        entriesList.add(new InfoEntry("Similar-Looking Characters:",
                getString(R.string.infoStrugglingSimilar)));
        entriesList.add(new InfoEntry("Pronunciation:",
                getString(R.string.pronunciationDifficulty)));
        entriesList.add(new InfoEntry("Writing Hiragana:",
                getString(R.string.infoWriting)));
        entriesList.add(new InfoEntry("What Next? ",
                getString(R.string.infoWhatNext)));

    }
}