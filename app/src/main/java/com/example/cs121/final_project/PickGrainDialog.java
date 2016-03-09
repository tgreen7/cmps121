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
public class PickGrainDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button add, cancel;

    public Spinner use, time_type;

    public String name_text, type_text, color_text, potential_text;
    public EditText name, alpha, type, boilTime, weight, time, color, potential;

    public PickGrainDialog(Activity a, String name, String type, String color, String potential) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.name_text = name;
        this.type_text = type;
        this.color_text = color;
        this.potential_text = potential;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pick_grain_dialog);


        add = (Button) findViewById(R.id.addItem);
        cancel = (Button) findViewById(R.id.cancel);

        name = (EditText) findViewById(R.id.nameText);
        alpha = (EditText) findViewById(R.id.alphaText);
        type = (EditText) findViewById(R.id.typeText);
        boilTime = (EditText) findViewById(R.id.boil);
        weight = (EditText) findViewById(R.id.weight);
        color = (EditText) findViewById(R.id.colorText);
        potential = (EditText) findViewById(R.id.potentialText);

        name.setText(name_text);
        type.setText(type_text);
        color.setText(color_text);
        potential.setText(potential_text);


        add.setOnClickListener(this);
        cancel.setOnClickListener(this);

        use = (Spinner) findViewById(R.id.use);
        time_type = (Spinner) findViewById(R.id.time_type);

        ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.use_array, R.layout.my_spinner_text);
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);
        use.setAdapter(spin_adapter);

        spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_type_array, R.layout.my_spinner_text);
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);

        time_type.setAdapter(spin_adapter);

    }


    public void sendItem() {
//        Item (String ing_type, String name,  String type, Float color,
//                Float potential, Float alpha, String lab,
//                String form, String use, Boolean wort, Boolean dry,
//                String time, Float weight){
//        PickHopActivity.make_item("hop", name.getText().toString(), type.getText().toString(), null,
//                null, null, alpha.getText().toString(), null, null, wort.isChecked(), dry.isChecked(),
//                boilTime.getText().toString(), Float.parseFloat(weight.getText().toString()));
////        System.out.println(boilTime.getText().toString());
//
//        MyDialogFragmentListener activity = (MyDialogFragmentListener) c;
//        activity.onReturnValue("done");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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