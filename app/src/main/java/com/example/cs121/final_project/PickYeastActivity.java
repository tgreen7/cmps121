package com.example.cs121.final_project;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.cs121.final_project.Constant.FIRST_COLUMN;
import static com.example.cs121.final_project.Constant.FOURTH_COLUMN;
import static com.example.cs121.final_project.Constant.SECOND_COLUMN;
import static com.example.cs121.final_project.Constant.THIRD_COLUMN;

public class PickYeastActivity extends AppCompatActivity {

    private ArrayList<HashMap> list;
    String[] YeastType = {"", "Ale", "Lager", "Wine", "Champagne"};
    String[] YeastForm = {"", "Liquid", "Dry"};
    DataBaseHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_yeast);
        myDbHelper = new DataBaseHelper(this);
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        }catch(SQLException sqle){
            sqle.printStackTrace();
        }

        ListView lview = (ListView) findViewById(R.id.yeast_list);

        populateList();
        SelectYeastAdapter adapter = new SelectYeastAdapter(this, list);
        lview.setAdapter(adapter);
    }

    private void populateList() {
        list = new ArrayList<HashMap>();
        Cursor cursor = myDbHelper.getReadableDatabase().query("Yeast", null, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            HashMap temp = new HashMap();
            temp.put(FIRST_COLUMN, cursor.getString(1));
            temp.put(SECOND_COLUMN, cursor.getString(2));
            temp.put(THIRD_COLUMN, YeastType[cursor.getInt(3)]);
            temp.put(FOURTH_COLUMN, YeastForm[cursor.getInt(4)]);
            list.add(temp);
            cursor.moveToNext();
        }

        myDbHelper.close();

    }
}
