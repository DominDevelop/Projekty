package com.example.dominik.mobilecoach.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.TrainingActivity;
import com.example.dominik.mobilecoach.activities.TrainingDetail;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by Dominik on 2016-01-14.
 */
public class FragmentMapDetails extends Fragment implements OnMapReadyCallback {

    public ImageButton imagebackButton;
    public GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);

        imagebackButton = (ImageButton) view.findViewById(R.id.imageGoTraining);
        imagebackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingDetail.viewPager.setCurrentItem(TrainingDetail.viewPager.getCurrentItem() - 1);
            }
        });

        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap)).getMap();
        map.getUiSettings().setZoomControlsEnabled(true);

        int i, length = FragmentMainDetails.track.size();
        PolylineOptions path = new PolylineOptions();
        for (i = 0; i < length; i++) {
            path.add(new LatLng(FragmentMainDetails.track.get(i).getLatidude(), FragmentMainDetails.track.get(i).getLongitude()));
        }

        map.addPolyline(path);
        if( FragmentMainDetails.track.size() > 0) {
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(FragmentMainDetails.track.get(0).getLatidude(), FragmentMainDetails.track.get(0).getLongitude()), 18.5f));
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
