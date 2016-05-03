package com.example.dominik.mobilecoach.fragments;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.TrainingActivity;
import com.example.dominik.mobilecoach.model.TrackTable;
import com.example.dominik.mobilecoach.model.TrainingPlan;
import com.example.dominik.mobilecoach.services.GPS;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-08-24.
 */
public class FragmentMap extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap = null;
    private double latitude, longitude;
    private boolean isBind = false;
    private ImageButton imagebackButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        imagebackButton = (ImageButton) view.findViewById(R.id.imageGoTraining);
        imagebackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingActivity.viewPager.setCurrentItem(TrainingActivity.viewPager.getCurrentItem() - 1);
            }
        });

        if (googleMap == null) {
            googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap)).getMap();
        }

        getActivity().getApplicationContext().bindService(FragmentTraining.services, connection, Context.BIND_AUTO_CREATE);
        initMapObject();
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
    }

    public void initMapObject() {

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragmentMap));
        mapFragment.getMapAsync(this);
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onDestroy() {
        googleMap.clear();
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
            sendMessage(4);
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            sendMessage(5);
            messenger = null;
            isBind = false;
        }
    };

    Messenger messageResponder = new Messenger(new ResponseHandler());

    class ResponseHandler extends Handler {

        private final int DRAW_LINE = 0;
        int arg;

        @Override
        public void handleMessage(Message msg) {

            arg = msg.getData().getInt("var");
            switch (arg) {
                case DRAW_LINE:

                    if (googleMap != null && isBind) {

                        double newLat = msg.getData().getDouble("newLat");
                        double newLon = msg.getData().getDouble("newLon");
                        latitude = msg.getData().getDouble("latitude");
                        longitude = msg.getData().getDouble("longitude");
                        TrackTable trackTable = new TrackTable();
                        trackTable.setLatidude(latitude);
                        trackTable.setLongitude(longitude);
                        FragmentTraining.track.add(trackTable);
                        if (FragmentTraining.timerRun) {
                            googleMap.addPolyline(new PolylineOptions().add(new LatLng(latitude, longitude), new LatLng(newLat, newLon)).width(4));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLat, newLon), 18.5f));
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}
