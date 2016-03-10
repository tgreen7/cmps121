//package com.example.cs121.final_project.Edit_Ing_Dialogs;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//
//import com.example.cs121.final_project.Item;
//import com.example.cs121.final_project.R;
//
///**
// * Created by Halsifer on 3/9/16.
// */
//public class EditHopDialog extends Dialog implements android.view.View.OnClickListener {
//}
//public Activity c;
//public Dialog d;
//public Button add, cancel;
//
//public Spinner use, time_type;
//
//public String name_text, type_text, use_text;
//public Float org_color, org_potential, org_weight;
//public Integer org_time;
//public EditText name, type, weight_oz, weight_lb, time, color, potential;
//
//    public EditHopDialog(Activity a, Item item) {
//        super(a);
//        this.c = a;
//        this.name_text = item.name;
//        this.type_text = item.type;
//        this.use_text  = item.str3;
//        this.org_time = item.time;
//        this.org_color = item.flt1;
//        this.org_potential = item.flt2;
//        this.org_weight = item.weight;
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.pick_grain_dialog);
//
//        add = (Button) findViewById(R.id.addItem);
//        cancel = (Button) findViewById(R.id.cancel);
//
//        name = (EditText) findViewById(R.id.nameText);
//        time = (EditText) findViewById(R.id.time_box);
//        type = (EditText) findViewById(R.id.typeText);
//        weight_lb = (EditText) findViewById(R.id.weight_lb);
//        weight_oz = (EditText) findViewById(R.id.weight_oz);
//        color = (EditText) findViewById(R.id.colorText);
//        potential = (EditText) findViewById(R.id.potentialText);
//
//        use = (Spinner) findViewById(R.id.use);
//        time_type = (Spinner) findViewById(R.id.time_type);
//
//        name.setText(name_text);
//        type.setText(type_text);
//        color.setText(org_color.toString());
//        potential.setText(org_potential.toString());
//
//
//        Integer org_lb = 0;
//        while (org_weight >= 16){
//            org_lb += 1;
//            org_weight -= 16;
//        }
//        weight_lb.setText(org_lb.toString());
//        weight_oz.setText(org_weight.toString());
//
//        add.setOnClickListener(this);
//        cancel.setOnClickListener(this);
//
//
//
//        ArrayAdapter<CharSequence> spin_adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.use_array, R.layout.my_spinner_text);
//        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);
//        use.setAdapter(spin_adapter);
//
//        spin_adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.time_type_array, R.layout.my_spinner_text);
//        spin_adapter.setDropDownViewResource(R.layout.my_spinner_style);
//
//        time_type.setAdapter(spin_adapter);
//
//        if (org_time >= 1440) {
//            int days = org_time / 1440;
//            time.setText(days);
//            time_type.setSelection(1);
//        } else time.setText(org_time.toString());
//        int i = 0;
//        use.setSelection(i);
//        while(use_text != use.getSelectedItem().toString()) use.setSelection((i+1));
//    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.addItem:
//                dismiss();
//                break;
//
//            case R.id.cancel:
//                dismiss();
//                break;
//
//            default:
//                break;
//        }
//    }