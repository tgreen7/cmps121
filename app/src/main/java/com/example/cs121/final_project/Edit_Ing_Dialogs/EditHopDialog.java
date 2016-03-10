package com.example.cs121.final_project.Edit_Ing_Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs121.final_project.Item;
import com.example.cs121.final_project.R;

/**
 * Created by Halsifer on 3/9/16.
 */
public class EditHopDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button add, cancel;

    public Spinner spinner;

    public String name_text, type_text;
    public Float org_alpha, org_weight;
    public Integer org_time;
    public Boolean org_wort, org_dry;
    public EditText name, type, alpha, boilTime, weight;
    public TextView time, boil;
    public CheckBox dry, wort;

    public EditHopDialog(Activity a, Item item) {
        super(a);
        this.c = a;
        this.name_text = item.name;
        this.type_text = item.type;
        this.org_dry  = item.dry;
        this.org_time = item.time;
        this.org_alpha = item.flt1;
        this.org_wort = item.wort;
        this.org_weight = item.weight;
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
        alpha.setText(org_alpha.toString());
        type.setText(type_text);
        dry.setChecked(org_dry);
        wort.setChecked(org_wort);
        dryClick();
        wortClick();

        add.setOnClickListener(this);
        cancel.setOnClickListener(this);

        dry.setOnClickListener(this);
        wort.setOnClickListener(this);

        spinner = (Spinner) findViewById(R.id.weight_type);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.weight_type_array, R.layout.my_spinner_text);
// Specify the layout to use when the list of choices appears
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);
// Apply the adapter to the spinner
        spinner.setAdapter(spin_adapter);

        if (org_time >= 1440) {
            Integer days = org_time / 1440;
            boilTime.setText(days.toString());
        } else boilTime.setText(org_time.toString());

        if (org_weight >= 28.3495f){
            Float oz = org_weight / 28.3495f;
            weight.setText(String.format("%.1f", oz));
            spinner.setSelection(0);
        }else {
            weight.setText(org_weight.toString());
            spinner.setSelection(1);
        }

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
    public boolean checkEmpty() {
        if(name.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a name.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(alpha.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter an alpha.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(type.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a type.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(boilTime.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a time.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(weight.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a weight.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void saveItem() {
        if(checkEmpty()) return;
        Spinner spinner = (Spinner) findViewById(R.id.weight_type);
        int timeparse = Integer.parseInt(boilTime.getText().toString());
        Float weightparse = Float.parseFloat(weight.getText().toString());
        if (dry.isChecked()) timeparse *= 1440;
        if (spinner.getSelectedItem().toString().equals("Oz")) weightparse *= 28.3495f;
        Item hop = new Item(2, timeparse, name.getText().toString(),
                type.getText().toString(), null, null, null,
                Float.parseFloat(alpha.getText().toString()), null,
                weightparse, wort.isChecked(), dry.isChecked());
        MyDialogFragmentListener activity = (MyDialogFragmentListener) c;
        activity.setItem(hop);

        dismiss();
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
        void setItem(Item bar);
    }
}

