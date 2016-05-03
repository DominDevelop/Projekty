package com.example.dominik.mobilecoach.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.MainActivity;
import com.example.dominik.mobilecoach.activities.TrainingActivity;

/**
 * Created by Dominik on 2015-11-22.
 */
public class FragmentPersonDetails extends Fragment {

    public EditText editAge;
    public EditText editWeigh;
    public EditText editGrowth;
    public RadioButton radioFamle;
    public RadioButton radioMale;
    public RadioGroup radioGroup;
    public TextView textBmi;
    public TextView textBmr;
    public TextView textStan;

    public int age = 0;
    public float weigh = 0;
    public int growth = 0;
    public boolean isMen = true;
    public Double BMI = 0.0;
    public Double BMR = 0.0;

    public final String FILE_NAME = "DetailsPerson";
    public final String AGE_VARIABLE = "ageVariable";
    public final String WEIGH_VARIABLE = "weighVariable";
    public final String GROWTH_VARIABLE = "growthVariable";
    public final String SEX_VARIABLE = "SexVariable";
    public final String BMI_VARIABLE = "bmiVariable";
    public final String BMR_VARIABLE = "bmrVariable";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        editAge = (EditText) view.findViewById(R.id.inputAge);
        editWeigh = (EditText) view.findViewById(R.id.inputWeigh);
        editGrowth = (EditText) view.findViewById(R.id.inputGrowth);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        textBmi = (TextView) view.findViewById(R.id.outputBmi);
        textBmr = (TextView) view.findViewById(R.id.outputBmr);
        textStan = (TextView) view.findViewById(R.id.stanWeigh);

     //   MainActivity.appActionBar.setTitle(R.string.app_details);

        TextView.OnEditorActionListener listner = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                SharedPreferences.Editor editor = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();

                if (v == editAge) {
                    try {
                        age = Integer.valueOf(editAge.getText().toString());
                        editor.putInt(AGE_VARIABLE, age).apply();
                        editAge.setText(String.format("%d", age));
                    } catch (Exception e) {
                        editAge.setText(String.format("%d", age));
                    }
                }
                if (v == editGrowth) {
                    try {
                        growth = Integer.valueOf(editGrowth.getText().toString());
                        editor.putInt(GROWTH_VARIABLE, growth).apply();
                        editGrowth.setText(String.format("%d", growth));
                    } catch (Exception e) {
                        editGrowth.setText(String.format("%d", growth));
                    }
                }
                if (v == editWeigh) {
                    try {
                        weigh = Float.valueOf(editWeigh.getText().toString());
                        editor.putFloat(WEIGH_VARIABLE, weigh).apply();
                        editWeigh.setText(String.format("%.02f", weigh));
                    } catch (Exception e) {
                        editWeigh.setText(String.format("%.02f", weigh));
                    }
                }

                calculateBmi();
                return false;
            }
        };
        editGrowth.setOnEditorActionListener(listner);
        editWeigh.setOnEditorActionListener(listner);
        editAge.setOnEditorActionListener(listner);

        View.OnTouchListener listn = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                EditText editText = (EditText) v;
                editText.setText("");
                return false;
            }
        };

        editGrowth.setOnTouchListener(listn);
        editWeigh.setOnTouchListener(listn);
        editAge.setOnTouchListener(listn);

        View.OnFocusChangeListener list = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                EditText editText = (EditText) v;
                if(editText == editAge){
                    editText.setText(String.format("%d",age));
                }
                if(editText == editGrowth){
                    editText.setText(String.format("%d",growth));
                }
                if(editText == editWeigh){
                    editText.setText(String.format("%.02f",weigh));
                }
            }
        };

        editGrowth.setOnFocusChangeListener(list);
        editWeigh.setOnFocusChangeListener(list);
        editAge.setOnFocusChangeListener(list);

        Activity activity = getActivity();
        isMen = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getBoolean(SEX_VARIABLE, false);
        growth = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getInt(GROWTH_VARIABLE, 0);
        age = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getInt(AGE_VARIABLE, 0);
        weigh = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getFloat(WEIGH_VARIABLE, 0);
        BMI = (double) activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getFloat(BMI_VARIABLE, 0);
        BMR = (double) activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                .getFloat(BMR_VARIABLE, 0);
        calculateBmi();

        editAge.setText(String.format("%d", age));
        editGrowth.setText(String.format("%d", growth));
        editWeigh.setText(String.format("%.02f", weigh));

        radioFamle = (RadioButton) view.findViewById(R.id.radioFamle);
        radioMale = (RadioButton) view.findViewById(R.id.radioMale);
        if (isMen) {
            radioMale.setChecked(true);
        } else {
            radioFamle.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                SharedPreferences.Editor editor = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
                switch (checkedId) {

                    case R.id.radioFamle:
                        editor.putBoolean(SEX_VARIABLE, false).apply();
                        isMen = false;
                        break;
                    case R.id.radioMale:
                        editor.putBoolean(SEX_VARIABLE, true).apply();
                        isMen = true;
                        break;
                }
                calculateBmi();
            }
        });

        if(BMI < 18.5){
            textStan.setText(" masz niedowagę ");
        } else if(BMI < 24.9){
            textStan.setText(" waga w normie ");
        } else if(BMI < 29.9 ){
            textStan.setText(" nadwaga ");
        } else if(BMI < 34.9 ){
            textStan.setText(" otyłość pierwszego stopnia ");
        } else if(BMI < 39.9 ){
            textStan.setText(" otyłość drugiego stopnia");
        }else{
            textStan.setText(" otyłość trzeciego stopnia");
        }

        return view;
    }

    void calculateBmi() {

        if (isMen) {
            BMR = (13.75 * weigh) + (5.00 * (double)growth) - (6.76 * (double)age) + 66;
        } else {
            BMR = (9.56 * weigh) + (1.85 * (double)growth) - (4.68 * (double)age) + 655;
        }

        BMI = (weigh / ((growth / 100.000) * (growth / 100.000)));
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putFloat(BMI_VARIABLE, Math.round(BMI)).apply();
        editor.putFloat(BMR_VARIABLE, Math.round(BMR)).apply();

        if (!BMI.isNaN() && !BMI.isInfinite()) {
            textBmi.setText(String.format("%.02f", BMI));
        }
        if (BMR > 0 && !BMR.isInfinite() && !BMR.isNaN()) {
            textBmr.setText(String.format("%.02f", BMR));
        }

        if(BMI < 18.5){
            textStan.setText(" masz niedowagę ");
        } else if(BMI < 24.9){
            textStan.setText(" waga w normie ");
        } else if(BMI < 29.9 ){
            textStan.setText(" nadwaga ");
        } else if(BMI < 34.9 ){
            textStan.setText(" otyłość pierwszego stopnia ");
        } else if(BMI < 39.9 ){
            textStan.setText(" otyłość drugiego stopnia");
        }else{
            textStan.setText(" otyłość trzeciego stopnia");
        }
    }

}
