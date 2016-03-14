package com.example.cs121.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BeerStatsActivity extends AppCompatActivity {

    TextView StatOutput, colorOut, info1, info2;
    ImageView beerimage;
    Double OG, FG, ABV, IBU, color;
    String[] info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_stats);
        StatOutput = (TextView) findViewById(R.id.statOutput);
        colorOut = (TextView) findViewById(R.id.colorOut);
        info1 = (TextView) findViewById(R.id.info1);
        info2 = (TextView) findViewById(R.id.info2);
        beerimage = (ImageView) findViewById(R.id.beerimage);
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
        color = DataHolder.getInstance().getColor();
        info1.setText(info[0] + "\n" + info[1] + "\n" + info[2] + "\n" + info[3]);
        info2.setText(info[4] + "\n" + info[5] + "\n" + info[6]);
        colorOut.setText("Color: " + String.format("%.1f", DataHolder.getInstance().getColor()) + "SRM");
        if(color >= 40) {
            beerimage.setImageResource(R.drawable.beer_40_up);
        } else if (color >= 35) {
            beerimage.setImageResource(R.drawable.beer_35);
        } else if (color >= 29) {
            beerimage.setImageResource(R.drawable.beer_29);
        } else if (color >= 24) {
            beerimage.setImageResource(R.drawable.beer_24);
        } else if (color >= 20) {
            beerimage.setImageResource(R.drawable.beer_20);
        } else if (color >= 17) {
            beerimage.setImageResource(R.drawable.beer_17);
        } else if (color >= 13) {
            beerimage.setImageResource(R.drawable.beer_13);
        } else if (color >= 10) {
            beerimage.setImageResource(R.drawable.beer_10);
        } else if (color >= 8) {
            beerimage.setImageResource(R.drawable.beer_8);
        } else if (color >= 6) {
            beerimage.setImageResource(R.drawable.beer_6);
        } else if (color >= 4) {
            beerimage.setImageResource(R.drawable.beer_4);
        } else if (color >= 2) {
            beerimage.setImageResource(R.drawable.beer_2_3);
        }else beerimage.setImageResource(R.drawable.beer_none);
        StatOutput.setText(String.format("%.3f%n%.1f%n%.2f%n%.3f", OG, IBU, ABV, FG));
    }
}