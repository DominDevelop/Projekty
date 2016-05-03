package com.example.dominik.mobilecoach.other;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.MainActivity;

/**
 * Created by Dominik on 2015-08-19.
 */
public class TransactionManager {
    public static void changeView(FragmentManager fragmentManager,Fragment fragment,String tag){

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
    }
}
