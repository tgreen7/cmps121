package com.example.cs121.final_project;

import android.content.Context;
import android.database.Cursor;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;

import android.database.Cursor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.cs121.final_project.Constant.FIRST_COLUMN;
import static com.example.cs121.final_project.Constant.SECOND_COLUMN;
import static com.example.cs121.final_project.Constant.THIRD_COLUMN;

public class PickHopActivity extends AppCompatActivity implements PickHopDialog.MyDialogFragmentListener {

    private ArrayList<HashMap> list;
    private ArrayList<Item> aList;
    DataBaseHelper myDbHelper;
    String[] HopType = {"", "Bittering", "Aroma", "Flavor", "Bittering/Aroma", "Aroma/Flavor", "Bittering/Flavor", "Bittering/Aroma/Flavor"};

    private static Item my_item;
    Item theitem;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_hop);

        aList = new ArrayList<Item>();

        ListView lview = (ListView) findViewById(R.id.hop_list);
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

        populateList();
        SelectHopAdapter adapter = new SelectHopAdapter(this, list);
        lview.setAdapter(adapter);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                String name = (String) map.get("First");
                String alpha = map.get("Second").toString();
                String type = (String) map.get("Third");
                System.out.println("name: " + name + "  alpha: " + alpha + " type: " + type);

                PickHopDialog cdd = new PickHopDialog(PickHopActivity.this, name, alpha, type);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });

    }

    @Override
    public void onReturnValue(String s) {
        Log.i("onReturnValue", "Got value " + s + " back from Dialog!");
    }
    public void setItem(Item hop) {
        theitem = hop;

        Log.i("onReturnValue", "HERERERE " + " back from Dialog!");
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Item", theitem);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

//    public static void make_item (Integer ing_type, Integer time, String name, String type,
//                                  String str1, String str2, String use, Float flt1, Float flt2,
//                                  Float weight, Boolean wort, Boolean dry){
//
//        my_item = new Item(ing_type, time, name, type, str1, str2, use, flt1, flt2, weight, wort, dry);
//
//        System.out.println(my_item.ing_type);
//
//
//    }

    private void populateList() {

        list = new ArrayList<HashMap>();
        Cursor cursor = myDbHelper.getReadableDatabase().query("Hops", null, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            HashMap temp = new HashMap();
            temp.put(FIRST_COLUMN, cursor.getString(1));
            temp.put(SECOND_COLUMN, cursor.getFloat(2));
            temp.put(THIRD_COLUMN, HopType[cursor.getInt(3)]);
            list.add(temp);
            cursor.moveToNext();
        }
        myDbHelper.close();
    }


}
