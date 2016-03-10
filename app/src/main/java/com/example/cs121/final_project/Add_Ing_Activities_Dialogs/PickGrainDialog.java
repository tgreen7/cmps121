package com.example.cs121.final_project.Add_Ing_Activities_Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cs121.final_project.Item;
import com.example.cs121.final_project.R;

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
    public EditText name, alpha, type, boilTime, weight_oz, weight_lb, time, color, potential;

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
        time = (EditText) findViewById(R.id.time_box);
        type = (EditText) findViewById(R.id.typeText);
        boilTime = (EditText) findViewById(R.id.boil);
        weight_lb = (EditText) findViewById(R.id.weight_lb);
        weight_oz = (EditText) findViewById(R.id.weight_oz);
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
                || weight_lb.getText().toString().trim().length() == 0) {
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
        activity.setItem(grain);


        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItem:
                sendItem();
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