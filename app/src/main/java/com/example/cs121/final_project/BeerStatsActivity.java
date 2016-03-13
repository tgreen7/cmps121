package com.example.cs121.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BeerStatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_stats);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Double data = DataHolder.getInstance().getOG();
        System.out.println("OG = " + data);
        data = DataHolder.getInstance().getFG();
        System.out.println("FG = " + data);
    }
}