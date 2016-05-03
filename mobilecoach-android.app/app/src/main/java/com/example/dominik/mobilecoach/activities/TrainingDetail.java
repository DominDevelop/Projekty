package com.example.dominik.mobilecoach.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.model.TrackTable;
import com.example.dominik.mobilecoach.model.TrainingSesion;
import com.example.dominik.mobilecoach.model.SerwisSession;
import com.example.dominik.mobilecoach.other.PagerAdapter;
import com.example.dominik.mobilecoach.other.PagerAdapterDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-11-28.
 */
public class TrainingDetail extends AppCompatActivity {

    public static int id;
    public FragmentManager fragmentManager;
    public static Activity activity;
    public static ViewPager viewPager;
    public android.support.v7.app.ActionBar appActionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fragmentManager = getSupportFragmentManager();
        activity = this;

        viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapterDetails pagerAdapter = new PagerAdapterDetails(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        appActionBar = getSupportActionBar();
        appActionBar.setTitle(R.string.app_history);

        Intent intent = getIntent();
        this.id = intent.getBundleExtra("sesja").getInt("id");

    }
}
