package com.example.cs121.final_project;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecipeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        final ListView recipeList = (ListView) findViewById(R.id.recipeList);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        ArrayList<String> recipeNames;
        Gson gson = new Gson();
        String json = prefs.getString("MyRecipeNames", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            recipeNames  = (ArrayList<String>) gson.fromJson(json, type);
        } else {
            recipeNames = new ArrayList<String>();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, recipeNames);


        // Assign adapter to ListView
        recipeList.setAdapter(adapter);

        // ListView Item Click Listener
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) recipeList.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("recipeName", itemValue);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

            }

        });
    }

}
