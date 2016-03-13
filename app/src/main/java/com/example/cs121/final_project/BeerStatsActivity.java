package com.example.cs121.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BeerStatsActivity extends AppCompatActivity {

    TextView StatOutput;
    Double OG, FG, ABV, IBU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_stats);
        StatOutput = (TextView) findViewById(R.id.statOutput);
        Double OG = 0.0;
        Double FG = 0.0;
        Double IBU = 0.0;
        Double ABV = 0.0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        OG = DataHolder.getInstance().getOG();
        FG = DataHolder.getInstance().getFG();
        ABV = (OG - FG) * 131.25;
        IBU = DataHolder.getInstance().getIBU();
//        System.out.println("OG = " + data);
//
//        System.out.println("FG = " + data);
        StatOutput.setText(String.format("%.3f%n%.1f%n%.2f%n%.3f", OG, IBU, ABV, FG));
    }
}