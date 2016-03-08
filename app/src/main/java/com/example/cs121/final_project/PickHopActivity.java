package com.example.cs121.final_project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.cs121.final_project.Constant.FIRST_COLUMN;
import static com.example.cs121.final_project.Constant.SECOND_COLUMN;
import static com.example.cs121.final_project.Constant.THIRD_COLUMN;

public class PickHopActivity extends AppCompatActivity {

    private ArrayList<HashMap> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_hop);

        ListView lview = (ListView) findViewById(R.id.hop_list);

        populateList();
        SelectHopAdapter adapter = new SelectHopAdapter(this, list);
        lview.setAdapter(adapter);
    }

    private void populateList() {

        list = new ArrayList<HashMap>();

        HashMap temp = new HashMap();
        temp.put(FIRST_COLUMN, "3 pkfwaojfeoiawfg");
        temp.put(SECOND_COLUMN, "B");
        temp.put(THIRD_COLUMN, "Rsawfojaw. 200");
        list.add(temp);

        HashMap temp1 = new HashMap();
        temp1.put(FIRST_COLUMN,"4 wafawefoz");
        temp1.put(SECOND_COLUMN, "By");
        temp1.put(THIRD_COLUMN, "Rs. 40fwafeawf0");
        list.add(temp1);

        HashMap temp2 = new HashMap();
        temp2.put(FIRST_COLUMN,"awjfioajwfe");
        temp2.put(SECOND_COLUMN, "By");
        temp2.put(THIRD_COLUMN, "Rwffwf600");
        list.add(temp2);

    }
}
