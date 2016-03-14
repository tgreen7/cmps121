package com.example.cs121.final_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import static com.example.cs121.final_project.Constant.FIFTH_COLUMN;
import static com.example.cs121.final_project.Constant.FIRST_COLUMN;
import static com.example.cs121.final_project.Constant.SECOND_COLUMN;
import static com.example.cs121.final_project.Constant.THIRD_COLUMN;
import static com.example.cs121.final_project.Constant.FOURTH_COLUMN;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickGrainActivity;
import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickHopActivity;
import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickMiscActivity;
import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickYeastActivity;
import com.example.cs121.final_project.Edit_Ing_Dialogs.EditGrainDialog;
import com.example.cs121.final_project.Edit_Ing_Dialogs.EditHopDialog;
import com.example.cs121.final_project.Edit_Ing_Dialogs.EditMiscDialog;
import com.example.cs121.final_project.Edit_Ing_Dialogs.EditYeastDialog;
import com.example.cs121.final_project.Recipes.MetaInfo;
import com.example.cs121.final_project.Recipes.RecipeList;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.OnClickWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Tab1Activity extends Activity
        implements EditGrainDialog.MyDialogFragmentListener, EditHopDialog.MyDialogFragmentListener,
        EditYeastDialog.MyDialogFragmentListener, EditMiscDialog.MyDialogFragmentListener,
        DialogInterface.OnDismissListener
{
    private ArrayList<HashMap> list;
    private ArrayList<Item> itemList;

    private ListViewAdapter adapter;

    public static Activity main_activity;
    private int index;
    SuperActivityToast superActivityToast;
    OnClickWrapper onClickWrapper;
    ListView lview;
    Item previtem;
    Integer prevpos;
    Double oGravity, fGravity, color;
    Boolean undo;
    EditText name, batch_size, efficiency, boil_time;
    Spinner type, style;
    public static ImageButton saveButton, recipesButton, clearButton;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);

//        this can be used to wipe shared preferences
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
//        prefs.edit().clear().apply();

        saveButton = (ImageButton) findViewById(R.id.saveButton);
        recipesButton = (ImageButton) findViewById(R.id.showRecipesButton);
        clearButton = (ImageButton) findViewById(R.id.clearButton);
        efficiency = (EditText) findViewById(R.id.efficiency);
        boil_time = (EditText) findViewById(R.id.boil_time);
        batch_size = (EditText) findViewById(R.id.batch);
        name = (EditText) findViewById(R.id.recipeName);
        type = (Spinner) findViewById(R.id.spinner_types);
        style = (Spinner) findViewById(R.id.spinner_styles);

        main_activity = this;
        undo = false;
        lview = (ListView) findViewById(R.id.listView);
        list = new ArrayList<HashMap>();
        adapter = new ListViewAdapter(this, list);
        lview.setAdapter(adapter);

        if (savedInstanceState != null) {
            ArrayList<Item> values = (ArrayList<Item>) savedInstanceState.getSerializable("myItems");
            if (values != null) {
                repopulateList(values);
                itemList = values;
            }
        } else {
            itemList = new ArrayList<Item>();

            ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(this,
                    R.array.beer_types_array, android.R.layout.simple_spinner_item);
            spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            type.setAdapter(spin_adapter);


            spin_adapter = ArrayAdapter.createFromResource(this,
                    R.array.beer_styles_array, android.R.layout.simple_spinner_item);
            spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            style.setAdapter(spin_adapter);

            efficiency.setText("70");
            boil_time.setText("60");
            batch_size.setText("5.00");

            updateData();
            setListeners();
        }
    }

    private void repopulateList(ArrayList<Item> values) {
        itemList.clear();
        list.clear();
        for(int i = 0; i < values.size(); i++) {
            Item t = values.get(i);
            switch (t.ing_type){
                case 1: putGrain(t, false);
                    break;

                case 2: putHop(t, false);
                    break;

                case 3: putYeast(t, false);
                    break;

                case 4: putMisc(t, false);
                    break;

                default: break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void relaunchDialog (View view) {

        new AlertDialog.Builder(this, 3)
                .setTitle("Clear Recipe")
                .setMessage("All unsaved changes will be lost.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        relaunch();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();

    }
    private void relaunch() {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }


//    code for hiding and showing buttons
    static boolean hidden = false;
    static boolean showing = false;
    public static void hideSendButton() {
        if(hidden) return;
        hidden = true;
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 250); //first 0 is start point, 150 is end point horizontal
        anim.setDuration(250); // 1000 ms = 1second
        saveButton.startAnimation(anim);
        recipesButton.startAnimation(anim);
        clearButton.startAnimation(anim);

        saveButton.setVisibility(View.GONE);
        recipesButton.setVisibility(View.GONE);
        clearButton.setVisibility(View.GONE);
    }
    public static void showSendButtonDelayed() {
        if(!hidden) return;
        if(showing) return;
        showing = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showSendButton();
            }
        }, 1000);
    }
    public static void showSendButton() {
        TranslateAnimation anim = new TranslateAnimation(0, 0, 250, 0); //first 0 is start point, 150 is end point horizontal
        anim.setDuration(250); // 1000 ms = 1second
        saveButton.startAnimation(anim);
        recipesButton.startAnimation(anim);
        clearButton.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                saveButton.setVisibility(View.VISIBLE);
                recipesButton.setVisibility(View.VISIBLE);
                clearButton.setVisibility(View.VISIBLE);
                hidden = false;
                showing = false;
            }
        }, 250);
    }

    public void onSaveInstanceState(Bundle savedState) {

        super.onSaveInstanceState(savedState);

        // Note: getValues() is a method in your ArrayAdapter subclass

        savedState.putSerializable("myItems", itemList);

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        // Do whatever
        closeInput(getWindow().getDecorView());
    }


    public static void closeInput(final View caller) {
        caller.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) caller.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(caller.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
    }

    public void putGrain(Item newItem, boolean edit) {
        HashMap newRow = new HashMap();
        String[] dataEntry = parseGrain(newItem.weight, newItem.time);
        newRow.put(FIRST_COLUMN, dataEntry[0]);
        newRow.put(SECOND_COLUMN, newItem.name);
        newRow.put(THIRD_COLUMN, newItem.type);
        newRow.put(FOURTH_COLUMN, dataEntry[1]);
        newRow.put(FIFTH_COLUMN, "n/a");


        if(edit) {
            itemList.set(index, newItem);
            list.set(index, newRow);
        }else if(undo){
            itemList.add(prevpos, newItem);
            list.add(prevpos, newRow);
            undo = false;
        } else {
            itemList.add(newItem);
            list.add(newRow);
        }
        updateData();
    }

    public void putHop(Item newItem, boolean edit) {
        HashMap newRow = new HashMap();

        String[] dataEntry = parseHop(newItem.weight, newItem.time);
        Double IBU = parseIBU(newItem.weight, newItem.time, newItem.dbl1);
        newRow.put(FIRST_COLUMN, dataEntry[0]);
        newRow.put(SECOND_COLUMN, newItem.name);
        newRow.put(THIRD_COLUMN, newItem.type);
        newRow.put(FOURTH_COLUMN, dataEntry[1]);
        newRow.put(FIFTH_COLUMN, String.format("%.1f", IBU));

        if(edit) {
            itemList.set(index, newItem);
            list.set(index, newRow);
        }else if(undo){
            itemList.add(prevpos, newItem);
            list.add(prevpos, newRow);
            undo = false;
        } else {
            itemList.add(newItem);
            list.add(newRow);
        }
        updateIBUs();
    }

    public void putYeast(Item newItem, boolean edit) {
        HashMap newRow = new HashMap();
        newRow.put(FIRST_COLUMN, newItem.weight + " " + newItem.str3);
        newRow.put(SECOND_COLUMN, newItem.name);
        newRow.put(THIRD_COLUMN, newItem.type);
        newRow.put(FOURTH_COLUMN, "-");
        newRow.put(FIFTH_COLUMN, "n/a");

        if (edit) {
            itemList.set(index, newItem);
            list.set(index, newRow);
        }else if(undo){
            itemList.add(prevpos, newItem);
            list.add(prevpos, newRow);
            undo = false;
        } else {
            itemList.add(newItem);
            list.add(newRow);
        }
        updateData();
    }

    public void putMisc (Item newItem, boolean edit) {
        HashMap newRow = new HashMap();

        newRow.put(FIRST_COLUMN, newItem.weight + " " + newItem.str2);
        newRow.put(SECOND_COLUMN, newItem.name);
        newRow.put(THIRD_COLUMN, newItem.type);
        if (newItem.time >= 1440) {
            int days = newItem.time/1440;
            newRow.put(FOURTH_COLUMN, days + " days");
        } else newRow.put(FOURTH_COLUMN, newItem.time + " min");
        newRow.put(FIFTH_COLUMN, "n/a");

        if (edit) {
            itemList.set(index, newItem);
            list.set(index, newRow);
        }else if(undo){
            itemList.add(prevpos, newItem);
            list.add(prevpos, newRow);
            undo = false;
        } else {
            itemList.add(newItem);
            list.add(newRow);
        }
        adapter.notifyDataSetChanged();
    }



    public void startGrain(View view) {
        Intent intent = new Intent(this, PickGrainActivity.class);
        startActivityForResult(intent, 1);
    }
    public void startHop(View view) {
        Intent intent = new Intent(this, PickHopActivity.class);
        startActivityForResult(intent, 2);
    }
    public void startYeast(View view) {
        Intent intent = new Intent(this, PickYeastActivity.class);
        startActivityForResult(intent, 3);
    }
    public void startMisc(View view) {
        Intent intent = new Intent(this, PickMiscActivity.class);
        startActivityForResult(intent, 4);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    Item newItem = (Item) data.getSerializableExtra("Item");
                    putGrain(newItem, false);
                    closeInput(getWindow().getDecorView());
                }
                break;
            }
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    Item newItem = (Item) data.getSerializableExtra("Item");
                    putHop(newItem, false);
                    closeInput(getWindow().getDecorView());
                }
                break;
            }
            case (3) : {
                if (resultCode == Activity.RESULT_OK) {
                    Item newItem = (Item) data.getSerializableExtra("Item");
                    putYeast(newItem, false);
                    closeInput(getWindow().getDecorView());
                }
                break;
            }
            case (4) : {
                if (resultCode == Activity.RESULT_OK) {
                    Item newItem = (Item) data.getSerializableExtra("Item");
                    putMisc(newItem, false);
                    closeInput(getWindow().getDecorView());
                }
                break;
            }
            case(5) : {
                if (resultCode == Activity.RESULT_OK) {
                    String recipeName = data.getStringExtra("recipeName");
                    System.out.println(recipeName);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

                    Gson gson = new Gson();
                    String json = prefs.getString("MyRecipeNames", null);
                    if (json != null) {
                        Type t = new TypeToken<ArrayList<MetaInfo>>() {}.getType();
                        ArrayList<MetaInfo> values  = (ArrayList<MetaInfo>) gson.fromJson(json, t);
                        if (values != null) {
                            for (MetaInfo rec : values){
                                if (recipeName.equals(rec.name)){
                                    name.setText(rec.name);
                                    efficiency.setText(String.format("%.1f", rec.effic));
                                    boil_time.setText( String.format("%.0f", rec.boilTime));
                                    batch_size.setText(String.format("%.2f", rec.batch));

                                    type.setSelection(0);
                                    int i = 0;
                                    while(!(rec.type.equals(type.getSelectedItem().toString()))) {
                                        type.setSelection((i+1));
                                        i++;
                                    }
                                    style.setSelection(0);
                                    i = 0;
                                    while(!(rec.style.equals(style.getSelectedItem().toString()))) {
                                        style.setSelection((i+1));
                                        i++;
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    gson = new Gson();
                    json = prefs.getString(recipeName, null);
                    if (json != null) {
                        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
                        ArrayList<Item> values  = (ArrayList<Item>) gson.fromJson(json, type);
                        if (values != null) {
                            System.out.println(values.get(0).name);
                            repopulateList(values);
                            EditText nameT = (EditText) findViewById(R.id.recipeName);
                            nameT.setText(recipeName);
                        }
                    }
                }
                break;
            }
        }
    }


    public String[] parseGrain(Double weight, int time){
        String[] result = new String[2];
        int lb = 0;
        while (weight >= 16){
            lb += 1;
            weight -= 16;
        }
        if (lb == 0) result[0] = String.format("%.1f oz", weight);
        else if (weight == 0) result[0] = lb + " lb";
        else result[0] = lb + " lb \n" + String.format("%.1f oz", weight);

        if (time >= 1440) {
            int days = time/1440;
            result[1] = days + " days";
        } else result[1] = time + " min";
        return result;
    }

    public String[] parseHop(Double weight, int time){
        String[] result = new String[2];
        if (weight >= 28.3495){
            Double oz = weight / 28.3495;
            result[0] = String.format("%.1f oz", oz);
        }else result[0] = weight + " g";

        if (time >= 1440) {
            int days = time/1440;
            result[1] = days + " days";
        } else result[1] = time + " min";
        return result;
    }

    public void launchRecipes(View view) {
        Intent intent = new Intent(this, RecipeList.class);
        startActivityForResult(intent, 5);
    }

    public void saveText (View view) {

        if(name.getText().toString().equals("")){
            Toast.makeText(this, "Please enter a name for your recipe.", Toast.LENGTH_LONG).show();
            return;
        } else if(itemList.isEmpty()){
            Toast.makeText(this, "Please add at least once ingredient to your recipe.", Toast.LENGTH_LONG).show();
            return;
        } else if (efficiency.getText().toString().equals("")) {
            Toast.makeText(this, "Please give your recipe an efficiency", Toast.LENGTH_LONG).show();
            return;
        } else if(boil_time.getText().toString().equals("")){
            Toast.makeText(this, "Please set the boil time of your recipe", Toast.LENGTH_LONG).show();
            return;
        } else if (batch_size.getText().toString().equals("")) {
            Toast.makeText(this, "Please give your recipe a batch size.", Toast.LENGTH_LONG).show();
            return;
        }


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = prefs.edit();

        MetaInfo info = new MetaInfo(name.getText().toString(), Double.parseDouble(efficiency.getText().toString()),
                type.getSelectedItem().toString(), style.getSelectedItem().toString(),
                Double.parseDouble(boil_time.getText().toString()), Double.parseDouble(batch_size.getText().toString()), color);

        ArrayList<MetaInfo> recipeNames;
        Gson gson = new Gson();
        String json = prefs.getString("MyRecipeNames", null);
        boolean in = false;
        if (json != null) {
            Type type = new TypeToken<ArrayList<MetaInfo>>() {}.getType();
            recipeNames  = gson.fromJson(json, type);
            if (recipeNames != null) {
                for(int i = 0; i < recipeNames.size(); i++) {
                    if (recipeNames.get(i).name.equals(info.name)) {
                        recipeNames.set(i, info);
                        Toast.makeText(this, "Your recipe has been updated.", Toast.LENGTH_SHORT).show();
                        in = true;
                        break;
                    }
                }
                if(!in) {
                    recipeNames.add(info);
                }
            }
        } else {
            recipeNames = new ArrayList<MetaInfo>();
            recipeNames.add(info);
        }
        if (!in) {
            Toast.makeText(this, "Your recipe has been saved.", Toast.LENGTH_SHORT).show();
        }

        gson = new Gson();
        json = gson.toJson(recipeNames);
        prefsEditor.putString("MyRecipeNames", json);

        gson = new Gson();
        json = gson.toJson(itemList);
        prefsEditor.putString(info.name, json);
        prefsEditor.apply();
    }

    public Double parseIBU(Double weight, Integer time, Double dbl1) {
        Double eq1 = Math.pow(0.000125, (oGravity-1));
        eq1 *= 1.65;
        Double eq2 = 1 - (Math.pow(Math.E, (-0.04*time)));
        eq2 /= 4.15;
        Double eq3 = (dbl1 / 100) * (weight / 28.3495) * 7490;
        if (batch_size.getText().toString().equals("")) eq3 = 0.0;
        else eq3 /= Double.parseDouble(batch_size.getText().toString());
        return (eq1 * eq2 * eq3);
    }

    public void updateIBUs(){
        Double total = 0.0;
        for(int i = itemList.size() - 1; i >= 0; i--){
            Item item = itemList.get(i);
            if(item.ing_type == 2){
                Double IBU = parseIBU(item.weight, item.time, item.dbl1);
                list.get(i).put(FIFTH_COLUMN, String.format("%.1f", IBU));
                total += IBU;
            }
        }
        adapter.notifyDataSetChanged();
        DataHolder.getInstance().setIBU(total);
    }
    public void updateData(){
        Double gTotal = 0.0;
        Double aTotal = 0.0;
        Double cTotal = 0.0;
        Integer aAverage = 0;
        for(int i = itemList.size() - 1; i >= 0; i--){
            Item item = itemList.get(i);
            if(item.ing_type == 1){
                Double result = item.dbl2 * 1000;
                result -= 1000;
                result *= (item.weight / 16);
                gTotal += result;
                if (batch_size.getText().toString().equals("")) cTotal += 0.0;
                else cTotal += (item.dbl1 * (item.weight / 16))/Double.parseDouble(batch_size.getText().toString());
            }
            if(item.ing_type == 3){
                aTotal += item.dbl2;
                aAverage++;
            }
        }

        System.out.println("Points = " + gTotal);
        if (gTotal == 0) {
            oGravity = 1.0;
            DataHolder.getInstance().setOG(1.0);
            updateIBUs();
        }else{
            Double eff;
            if(efficiency.getText().toString().equals("")) eff = 0.0;
            else eff = Double.parseDouble(efficiency.getText().toString());
            gTotal *= (eff / 100);
            System.out.println("After Eff = " + gTotal);
            if (batch_size.getText().toString().equals("")) gTotal = 0.0;
            else gTotal /= Double.parseDouble(batch_size.getText().toString());
            System.out.println("After Ferm = " + gTotal);
            gTotal = (gTotal / 1000) + 1;
            oGravity = gTotal;
        }

        if (aTotal == 0) fGravity = oGravity;
        else {
            Double attresult = 1 - ((aTotal / aAverage)/100);
            gTotal = (gTotal - 1) * 1000;
            gTotal *= attresult;
            gTotal = (gTotal / 1000) + 1;
            fGravity = gTotal;
        }
        cTotal = Math.pow(cTotal, 0.6859);
        color = cTotal * 1.4922;
        DataHolder.getInstance().setColor(color);
        DataHolder.getInstance().setFG(fGravity);
        DataHolder.getInstance().setOG(oGravity);
        updateIBUs();

    }
    private void setListeners() {
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item myItem = itemList.get(position);
                index = position;

                switch (myItem.ing_type) {
                    case 1: {
                        EditGrainDialog cdd = new EditGrainDialog(Tab1Activity.this, myItem);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.setOnDismissListener(Tab1Activity.this);
                        cdd.show();
                        break;
                    }
                    case 2: {
                        EditHopDialog cdd = new EditHopDialog(Tab1Activity.this, myItem);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.setOnDismissListener(Tab1Activity.this);
                        cdd.show();
                        break;
                    }
                    case 3: {
                        EditYeastDialog cdd = new EditYeastDialog(Tab1Activity.this, myItem);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.setOnDismissListener(Tab1Activity.this);
                        cdd.show();
                        break;
                    }
                    case 4: {
                        EditMiscDialog cdd = new EditMiscDialog(Tab1Activity.this, myItem);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.setOnDismissListener(Tab1Activity.this);
                        cdd.show();
                        break;
                    }
                    default:
                        break;
                }
            }
        });
        onClickWrapper = new OnClickWrapper("superactivitytoast", new SuperToast.OnClickListener() {

            @Override
            public void onClick(View view, Parcelable token) {
                undo = true;
                switch (previtem.ing_type) {
                    case 1:
                        putGrain(previtem, false);
                        break;
                    case 2:
                        putHop(previtem, false);
                        break;
                    case 3:
                        putYeast(previtem, false);
                        break;
                    case 4:
                        putMisc(previtem, false);
                        break;
                }
            }

        });

        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        lview,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                if (superActivityToast != null)
                                    superActivityToast.dismiss();
                                for (int position : reverseSortedPositions) {
                                    previtem = itemList.get(position);
                                    prevpos = position;
                                    itemList.remove(position);
                                    list.remove(position);
                                    superActivityToast = new SuperActivityToast(Tab1Activity.this, SuperToast.Type.BUTTON);
                                    superActivityToast.setDuration(SuperToast.Duration.LONG);
                                    superActivityToast.setText("Ingredient Removed");
                                    superActivityToast.setButtonIcon(SuperToast.Icon.Dark.UNDO, "UNDO");
                                    superActivityToast.setOnClickWrapper(onClickWrapper);
                                    superActivityToast.show();
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });

        lview.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        lview.setOnScrollListener(touchListener.makeScrollListener());

        TextWatcher change = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                updateData();
            }
        };

        batch_size.addTextChangedListener(change);
        efficiency.addTextChangedListener(change);
    }

}
