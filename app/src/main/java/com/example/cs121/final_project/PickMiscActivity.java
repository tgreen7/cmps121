package com.example.cs121.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.cs121.final_project.Constant.FIFTH_COLUMN;
import static com.example.cs121.final_project.Constant.FIRST_COLUMN;
import static com.example.cs121.final_project.Constant.FOURTH_COLUMN;
import static com.example.cs121.final_project.Constant.SECOND_COLUMN;
import static com.example.cs121.final_project.Constant.THIRD_COLUMN;

public class PickMiscActivity extends AppCompatActivity {

    private ArrayList<HashMap> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_misc);

        ListView lview = (ListView) findViewById(R.id.misc_list);

        populateList();
        SelectMiscAdapter adapter = new SelectMiscAdapter(this, list);
        lview.setAdapter(adapter);
    }

    private void populateList() {

        list = new ArrayList<HashMap>();

        HashMap temp = new HashMap();
        temp.put(FIRST_COLUMN, "Amalase Enzyme");
        temp.put(SECOND_COLUMN, "other");
        temp.put(THIRD_COLUMN, "Fermentation");
        list.add(temp);

        HashMap temp1 = new HashMap();
        temp1.put(FIRST_COLUMN,"American Ale 2");
        temp1.put(SECOND_COLUMN, "afwjoawjf");
        temp1.put(THIRD_COLUMN, "fwajefoa");
        list.add(temp1);

        HashMap temp2 = new HashMap();
        temp2.put(FIRST_COLUMN,"American Ale 2");
        temp2.put(SECOND_COLUMN, "Bafwjy");
        temp2.put(THIRD_COLUMN, "oio");
        list.add(temp2);

    }
}
