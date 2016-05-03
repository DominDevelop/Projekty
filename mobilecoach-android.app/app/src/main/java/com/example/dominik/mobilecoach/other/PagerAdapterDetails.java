package com.example.dominik.mobilecoach.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dominik.mobilecoach.fragments.FragmentMainDetails;
import com.example.dominik.mobilecoach.fragments.FragmentMap;
import com.example.dominik.mobilecoach.fragments.FragmentMapDetails;
import com.example.dominik.mobilecoach.fragments.FragmentTraining;

/**
 * Created by Dominik on 2016-01-14.
 */
public class PagerAdapterDetails extends FragmentPagerAdapter {

    public PagerAdapterDetails(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){
            case 0:
                return new FragmentMainDetails();
            case 1:
                return new FragmentMapDetails();
            default:
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
