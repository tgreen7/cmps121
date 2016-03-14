package com.example.cs121.final_project.Recipes;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cs121.final_project.R;
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
        ArrayList<MetaInfo> recipes;
        Gson gson = new Gson();
        String json = prefs.getString("MyRecipeNames", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<MetaInfo>>() {}.getType();
            recipes  = (ArrayList<MetaInfo>) gson.fromJson(json, type);
        } else {
            recipes = new ArrayList<MetaInfo>();
        }


        final RecipeListAdapter adapter = new RecipeListAdapter(this, recipes);
        recipeList.setAdapter(adapter);

        // ListView Item Click Listener
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item value
                String itemValue = ((MetaInfo) recipeList.getItemAtPosition(position)).name;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("recipeName", itemValue);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    public void closeActivity(View v) {
        finish();
    }

}
