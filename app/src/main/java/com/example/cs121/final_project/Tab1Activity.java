package com.example.cs121.final_project;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static com.example.cs121.final_project.Constant.FIFTH_COLUMN;
import static com.example.cs121.final_project.Constant.FIRST_COLUMN;
import static com.example.cs121.final_project.Constant.SECOND_COLUMN;
import static com.example.cs121.final_project.Constant.THIRD_COLUMN;
import static com.example.cs121.final_project.Constant.FOURTH_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 *
 * @author Paresh N. Mayani
 */
public class Tab1Activity extends Activity
{
    private ArrayList<HashMap> list;
    private ArrayList<Item> aList;

    private EditText name;
    private ListViewAdapter adapter;

    public static Activity main_activity;
    static Item my_item;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);

        main_activity = this;

        ListView lview = (ListView) findViewById(R.id.listView);
        list = new ArrayList<HashMap>();
        aList = new ArrayList<Item>();
        populateList();
        adapter = new ListViewAdapter(this, list);
        lview.setAdapter(adapter);


        Spinner spinner = (Spinner) findViewById(R.id.spinner_types);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(this,
                R.array.beer_types_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(spin_adapter);


        spinner = (Spinner) findViewById(R.id.spinner_styles);
// Create an ArrayAdapter using the string array and a default spinner layout
        spin_adapter = ArrayAdapter.createFromResource(this,
                R.array.beer_styles_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        spin_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(spin_adapter);


        EditText efficiency =(EditText) findViewById(R.id.efficiency);
        EditText boil_time =(EditText) findViewById(R.id.boil_time);
        EditText batch_size =(EditText) findViewById(R.id.batch);

        name = (EditText) findViewById(R.id.recipeName);

        efficiency.setText("70");
        boil_time.setText("60");
        batch_size.setText("5.00");


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
        startActivity(intent);
    }
    public void startMisc(View view) {
        Intent intent = new Intent(this, PickMiscActivity.class);
        startActivity(intent);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        InputMethodManager inputManager = (InputMethodManager)
//                getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                InputMethodManager.HIDE_NOT_ALWAYS);

        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    Item newItem = (Item) data.getSerializableExtra("Item");
                    HashMap newRow = new HashMap();
                    String[] dataEntry = parseGrain(newItem.weight, newItem.time);
                    newRow.put(FIRST_COLUMN, dataEntry[0]);
                    newRow.put(SECOND_COLUMN, newItem.name);
                    newRow.put(THIRD_COLUMN, newItem.type);
                    newRow.put(FOURTH_COLUMN, dataEntry[1]);
                    newRow.put(FIFTH_COLUMN, "n/a");
                    aList.add(newItem);
                    list.add(newRow);
                }
            }
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    Item newItem = (Item) data.getSerializableExtra("Item");
                    HashMap newRow = new HashMap();
                    String[] dataEntry = parseHop(newItem.weight, newItem.time);
                    newRow.put(FIRST_COLUMN, dataEntry[0]);
                    newRow.put(SECOND_COLUMN, newItem.name);
                    newRow.put(THIRD_COLUMN, newItem.type);
                    newRow.put(FOURTH_COLUMN, dataEntry[1]);
                    newRow.put(FIFTH_COLUMN, "n/a");
                    aList.add(newItem);
                    list.add(newRow);
                }
                break;

            }
        }

        adapter.notifyDataSetChanged();

    }

    public String[] parseGrain(Float weight, int time){
        String[] result = new String[2];
        int lb = 0;
        Float oz = 0f;
        while (weight >= 16){
            lb += 1;
            weight -= 16;
        }
        if (lb == 0) result[0] = String.format("%.2f oz", oz);
        else if (weight == 0) result[0] = lb + " lb";
        else result[0] = lb + " lb " + String.format("%.1f oz", oz);

        if (time >= 1440) {
            int days = time/1440;
            result[1] = days + " days";
        } else result[1] = time + " min";
        return result;
    }

    public String[] parseHop(Float weight, int time){
        String[] result = new String[2];
        if (weight >= 28.3495f){
            Float oz = weight / 28.3495f;
            result[0] = String.format("%.2f oz", oz);
        }else result[0] = weight + " g";

        if (time >= 1440) {
            int days = time/1440;
            result[1] = days + " days";
        } else result[1] = time + " min";
        return result;
    }

    public static void make_item (Integer ing_type, Integer time, String name, String type,
                                  String str1, String str2, String use, Float flt1, Float flt2,
                                  Float weight, Boolean wort, Boolean dry){

        my_item = new Item(ing_type, time, name, type, str1, str2, use, flt1, flt2, weight, wort, dry);

        System.out.println(my_item.ing_type);

    }

    private void populateList() {

        list = new ArrayList<HashMap>();

//        HashMap temp = new HashMap();
//        temp.put(FIRST_COLUMN,"3 pkg");
//        temp.put(SECOND_COLUMN, "By NavNeet");
//        temp.put(THIRD_COLUMN, "Rs. 200");
//        temp.put(FOURTH_COLUMN, "Per Unit");
//        temp.put(FIFTH_COLUMN, "75.6");
//        list.add(temp);
//
//        HashMap temp1 = new HashMap();
//        temp1.put(FIRST_COLUMN,"4 oz");
//        temp1.put(SECOND_COLUMN, "By Amee Products");
//        temp1.put(THIRD_COLUMN, "Rs. 400");
//        temp1.put(FOURTH_COLUMN, "Per Unit");
//        temp1.put(FIFTH_COLUMN, "75.6");
//        list.add(temp1);
//
//        HashMap temp2 = new HashMap();
//        temp2.put(FIRST_COLUMN,"10 grams");
//        temp2.put(SECOND_COLUMN, "By National Products");
//        temp2.put(THIRD_COLUMN, "Rs. 600");
//        temp2.put(FOURTH_COLUMN, "Per Unit");
//        temp2.put(FIFTH_COLUMN, "75.6");
//        list.add(temp2);
//
//        HashMap temp3 = new HashMap();
//        temp3.put(FIRST_COLUMN,"6.73 oz");
//        temp3.put(SECOND_COLUMN, "By Devarsh Prakashan");
//        temp3.put(THIRD_COLUMN, "Rs. 800");
//        temp3.put(FOURTH_COLUMN, "Per Unit");
//        temp3.put(FIFTH_COLUMN, "75.6");
//        list.add(temp3);
//
//        HashMap temp4 = new HashMap();
//        temp4.put(FIRST_COLUMN,"7 oz");
//        temp4.put(SECOND_COLUMN, "By TechnoTalaktive Pvt. Ltd.");
//        temp4.put(THIRD_COLUMN, "Rs. 100");
//        temp4.put(FOURTH_COLUMN, "Per Unit");
//        temp4.put(FIFTH_COLUMN, "75.6");
//        list.add(temp4);
    }


}
