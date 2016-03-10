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

/**
 * Created by Halsifer on 3/9/16.
 */
public class EditMiscDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button add, cancel;

    public Spinner use_type, time_type, amount_type;

    public String name_text, type_text, use_text, org_amount_type;
    public Integer org_time;
    public Float org_weight;
    public EditText name, type, weight, time;

    public EditMiscDialog(Activity a, Item item) {
        super(a);
        this.c = a;
        this.name_text = item.name;
        this.type_text = item.type;
        this.org_weight  = item.weight;
        this.org_time = item.time;
        this.use_text = item.str1;
        this.org_amount_type = item.str1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pick_misc_dialog);

        add = (Button) findViewById(R.id.addItem);
        cancel = (Button) findViewById(R.id.cancel);

        name = (EditText) findViewById(R.id.nameText);
        type = (EditText) findViewById(R.id.typeText);
        weight = (EditText) findViewById(R.id.amountText);
        time = (EditText) findViewById(R.id.timeBox);

        name.setText(name_text);
        type.setText(type_text);
        weight.setText(org_weight.toString());

        add.setOnClickListener(this);
        cancel.setOnClickListener(this);

        use_type = (Spinner) findViewById(R.id.use_type);
        time_type = (Spinner) findViewById(R.id.time_type);
        amount_type = (Spinner) findViewById(R.id.amount_type);

        ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.use_array, R.layout.my_spinner_text);
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);
        use_type.setAdapter(spin_adapter);

        spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.time_type_array, R.layout.my_spinner_text);
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);

        time_type.setAdapter(spin_adapter);

        spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.all_amount_types, R.layout.my_spinner_text);
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);

        amount_type.setAdapter(spin_adapter);

        if (org_time >= 1440) {
            Integer days = org_time / 1440;
            time.setText(days.toString());
            time_type.setSelection(1);
        } else time.setText(org_time.toString());

        int i = 0;
        while(!(use_text.equals(use_type.getSelectedItem().toString()))) {
            use_type.setSelection((i+1));
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
        else if(time.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a time.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(weight.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter an amount.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }



    public void sendItem() {

        if(checkEmpty()) {
            return;
        }


        int timeparse = Integer.parseInt(time.getText().toString());
        if (time_type.getSelectedItem().toString().equals("Days")) timeparse *= 1440;
        Item grain = new Item(4, timeparse, name.getText().toString(),
                type.getText().toString(), use_type.getSelectedItem().toString(),
                amount_type.getSelectedItem().toString(), null, null, null,
                Float.parseFloat(weight.getText().toString()), null, null);
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