package com.example.cs121.final_project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import static com.example.cs121.final_project.Constant.FIFTH_COLUMN;
import static com.example.cs121.final_project.Constant.FIRST_COLUMN;
import static com.example.cs121.final_project.Constant.SECOND_COLUMN;
import static com.example.cs121.final_project.Constant.THIRD_COLUMN;
import static com.example.cs121.final_project.Constant.FOURTH_COLUMN;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickGrainActivity;
import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickHopActivity;
import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickMiscActivity;
import com.example.cs121.final_project.Add_Ing_Activities_Dialogs.PickYeastActivity;
import com.example.cs121.final_project.Edit_Ing_Dialogs.EditGrainDialog;
import com.example.cs121.final_project.Edit_Ing_Dialogs.EditHopDialog;
import com.example.cs121.final_project.Edit_Ing_Dialogs.EditMiscDialog;
import com.example.cs121.final_project.Edit_Ing_Dialogs.EditYeastDialog;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.OnClickWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//Meep Meep
//Meep

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
    Double oGravity, fGravity;
    Boolean undo;
    EditText name, batch_size, efficiency, boil_time;
    Spinner type, style;
    public static ImageButton sendButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);

        sendButton = (ImageButton) findViewById(R.id.sendButton);
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

            updateGravity();

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

    public static void hideSendButton() {
        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 150); //first 0 is start point, 150 is end point horizontal
        anim.setDuration(250); // 1000 ms = 1second
        sendButton.startAnimation(anim);
        sendButton.setVisibility(View.GONE);
    }
    public static void showSendButton() {
        TranslateAnimation anim = new TranslateAnimation(0, 0, 150, 0); //first 0 is start point, 150 is end point horizontal
        anim.setDuration(250); // 1000 ms = 1second
        sendButton.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendButton.setVisibility(View.VISIBLE);
            }
        }, 250);
    }
//    private void showSendButtonImm() {
//        sendButton.setVisibility(View.VISIBLE);
//    }

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

//    boolean willMyListScroll() {
//        int pos = lview.getLastVisiblePosition();
//        if(lview.getChildAt(pos) == null) return false;
//        return((lview.getChildAt(pos).getBottom() > lview.getHeight()));
//    }


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
        updateGravity();
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
        updateIBUs();
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
                                    efficiency.setText(rec.effic.toString());
                                    boil_time.setText(rec.boilTime.toString());
                                    batch_size.setText(rec.boilTime.toString());

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
                                }
                            }
                            System.out.println(values.get(0).name);
                            EditText nameT = (EditText) findViewById(R.id.recipeName);
                            nameT.setText(recipeName);
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
        }
        if(itemList.isEmpty()){
            Toast.makeText(this, "Please add at least once ingredient to your recipe.", Toast.LENGTH_LONG).show();
            return;

        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor prefsEditor = prefs.edit();

        MetaInfo info = new MetaInfo(name.getText().toString(), Double.parseDouble(efficiency.getText().toString()),
                type.getSelectedItem().toString(), style.getSelectedItem().toString(),
                Double.parseDouble(boil_time.getText().toString()), Double.parseDouble(batch_size.getText().toString()));

//        use.setSelection(0);
//        while(!(use_text.equals(use.getSelectedItem().toString()))) {
//            use.setSelection((i+1));
//            i++;
//        }

        ArrayList<MetaInfo> recipeNames;
        Gson gson = new Gson();
        String json = prefs.getString("MyRecipeNames", null);
        boolean in = false;
        if (json != null) {
            Type type = new TypeToken<ArrayList<MetaInfo>>() {}.getType();
            recipeNames  = (ArrayList<MetaInfo>) gson.fromJson(json, type);
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
        prefsEditor.commit();
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
    public void updateGravity(){
        Double total = 0.0;
        Double attenu = 0.0;
        Integer attcount = 0;
        for(int i = itemList.size() - 1; i >= 0; i--){
            Item item = itemList.get(i);
            if(item.ing_type == 1){
                Double result = item.dbl2 * 1000;
                result -= 1000;
                result *= (item.weight / 16);
                total += result;
            }
            if(item.ing_type == 3){
                attenu += item.dbl2;
                attcount++;
            }
        }

        System.out.println("Points = " + total);
        if (total == 0) {
            oGravity = 1.0;
            DataHolder.getInstance().setOG(1.0);
            updateIBUs();
        }else{
            Double eff;
            if(efficiency.getText().toString().equals("")) eff = 0.0;
            else eff = Double.parseDouble(efficiency.getText().toString());
            total *= (eff / 100);
            System.out.println("After Eff = " + total);
            if (batch_size.getText().toString().equals("")) total = 0.0;
            else total /= Double.parseDouble(batch_size.getText().toString());
            System.out.println("After Ferm = " + total);
            total = (total / 1000) + 1;
            oGravity = total;
        }

        if (attenu == 0) fGravity = oGravity;
        else {
            Double attresult = 1 - ((attenu / attcount)/100);
            total = (total - 1) * 1000;
            total *= attresult;
            total = (total / 1000) + 1;
            fGravity = total;
        }
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
                                    superActivityToast.setDuration(SuperToast.Duration.EXTRA_LONG);
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
                updateGravity();
            }
        };

        batch_size.addTextChangedListener(change);
        efficiency.addTextChangedListener(change);
    }

}
