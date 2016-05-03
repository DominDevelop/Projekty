package com.example.dominik.mobilecoach.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.TrainingDetail;
import com.example.dominik.mobilecoach.model.SerwisSession;
import com.example.dominik.mobilecoach.model.TrackTable;
import com.example.dominik.mobilecoach.model.TrainingSesion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by Dominik on 2016-01-14.
 */
public class FragmentMainDetails extends Fragment {

    public SupportMapFragment googleMap;
    public GoogleMap map;
    public TextView outputWeight;
    public TextView outputSpeed;
    public TextView outputCalories;
    public TextView outputDistance;
    public TextView outputDay;
    public TextView outputTime;

    public TrainingSesion sesion;
    public static ArrayList<TrackTable> track;
    public ImageButton imageMapButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_detail,container,false);
        outputDistance = (TextView) view.findViewById(R.id.distanceOutput);
        outputCalories = (TextView) view.findViewById(R.id.caloriesOutput);
        outputDay = (TextView) view.findViewById(R.id.dateOutput);
        outputSpeed = (TextView) view.findViewById(R.id.speedOutput);
        outputWeight = (TextView) view.findViewById(R.id.weightOutput);
        outputTime = (TextView) view.findViewById(R.id.timeOutput);
        imageMapButton = (ImageButton) view.findViewById(R.id.imageGoMap);

        imageMapButton = (ImageButton) view.findViewById(R.id.imageGoMap);
        imageMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingDetail.viewPager.setCurrentItem(TrainingDetail.viewPager.getCurrentItem() + 1);
            }
        });

        SerwisSession db = new SerwisSession(TrainingDetail.activity.getApplicationContext(), null);
        track = db.getTrace(TrainingDetail.id);
        sesion = db.getSession(TrainingDetail.id);
        db.close();

        outputWeight.setText(sesion.getWeight() + " kg");
        outputSpeed.setText(String.format("%.02f", sesion.getSpeed()) + " km/h");
        outputDay.setText(sesion.getDate());
        outputCalories.setText(sesion.getCalories() + " kcal");
        outputDistance.setText(String.format("%.02f", sesion.getDistance() / 1000.0000) + " km");

        long sek = 0,min = 0, hor = 0;
        hor = sesion.getTime() / 3600;
        min = sesion.getTime() / 60;
        sek = sesion.getTime() % 60;
        outputTime.setText(String.format("%02d:%02d:%02d",hor,min,sek));
        return view;
    }
}
