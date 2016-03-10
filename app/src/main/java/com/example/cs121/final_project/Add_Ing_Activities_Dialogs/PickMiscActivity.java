package com.example.cs121.final_project.Add_Ing_Activities_Dialogs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import static com.example.cs121.final_project.Constant.SECOND_COLUMN;
import static com.example.cs121.final_project.Constant.THIRD_COLUMN;

public class PickMiscActivity extends AppCompatActivity implements PickMiscDialog.MyDialogFragmentListener {

    private ArrayList<HashMap> list;
    DataBaseHelper myDbHelper;
    EditText searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_misc);
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
        ListView lview = (ListView) findViewById(R.id.misc_list);
        searchQuery = (EditText) findViewById(R.id.searchText);
        list = new ArrayList<HashMap>();

        populateList("");
        final SelectMiscAdapter adapter = new SelectMiscAdapter(this, list);
        lview.setAdapter(adapter);


        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                // TODO Auto-generated method stub
                String query = searchQuery.getText().toString();
                if (!query.equals("")) {
                    System.out.println(query);
                    populateList(query);
                    adapter.notifyDataSetChanged();
                } else {
                    populateList("");
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                String name = (String) map.get("First");
                String type = (String) map.get("Second");
                String use = (String) map.get("Third");

                PickMiscDialog cdd = new PickMiscDialog(PickMiscActivity.this, name, type, use);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });
    }

    public void setItem(Item hop) {
        Item theitem = hop;
        Log.i("onReturnValue", "HERERERE " + " back from Dialog!");
        Intent resultIntent = new Intent();
        resultIntent.putExtra("Item", theitem);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void populateList(String arg) {
        list.clear();

        Cursor cursor;

        if (arg.equals("")) {
            cursor = myDbHelper.getReadableDatabase().query("Misc", null, null, null, null, null, null);
        }
        else {
            String query = "SELECT * FROM Misc WHERE Name LIKE '%" + arg + "%'" ;
            cursor = myDbHelper.getReadableDatabase().rawQuery(query, null);
        }


        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            while(!cursor.isAfterLast()){
                HashMap temp = new HashMap();
                temp.put(FIRST_COLUMN, cursor.getString(1));
                temp.put(SECOND_COLUMN, cursor.getString(2));
                temp.put(THIRD_COLUMN, cursor.getString(3));
                list.add(temp);
                cursor.moveToNext();
            }
        }

        cursor.close();

        myDbHelper.close();

    }
}
