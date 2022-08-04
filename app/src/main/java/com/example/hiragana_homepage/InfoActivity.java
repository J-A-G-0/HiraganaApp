package com.example.hiragana_homepage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<InfoEntry> entriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        recyclerView = findViewById(R.id.recyclerView_info);

        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerAdapterInfo infoAdapter = new RecyclerAdapterInfo(entriesList);
        recyclerView.setAdapter(infoAdapter);
    }

    private void initData() {
        entriesList = new ArrayList<>();
        entriesList.add(new InfoEntry("Recommended Materials:", "wah"));
        entriesList.add(new InfoEntry("Similar-Looking Characters:", "wah"));
        entriesList.add(new InfoEntry("Pronunciation:", "wah"));
        entriesList.add(new InfoEntry("Writing Hiragana:", "wah"));
        entriesList.add(new InfoEntry("What Next? ", "wah"));

    }
}