package com.example.cs121.final_project.Edit_Ing_Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cs121.final_project.Item;
import com.example.cs121.final_project.R;
import com.example.cs121.final_project.Tab1Activity;

/**
 * Created by Halsifer on 3/9/16.
 */
public class EditGrainDialog extends Dialog implements android.view.View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button add, cancel;

    public Spinner use, time_type;

    public String name_text, type_text, use_text;
    public Float org_color, org_potential, org_weight;
    public Integer org_time;
    public EditText name, type, weight_oz, weight_lb, time, color, potential;

    public EditGrainDialog(Activity a, Item item) {
        super(a);
        this.c = a;
        this.name_text = item.name;
        this.type_text = item.type;
        this.use_text  = item.str3;
        this.org_time = item.time;
        this.org_color = item.flt1;
        this.org_potential = item.flt2;
        this.org_weight = item.weight;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pick_grain_dialog);

        add = (Button) findViewById(R.id.addItem);
        cancel = (Button) findViewById(R.id.cancel);

        name = (EditText) findViewById(R.id.nameText);
        time = (EditText) findViewById(R.id.time_box);
        type = (EditText) findViewById(R.id.typeText);
        weight_lb = (EditText) findViewById(R.id.weight_lb);
        weight_oz = (EditText) findViewById(R.id.weight_oz);
        color = (EditText) findViewById(R.id.colorText);
        potential = (EditText) findViewById(R.id.potentialText);

        use = (Spinner) findViewById(R.id.use);
        time_type = (Spinner) findViewById(R.id.time_type);

        name.setText(name_text);
        type.setText(type_text);
        color.setText(org_color.toString());
        potential.setText(org_potential.toString());


        Integer org_lb = 0;
        while (org_weight >= 16){
            org_lb += 1;
            org_weight -= 16;
        }
        weight_lb.setText(org_lb.toString());
        weight_oz.setText(String.format("%.1f", org_weight));

        add.setOnClickListener(this);
        add.setText(R.string.save);
        cancel.setOnClickListener(this);



        ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.use_array, R.layout.my_spinner_text);
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);
        use.setAdapter(spin_adapter);

        spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_type_array, R.layout.my_spinner_text);
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);

        time_type.setAdapter(spin_adapter);

        if (org_time >= 1440) {
            Integer days = org_time / 1440;
            time.setText(days.toString());
            time_type.setSelection(1);
        } else time.setText(org_time.toString());
        int i = 0;
        use.setSelection(0);
        while(!(use_text.equals(use.getSelectedItem().toString()))) {
            use.setSelection((i+1));
            i++;
        }
    }

    public boolean checkEmpty() {
        if(name.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a name.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(type.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a type.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(color.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a color.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(potential.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a potential.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(time.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a time.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(weight_lb.getText().toString().trim().length() == 0
                || weight_oz.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a weight.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void sendItem() {
        if(checkEmpty()) {
            return;
        }

        if (weight_lb.getText().toString().trim().length() == 0) {
            weight_lb.setText("0");
        }
        if (weight_oz.getText().toString().trim().length() == 0) {
            weight_oz.setText("0");
        }

        int timeparse = Integer.parseInt(time.getText().toString());
        Float weightlbparse = Float.parseFloat(weight_lb.getText().toString()) * 16;
        Float weightoz = Float.parseFloat(weight_oz.getText().toString());
        if (time_type.getSelectedItem().toString().equals("Days")) timeparse *= 1440;
        Item grain = new Item(1, timeparse, name.getText().toString(),
                type.getText().toString(), null, null, use.getSelectedItem().toString(),
                Float.parseFloat(color.getText().toString()),
                Float.parseFloat(potential.getText().toString()),
                (weightlbparse+weightoz), null, null);

        MyDialogFragmentListener activity = (MyDialogFragmentListener) c;
        activity.putGrain(grain, true);

        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItem:
                sendItem();
                break;

            case R.id.cancel:
                MyDialogFragmentListener activity = (MyDialogFragmentListener) c;
                dismiss();
                break;

            default:
                break;
        }
    }

    public interface MyDialogFragmentListener {
//        void editGrain(Item bar);
        void putGrain(Item bar, boolean t);
    }
}
