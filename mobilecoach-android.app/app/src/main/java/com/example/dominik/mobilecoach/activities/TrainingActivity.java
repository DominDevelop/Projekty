package com.example.dominik.mobilecoach.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.model.SerwisSession;
import com.example.dominik.mobilecoach.other.PagerAdapter;

/**
 * Created by Dominik on 2015-08-24.
 */
public class TrainingActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    public SerwisSession dbContext;
    public static final String FILE_NAME = "TrainingActivityFile";
    public static final String ACTIVITY_VARIABLE = "Aktywność";
    public static final String ACTIVITY_FACTOR = "Aktywność";
    public static final String FIRST_TRAINING_VARIABLE = "PierwszeUruchomienie";
    public static Activity activity;
    public android.support.v7.app.ActionBar appActionBar;
    public static ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        fragmentManager = getSupportFragmentManager();
        activity = this;

        viewPager = (ViewPager)findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        appActionBar = getSupportActionBar();
        appActionBar.setTitle(R.string.app_training);
    }

    @Override
    public void onBackPressed() {

        appActionBar.setTitle(R.string.app_name);
        super.onBackPressed();
    }
}
