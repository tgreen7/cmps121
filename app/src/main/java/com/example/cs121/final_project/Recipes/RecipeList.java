package com.example.cs121.final_project.Recipes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.backup.BackupManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cs121.final_project.Item;
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

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyRecipes", 0);
        Gson gson = new Gson();
        String json = prefs.getString("RecipeMeta", null);
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

        recipeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Log.v("long clicked", "pos: " + pos);
                MetaInfo recipe = recipes.get(pos);
                sendRecipeDialog(recipe);
                return true;
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

    /**
     * removes recipe from shared preferences
     * @param recipeName recipe to be removed
     */
    public void finishRemove(String recipeName){
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyRecipes", 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipes);
        prefsEditor.putString("RecipeMeta", json);
        prefsEditor.remove(recipeName);
        prefsEditor.apply();

//        BackupManager bm = new BackupManager(getApplicationContext());
//        bm.dataChanged();
    }

    /**
     * confirms that user really wants to remove recipe
     * @param position position of item to be removed
     * @param recipeName recipe to be removed
     * @param save info that must be put back into list if user cancels
     */
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

    public void sendRecipe(final MetaInfo recipe) {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyRecipes", 0);

        String grains = "";
        String hops = "";
        String yeasts = "";
        String misc = "";
        Gson gson = new Gson();
        String json = prefs.getString(recipe.name, null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Item>>() {}.getType();
            ArrayList<Item> values  = (ArrayList<Item>) gson.fromJson(json, type);
            if (values != null) {
                System.out.println(values.get(0).name);
                for(int i = 0; i < values.size(); i++) {
                    Item t = values.get(i);
                    switch (t.ing_type){
                        case 1: grains += "        " + t.weight + " of " + t.name + " for " + t.time + "\n";
                            break;

                        case 2: hops += "        " + t.weight + " of " + t.name + " for " + t.time + "\n";
                            break;

                        case 3: yeasts += "        " + t.weight + " of " + t.name +  "\n";
                            break;

                        case 4: misc += "        " + t.weight + " of " + t.name + " for " + t.time + "\n";
                            break;

                        default: break;
                    }
                }
            }
        }

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, recipe.name);
        String formatted_recipe =
                "Recipe: " + recipe.name + "\n" +
                        "\nInfo:" + "\n" +
                        "   Efficiency: " + recipe.effic + "\n" +
                        "   Type: " + recipe.type + "\n" +
                        "   Style: " + recipe.style + "\n" +
                        "   Boil Time: " + recipe.boilTime + "\n" +
                        "   Batch Size: " + recipe.batch + "\n" +
                        "\nIngredients:" + "\n" +
                        "   Grains:" + "\n" + grains + "\n" +
                        "   Hops:" + "\n" + hops + "\n" +
                        "   Yeasts:" + "\n" + yeasts + "\n" +
                        "   Misc:" + "\n" + misc + "\n"
                ;
        i.putExtra(Intent.EXTRA_TEXT   , formatted_recipe);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendRecipeDialog (final MetaInfo recipe) {
        new AlertDialog.Builder(this, 3)
                .setTitle("Send Recipe")
                .setMessage("Would you like to send this recipe?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(recipe.name);
                        sendRecipe(recipe);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    /**
     * closes the activity
     * @param v current view
     */
    public void closeActivity(View v) {
        finish();
    }

}
