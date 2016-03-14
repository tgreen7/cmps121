package com.example.cs121.final_project;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends TabActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create the TabHost that will contain the Tabs
        final TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);



        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");

        // Set the Tab name and Activity
        // that will be opened when particular Tab will be selected
        tab1.setIndicator("Recipe");
        tab1.setContent(new Intent(this, RecipeBuilder.class));

        tab2.setIndicator("Beer Stats");
        tab2.setContent(new Intent(this, BeerStatsActivity.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);

        tabHost.getTabWidget().setDividerDrawable(null);


        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.light_red));
        }


        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.gold));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String arg0) {
                for(int i=0;i<tabHost.getTabWidget().getChildCount();i++) {
                    tabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.light_red));
                }
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(getResources().getColor(R.color.gold));
            }
        });
    }

    /**
     * This launches the helpScreen activity
     * @param v the view is passed
     */
    public void helpScreen(View v) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}