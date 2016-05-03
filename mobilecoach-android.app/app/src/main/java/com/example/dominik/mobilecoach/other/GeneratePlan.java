package com.example.dominik.mobilecoach.other;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.MainActivity;
import com.example.dominik.mobilecoach.fragments.FragmentPlan;
import com.example.dominik.mobilecoach.model.SerwisPlan;

/**
 * Created by Dominik on 2015-10-10.
 */
public class GeneratePlan {

    private int weekNumber;
    private int sportLevel;
    private Button btnAccept;
    private Button btnCancel;
    private Dialog dialog;
    private NumberPicker numberPicker;

    public void generateAsk(){

        dialog = new Dialog(MainActivity.activity);
        dialog.setTitle("Generuj Plan");
        dialog.setContentView(R.layout.dialog_suggest_plan);

        numberPicker = (NumberPicker)dialog.findViewById(R.id.numberPicker);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(4);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                weekNumber = picker.getValue();
            }
        });
        weekNumber = 4;

        RadioGroup radioGroup = (RadioGroup)dialog.findViewById(R.id.groupLevels);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radio1:
                        sportLevel = 1;
                        break;
                    case R.id.radio2:
                        sportLevel = 2;
                        break;
                    case R.id.radio3:
                        sportLevel = 3;
                        break;
                }
            }
        });
        RadioButton radioButton = (RadioButton)dialog.findViewById(R.id.radio1);
        radioButton.isChecked();
        sportLevel = 1;

        btnAccept = (Button)dialog.findViewById(R.id.accept);
        btnCancel = (Button)dialog.findViewById(R.id.cancel);

        btnAccept.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SerwisPlan db = new SerwisPlan(MainActivity.activity.getApplicationContext(),null);
                db.addPlan(weekNumber, sportLevel);
                FragmentPlan.clearListView();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
