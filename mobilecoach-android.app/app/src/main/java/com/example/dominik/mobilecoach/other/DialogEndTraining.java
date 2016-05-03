package com.example.dominik.mobilecoach.other;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.model.SerwisSession;

/**
 * Created by Dominik on 2015-11-23.
 */
public class DialogEndTraining extends DialogFragment {

    private Activity activity;
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void DialogEndTraining(){


    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.endTraining);
        builder.setMessage(R.string.endTrainingMessage);
        builder.setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SerwisSession db = new SerwisSession(getActivity(),null);

            }
        });

        builder.setNegativeButton(R.string.nie, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }
}
