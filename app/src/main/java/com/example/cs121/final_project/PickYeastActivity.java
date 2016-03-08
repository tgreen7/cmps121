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

public class PickYeastActivity extends AppCompatActivity {

    private ArrayList<HashMap> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_yeast);

        ListView lview = (ListView) findViewById(R.id.yeast_list);

        populateList();
        SelectYeastAdapter adapter = new SelectYeastAdapter(this, list);
        lview.setAdapter(adapter);
    }

    private void populateList() {

        list = new ArrayList<HashMap>();

        HashMap temp = new HashMap();
        temp.put(FIRST_COLUMN, "American Ale");
        temp.put(SECOND_COLUMN, "foiwjfawf");
        temp.put(THIRD_COLUMN, "Rsawfojaw. 200");
        temp.put(FOURTH_COLUMN, ". 200");
        temp.put(FIFTH_COLUMN, "fojaw. 200");
        list.add(temp);

        HashMap temp1 = new HashMap();
        temp1.put(FIRST_COLUMN,"American Ale 2");
        temp1.put(SECOND_COLUMN, "afwjoawjf");
        temp1.put(THIRD_COLUMN, "fwajefoa");
        temp1.put(FOURTH_COLUMN, "lager");
        temp1.put(FIFTH_COLUMN, "liquid");
        list.add(temp1);

        HashMap temp2 = new HashMap();
        temp2.put(FIRST_COLUMN,"American Ale 2");
        temp2.put(SECOND_COLUMN, "Bafwjy");
        temp2.put(THIRD_COLUMN, "oio");
        temp2.put(FOURTH_COLUMN, "Rsa. 200");
        temp2.put(FIFTH_COLUMN, "Rsa. 200");
        list.add(temp2);

    }
}
