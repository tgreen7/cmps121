package com.example.cs121.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1);

        ListView lview = (ListView) findViewById(R.id.listView);

        populateList();
        ListViewAdapter adapter = new ListViewAdapter(this, list);
        lview.setAdapter(adapter);
    }

    private void populateList() {

        list = new ArrayList<HashMap>();

        HashMap temp = new HashMap();
        temp.put(FIRST_COLUMN,"3 pkg");
        temp.put(SECOND_COLUMN, "By NavNeet");
        temp.put(THIRD_COLUMN, "Rs. 200");
        temp.put(FOURTH_COLUMN, "Per Unit");
        temp.put(FIFTH_COLUMN, "75.6");
        list.add(temp);

        HashMap temp1 = new HashMap();
        temp1.put(FIRST_COLUMN,"4 oz");
        temp1.put(SECOND_COLUMN, "By Amee Products");
        temp1.put(THIRD_COLUMN, "Rs. 400");
        temp1.put(FOURTH_COLUMN, "Per Unit");
        temp1.put(FIFTH_COLUMN, "75.6");
        list.add(temp1);

        HashMap temp2 = new HashMap();
        temp2.put(FIRST_COLUMN,"10 grams");
        temp2.put(SECOND_COLUMN, "By National Products");
        temp2.put(THIRD_COLUMN, "Rs. 600");
        temp2.put(FOURTH_COLUMN, "Per Unit");
        temp2.put(FIFTH_COLUMN, "75.6");
        list.add(temp2);

        HashMap temp3 = new HashMap();
        temp3.put(FIRST_COLUMN,"6.73 oz");
        temp3.put(SECOND_COLUMN, "By Devarsh Prakashan");
        temp3.put(THIRD_COLUMN, "Rs. 800");
        temp3.put(FOURTH_COLUMN, "Per Unit");
        temp3.put(FIFTH_COLUMN, "75.6");
        list.add(temp3);

        HashMap temp4 = new HashMap();
        temp4.put(FIRST_COLUMN,"7 oz");
        temp4.put(SECOND_COLUMN, "By TechnoTalaktive Pvt. Ltd.");
        temp4.put(THIRD_COLUMN, "Rs. 100");
        temp4.put(FOURTH_COLUMN, "Per Unit");
        temp4.put(FIFTH_COLUMN, "75.6");
        list.add(temp4);
    }
}
