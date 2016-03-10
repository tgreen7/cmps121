package com.example.cs121.final_project.Add_Ing_Activities_Dialogs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cs121.final_project.DataBaseHelper;
import com.example.cs121.final_project.Item;
import com.example.cs121.final_project.R;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.cs121.final_project.Constant.FIRST_COLUMN;
import static com.example.cs121.final_project.Constant.FOURTH_COLUMN;
import static com.example.cs121.final_project.Constant.SECOND_COLUMN;
import static com.example.cs121.final_project.Constant.THIRD_COLUMN;

public class PickGrainActivity extends AppCompatActivity implements PickGrainDialog.MyDialogFragmentListener {

    private ArrayList<HashMap> list;
    DataBaseHelper myDbHelper;
    String[] GrainType = {"", "Grain", "Dry Extract", "Liquid Extract", "Adjunct", "Sugar"};
    Item theitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_grain);
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

        ListView lview = (ListView) findViewById(R.id.grain_list);

        populateList();
        SelectGrainAdapter adapter = new SelectGrainAdapter(this, list);
        lview.setAdapter(adapter);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                String name = (String) map.get("First");
                String type = (String) map.get("Second");
                String color = map.get("Third").toString();
                color = color.replace("L", "");
                String potential = map.get("Fourth").toString();

                PickGrainDialog cdd = new PickGrainDialog(PickGrainActivity.this, name, type, color, potential);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });
    }

    public void setItem(Item hop) {
        theitem = hop;
        Log.i("onReturnValue", "HERERERE " + " back from Dialog!");
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Item", theitem);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void populateList() {

        list = new ArrayList<HashMap>();
        Cursor cursor = myDbHelper.getReadableDatabase().query("Grains", null, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            HashMap temp = new HashMap();
            temp.put(FIRST_COLUMN, cursor.getString(1));
            temp.put(SECOND_COLUMN, GrainType[cursor.getInt(2)]);
            temp.put(THIRD_COLUMN, cursor.getString(3) + "L");
            temp.put(FOURTH_COLUMN, cursor.getFloat(4));
            list.add(temp);
            cursor.moveToNext();
        }

        myDbHelper.close();

    }
}
