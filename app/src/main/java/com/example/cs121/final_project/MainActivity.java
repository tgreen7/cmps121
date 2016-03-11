package com.example.cs121.final_project;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickGrainActivity;
import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickHopActivity;
import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickMiscActivity;
import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickYeastActivity;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(MainActivity.this, Tab1Activity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
