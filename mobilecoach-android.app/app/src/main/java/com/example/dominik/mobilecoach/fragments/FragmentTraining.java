package com.example.dominik.mobilecoach.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.MainActivity;
import com.example.dominik.mobilecoach.activities.TrainingActivity;
import com.example.dominik.mobilecoach.model.TrackTable;
import com.example.dominik.mobilecoach.model.TrainingSesion;
import com.example.dominik.mobilecoach.model.SerwisPlan;
import com.example.dominik.mobilecoach.model.SerwisSession;
import com.example.dominik.mobilecoach.other.MyTimer;
import com.example.dominik.mobilecoach.services.GPS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dominik on 2015-08-19.
 */
public class FragmentTraining extends Fragment {

    public Spinner txtChoseActivity;
    public TextView distanceView;
    public TextView timerView;
    public TextView gpsListner;
    public TextView speedView;
    public TextView calorieView;
    public ImageButton imageMapButton;
    public ImageButton startButton;
    public MyTimer counter;
    public Handler handler = new Handler();
    public static Context context;
    public Activity activity = TrainingActivity.activity;
    public static boolean timerRun = false;
    public static Intent services;
    public boolean isBind = false;
    public String list[];
    public String onOff[];
    public int sateliteNumber;
    public boolean allowToTrain = false;
    public ImageButton endBtn;
    public double calories;
    public double distance;
    public double speed;
    public double MET = 13.5;

    public final String FILE_NAME = "DetailsPerson";
    public final String DB_EXIST = "DateBase";
    public final String AGE_VARIABLE = "ageVariable";
    public final String WEIGH_VARIABLE = "weighVariable";
    public final String GROWTH_VARIABLE = "growthVariable";
    public final String SEX_VARIABLE = "SexVariable";
    public final String BMI_VARIABLE = "bmiVariable";
    public final String BMR_VARIABLE = "bmrVariable";
    public float BMR = 0;
    public AlertDialog.Builder dialog;
    public TrainingSesion trainingSesion;
    public static ArrayList<TrackTable> track;
    public float avargeSpeed;
    public int speedSamples;

    @Nullable
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_training, container, false);
        startButton = (ImageButton) view.findViewById(R.id.btnStart);
        distanceView = (TextView) view.findViewById(R.id.distanceView);
        timerView = (TextView) view.findViewById(R.id.timerView);
        gpsListner = (TextView) view.findViewById(R.id.gpsListner);
        speedView = (TextView) view.findViewById(R.id.speedView);
        imageMapButton = (ImageButton) view.findViewById(R.id.imageGoMap);
        endBtn = (ImageButton) view.findViewById(R.id.endBtn);
        calorieView = (TextView) view.findViewById(R.id.caloriesViewText);
        context = getActivity().getApplicationContext();
        BMR = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getFloat(BMR_VARIABLE, 0);
        MET = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getFloat(TrainingActivity.ACTIVITY_FACTOR, 13.5f);
        avargeSpeed = 0;
        speedSamples = 0;
        track = new ArrayList<TrackTable>();
        trainingSesion = new TrainingSesion();

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });

        boolean dbexist = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(DB_EXIST, false);
        if (!dbexist) {

            SerwisPlan db = new SerwisPlan(MainActivity.activity.getApplicationContext(), null);
            db.addPlan(8, 1);
            db.close();
            getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit().putBoolean(DB_EXIST, true).apply();
        }

        dialog = new AlertDialog.Builder(TrainingActivity.activity);
        dialog.setTitle(R.string.endTraining);
        dialog.setMessage(R.string.endTrainingMessage);
        dialog.setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                trainingSesion.setCalories((int) calories);
                trainingSesion.setDistance((float) distance);
                trainingSesion.setWeight(0);
                trainingSesion.setTime(counter.time);
                trainingSesion.setSpeed(avargeSpeed / ((float) speedSamples) * 3.60f);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(new Date());

                trainingSesion.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                SerwisSession db = new SerwisSession(getActivity().getApplicationContext(), null);
                db.addSession(trainingSesion, track);
                db.close();

                getActivity().finish();
            }
        });
        dialog.setNegativeButton(R.string.nie, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        imageMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingActivity.viewPager.setCurrentItem(TrainingActivity.viewPager.getCurrentItem() + 1);
            }
        });

        counter = new MyTimer(handler, timerView);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timerRun = !timerRun;
                if (timerRun) {
                    handler.postDelayed(counter, 1000);
                    startButton.setImageResource(R.mipmap.pause);
                    sendMessage(2);
                    endBtn.setVisibility(ImageButton.INVISIBLE);
                } else {
                    handler.removeCallbacks(counter);
                    startButton.setImageResource(R.mipmap.play);
                    sendMessage(3);
                    endBtn.setVisibility(ImageButton.VISIBLE);
                }
            }
        });

        // Lista treningów
        txtChoseActivity = (Spinner) view.findViewById(R.id.popMenu);
        list = MainActivity.activity.getResources().getStringArray(R.array.list_activities);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(MainActivity.activity.getApplicationContext(), R.layout.spinner_item, list);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        txtChoseActivity.setAdapter(adapter);
        txtChoseActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences.Editor editor = getActivity().getSharedPreferences(TrainingActivity.FILE_NAME, Context.MODE_PRIVATE).edit();
                editor.putString(TrainingActivity.ACTIVITY_VARIABLE, list[position]).apply();
                switch(position) {
                    case 0:
                        editor.putFloat(TrainingActivity.ACTIVITY_FACTOR, 13.5f).apply();
                        MET = 13.5;
                        break;
                    case 1:
                        editor.putFloat(TrainingActivity.ACTIVITY_FACTOR, 4.5f).apply();
                        MET = 4.5;
                        break;
                    case 2:
                        editor.putFloat(TrainingActivity.ACTIVITY_FACTOR, 10.00f).apply();
                        MET = 10.00;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        services = new Intent(getActivity().getApplicationContext(), GPS.class);
        getActivity().getApplicationContext().bindService(services, connection, Context.BIND_AUTO_CREATE);
        return view;
    }

    @Override
    public void onDestroy() {

        handler.removeCallbacks(counter);
        counter.cancel();
        timerRun = false;
        sendMessage(3);
        getActivity().getApplicationContext().unbindService(connection);
        super.onDestroy();
    }

    public void sendMessage(int arg) {

        Message message = new Message();
        message.replyTo = messageResponder;
        Bundle bundle = new Bundle();
        bundle.putInt("var", arg);
        message.setData(bundle);

        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Messenger messenger;
    public ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            messenger = new Messenger(service);
            isBind = true;
            sendMessage(0);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            Toast.makeText(context, "USUNIETO", Toast.LENGTH_SHORT).show();
            sendMessage(1);
            messenger = null;
            isBind = false;
        }
    };

    Messenger messageResponder = new Messenger(new ResponseHandler());

    class ResponseHandler extends Handler {

        private final int SET_DISTANCE_VIEW = 0;
        private final int SET_GPS_READY = 1;
        int arg;

        @Override
        public void handleMessage(Message msg) {

            arg = msg.getData().getInt("var");
            switch (arg) {
                case SET_DISTANCE_VIEW:

                    if (isBind) {
                        String txt = (String) msg.getData().get("DistanceView");
                        distanceView.setText(txt);
                        distance = msg.getData().getFloat("distance");
                        speed = msg.getData().getFloat("speed");
                        speedSamples += 1;
                        avargeSpeed += speed;
                        calories = (BMR / 24) * MET * ((double) counter.time / 3600.000);
                        calorieView.setText(String.format("%d kcal", (int) calories));
                        distanceView.setText(String.format("%.02f", distance / 1000) + " km");
                        speedView.setText(String.format("%.02f", (avargeSpeed / (float) speedSamples) * 3.6) + " km/h");
                    }
                    break;
                case SET_GPS_READY:

                    sateliteNumber = (int) msg.getData().get("sateliteNumber");
                    if (sateliteNumber >= 8) {
                        gpsListner.setText(R.string.GpsListner1);
                    } else {
                        gpsListner.setText(R.string.GpsListner2);
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}
