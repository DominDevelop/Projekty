package com.example.dominik.mobilecoach.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.MainActivity;
import com.example.dominik.mobilecoach.model.SerwisPlan;
import com.example.dominik.mobilecoach.other.GeneratePlan;
import com.example.dominik.mobilecoach.other.PlanAdapter;

import java.text.ParseException;

/**
 * Created by Dominik on 2015-08-19.
 */
public class FragmentPlan extends Fragment {

    private static ListView listView;
    private Button generatePlan;
    private static Context context;
    public final String FILE_NAME = "DetailsPerson";
    public final String DB_EXIST = "DateBase";
    public final String PLAN_CREATED = "DateBase";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        context = getActivity().getApplicationContext();

        boolean dbexist = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(DB_EXIST, false);
        SerwisPlan db = new SerwisPlan(MainActivity.activity.getApplicationContext(),null);
        if (!dbexist) {

            Log.e("baza nie istnieje","");
            db.addPlan(8, 1);
            db.close();
            getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean(DB_EXIST, true).apply();
        }else{

            Log.e("baza istnieje","");
            try {
                db.checkPlan();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            db.close();
        }

        generatePlan = (Button) view.findViewById(R.id.generatePlan);
        generatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPlan();
            }
        });

        listView = (ListView) view.findViewById(R.id.planView);
        listView.setAdapter(new PlanAdapter(this.getActivity().getApplicationContext(), android.R.layout.simple_list_item_1));
        return view;
    }

    public void createPlan() {

        GeneratePlan planDialog = new GeneratePlan();
        planDialog.generateAsk();
    }

    public static void clearListView() {

        listView.setAdapter(null);
        listView.setAdapter(new PlanAdapter(context, android.R.layout.simple_list_item_1));
    }
}