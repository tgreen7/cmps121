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
public class EditYeastDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button add, cancel;

    public Spinner amount_type;

    public String name_text, company_text, type_text, form_text, unit_text;
    public Float org_amount;
    public EditText name, company, type, amount, form;

    public EditYeastDialog(Activity a, Item item) {
        super(a);
        this.c = a;
        this.name_text = item.name;
        this.type_text = item.type;
        this.company_text  = item.str1;
        this.form_text = item.str2;
        this.unit_text = item.str3;
        this.org_amount = item.weight;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pick_yeast_dialog);


        add = (Button) findViewById(R.id.addItem);
        cancel = (Button) findViewById(R.id.cancel);

        name = (EditText) findViewById(R.id.nameText);
        company = (EditText) findViewById(R.id.companyText);
        form = (EditText) findViewById(R.id.formText);
        type = (EditText) findViewById(R.id.typeText);
        amount = (EditText) findViewById(R.id.amountText);

        name.setText(name_text);
        type.setText(type_text);
        company.setText(company_text);
        form.setText(form_text);
        amount.setText(org_amount.toString());


        add.setOnClickListener(this);
        add.setText(R.string.edit);
        cancel.setOnClickListener(this);

        amount_type = (Spinner) findViewById(R.id.amountType);

        ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.amount_type_array, R.layout.my_spinner_text);
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);
        amount_type.setAdapter(spin_adapter);
        int i = 0;
        while(!(unit_text.equals(amount_type.getSelectedItem().toString()))) {
            amount_type.setSelection((i+1));
            i++;
        }

    }

    public boolean checkEmpty() {
        if (name.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a name.",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (company.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter an company.",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a type.",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (form.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter a form.",
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (amount.getText().toString().trim().length() == 0) {
            Toast.makeText(c, "Please enter an amount.",
                    Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    public void editItem() {
        if (checkEmpty()) {
            return;
        }

        Spinner spinner = (Spinner) findViewById(R.id.amountType);
        Item yeast = new Item(3, null, name.getText().toString(),
                type.getText().toString(), company.getText().toString(), form.getText().toString(),
                spinner.getSelectedItem().toString(), null, null,
                Float.parseFloat(amount.getText().toString()), null, null);
        MyDialogFragmentListener activity = (MyDialogFragmentListener) c;
        activity.putYeast(yeast, true);

        dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItem:
                editItem();
                break;

            case R.id.cancel:
                dismiss();
                break;

            default:
                break;
        }
    }

    public interface MyDialogFragmentListener {
        void putYeast(Item foo, boolean t);
    }
}
