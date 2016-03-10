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

import com.example.cs121.final_project.Item;
import com.example.cs121.final_project.R;

/**
 * Created by Taoh on 3/7/2016.
 */
public class PickYeastDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button add, cancel;

    public Spinner amount_type;

    public String name_text, company_text, type_text, form_text;
    public EditText name, company, type, amount, form;

    public PickYeastDialog (Activity a, String name, String company, String type, String form) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.name_text = name;
        this.company_text = company;
        this.type_text = type;
        this.form_text = form;
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


        add.setOnClickListener(this);
        cancel.setOnClickListener(this);

        amount_type = (Spinner) findViewById(R.id.amountType);

        ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.amount_type_array, R.layout.my_spinner_text);
        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);
        amount_type.setAdapter(spin_adapter);

    }


    public void sendItem() {
        Spinner spinner = (Spinner) findViewById(R.id.amountType);
        Item yeast = new Item(3, null, name.getText().toString(),
                type.getText().toString(), company.getText().toString(), form.getText().toString(),
                spinner.getSelectedItem().toString(), null, null,
                Float.parseFloat(amount.getText().toString()), null, null);
        MyDialogFragmentListener activity = (MyDialogFragmentListener) c;
        activity.setItem(yeast);
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
        void setItem(Item foo);
    }
}