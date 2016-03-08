package com.example.cs121.final_project;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third tab");
        TabHost.TabSpec tab4 = tabHost.newTabSpec("Fourth tab");
        TabHost.TabSpec tab5 = tabHost.newTabSpec("Fifth tab");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Recipe");
        tab1.setContent(new Intent(this,Tab1Activity.class));

        tab2.setIndicator("select grain");
        tab2.setContent(new Intent(this,PickGrainActivity.class));

        tab3.setIndicator("select hop");
        tab3.setContent(new Intent(this,PickHopActivity.class));

        tab4.setIndicator("select yeast");
        tab4.setContent(new Intent(this,PickYeastActivity.class));

        tab5.setIndicator("select misc");
        tab5.setContent(new Intent(this,PickMiscActivity.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);
        tabHost.addTab(tab5);

    }
}
