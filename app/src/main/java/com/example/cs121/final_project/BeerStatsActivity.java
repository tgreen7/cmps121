package com.example.cs121.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BeerStatsActivity extends AppCompatActivity {

    TextView StatOutput, colorOut, info1, info2;
    Double OG, FG, ABV, IBU;
    String[] info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_stats);
        StatOutput = (TextView) findViewById(R.id.statOutput);
        colorOut = (TextView) findViewById(R.id.colorOut);
        info1 = (TextView) findViewById(R.id.info1);
        info2 = (TextView) findViewById(R.id.info2);
        FrameLayout text = (FrameLayout) findViewById(R.id.frame1);
        text.setVisibility(View.VISIBLE);
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
        info = DataHolder.getInstance().getMainInfo();
        info1.setText(info[0] + "\n" + info[1] + "\n" + info[2] + "\n" + info[3]);
        info2.setText(info[4] + "\n" + info[5] + "\n" + info[6]);
        StatOutput.setText(String.format("%.3f%n%.1f%n%.2f%n%.3f", OG, IBU, ABV, FG));
    }
}