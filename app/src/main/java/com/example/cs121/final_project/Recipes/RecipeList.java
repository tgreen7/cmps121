package com.example.cs121.final_project.Recipes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cs121.final_project.R;
import com.example.cs121.final_project.SwipeDismissListViewTouchListener;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecipeList extends AppCompatActivity {

    MetaInfo saveItem;
    String saveName;
    ArrayList<MetaInfo> recipes;
    RecipeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        final ListView recipeList = (ListView) findViewById(R.id.recipeList);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString("MyRecipeNames", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<MetaInfo>>() {}.getType();
            recipes  = (ArrayList<MetaInfo>) gson.fromJson(json, type);
        } else {
            recipes = new ArrayList<MetaInfo>();
        }


        adapter = new RecipeListAdapter(this, recipes);
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



        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        recipeList,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (final int position : reverseSortedPositions) {
                                    saveName = recipes.get(position).name;
                                    saveItem = recipes.get(position);
                                    recipes.remove(position);
                                    adapter.notifyDataSetChanged();
                                    remove(position, saveName, saveItem);
                                }
                            }
                        });

        recipeList.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recipeList.setOnScrollListener(touchListener.makeScrollListener());
    }

    public void finishRemove(String recipeName){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipes);
        prefsEditor.putString("MyRecipeNames", json);
        prefsEditor.remove(recipeName);
        prefsEditor.apply();
    }

    public void remove(final int position, final String recipeName, final MetaInfo save) {
        new AlertDialog.Builder(this, 3)
                .setTitle("Delete Recipe")
                .setMessage("Are you Sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishRemove(recipeName);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        recipes.add(position, save);
                        adapter.notifyDataSetChanged();
                    }
                })
                .show();
    }

    public void closeActivity(View v) {
        finish();
    }

}
