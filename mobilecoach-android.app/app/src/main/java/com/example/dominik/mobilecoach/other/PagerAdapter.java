package com.example.dominik.mobilecoach.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dominik.mobilecoach.fragments.FragmentMap;
import com.example.dominik.mobilecoach.fragments.FragmentTraining;

/**
 * Created by Dominik on 2015-08-24.
 */
public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return new FragmentTraining();
            case 1:
                return new FragmentMap();
            default:
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}