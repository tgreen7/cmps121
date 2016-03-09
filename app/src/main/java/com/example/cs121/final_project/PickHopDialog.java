package com.example.cs121.final_project;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cs121.final_project.Tab1Activity;
import com.example.cs121.final_project.PickHopActivity;

import org.w3c.dom.Text;

/**
 * Created by Taoh on 3/7/2016.
 */
public class PickHopDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button add, cancel;
    public CheckBox dry, wort;
    public String name_text, alpha_text, type_text;
    public TextView time, boil;
    public EditText name, alpha, type, boilTime, weight;

    public PickHopDialog(Activity a, String name, String alpha, String type) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.name_text = name;
        this.alpha_text = alpha;
        this.type_text = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pick_hop_dialog);


        add = (Button) findViewById(R.id.addItem);
        cancel = (Button) findViewById(R.id.cancel);
        dry = (CheckBox) findViewById(R.id.dryHop);
        wort = (CheckBox) findViewById(R.id.wort);
        time = (TextView) findViewById(R.id.time);
        boil = (TextView) findViewById(R.id.boil_time);

        name = (EditText) findViewById(R.id.nameText);
        alpha = (EditText) findViewById(R.id.alphaText);
        type = (EditText) findViewById(R.id.typeText);
        boilTime = (EditText) findViewById(R.id.boil);
        weight = (EditText) findViewById(R.id.weight);

        name.setText(name_text);
        alpha.setText(alpha_text);
        type.setText(type_text);


        add.setOnClickListener(this);
        cancel.setOnClickListener(this);

        dry.setOnClickListener(this);
        wort.setOnClickListener(this);


        Spinner spinner = (Spinner) findViewById(R.id.weight_type);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.weight_type_array, R.layout.my_spinner_text);
// Specify the layout to use when the list of choices appears
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);
// Apply the adapter to the spinner
        spinner.setAdapter(spin_adapter);



    }

    public void dryClick() {
        if(dry.isChecked()){
            time.setText("days");
            boil.setText("");
            wort.setChecked(false);
        }
        else {
            time.setText("min");
            boil.setText("Boil Time");
        }
    }

    public void wortClick() {
        if(wort.isChecked()){
            time.setText("min");
            boil.setText("Boil Time");
            dry.setChecked(false);
        }
    }

    public void sendItem() {
//        Item (String ing_type, String name,  String type, Float color,
//                Float potential, Float alpha, String lab,
//                String form, String use, Boolean wort, Boolean dry,
//                String time, Float weight){
        PickHopActivity.make_item("hop", name.getText().toString(), type.getText().toString(), null,
                null, null, alpha.getText().toString(), null, null, wort.isChecked(), dry.isChecked(),
                boilTime.getText().toString(), Float.parseFloat(weight.getText().toString()));
//        System.out.println(boilTime.getText().toString());

        MyDialogFragmentListener activity = (MyDialogFragmentListener) c;
        activity.onReturnValue("done");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dryHop:
                dryClick();
                break;

            case R.id.wort:
                wortClick();
                break;

            case R.id.addItem:
                sendItem();
                dismiss();
                break;

            case R.id.cancel:
                dismiss();
                break;

            default:
                break;
        }
    }

    public interface MyDialogFragmentListener {
        void onReturnValue(String foo);
    }
}