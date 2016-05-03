package com.example.dominik.mobilecoach.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.MainActivity;
import com.example.dominik.mobilecoach.activities.TrainingActivity;
import com.example.dominik.mobilecoach.fragments.FragmentTraining;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * Created by Dominik on 2015-09-16.
 */
public class GPS extends Service implements LocationListener{

    private LocationManager locationManager;
    private Location location;
    boolean gpsEnabled = false;
    boolean fisrtChange = true;
    public double latitude;
    public double longitude;
    public float distance;
    public long time;
    public final long PERIOD_TIME = 1200;
    public GpsStatus gpsStatus;
    public int ev;

    public void performGps() {

        this.distance = 0;
        this.time = 0;
        this.ev = 0;
        this.locationManager = (LocationManager) TrainingActivity.activity.getSystemService(Context.LOCATION_SERVICE);
        this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        this.locationManager.addGpsStatusListener(new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        String s = LocationManager.NETWORK_PROVIDER;

                        int sum = 0;
                        if(ev == GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
                            gpsStatus = locationManager.getGpsStatus(null);
                            if (gpsStatus != null) {
                                Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
                                Iterator<GpsSatellite> sat = satellites.iterator();
                                while (sat.hasNext()) {
                                    GpsSatellite satellite = sat.next();
                                    if(satellite.usedInFix()){
                                        sum++;
                                    }
                                }
                            }
                        }

                        bundle.putInt("sateliteNumber",sum);
                        bundle.putInt("var", 1);
                        message.setData(bundle);
                        try {
                            client.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });

                ev = event;
                thread.run();
            }
        });
    }

    public void stopGps(){

     //   locationManager.removeUpdates(this);
        gpsEnabled = false;
    }

    public void startGps(){

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,PERIOD_TIME,5,this);
        gpsEnabled = true;
    }

    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {

            if(fisrtChange){
                latitude = currentLocation.getLatitude();
                longitude = currentLocation.getLongitude();
                fisrtChange = false;
            }

            double newLat,newLon;
            float meters[] = new float[2];
            newLat = currentLocation.getLatitude();
            newLon = currentLocation.getLongitude();

            Bundle bundleMap = new Bundle();
            bundleMap.putDouble("latitude", latitude);
            bundleMap.putDouble("longitude", longitude);
            bundleMap.putDouble("newLat", newLat);
            bundleMap.putDouble("newLon", newLon);
            bundleMap.putInt("var", 0);

            Location.distanceBetween(latitude, longitude, newLat, newLon, meters);
            distance += meters[0]/1000;
            latitude = newLat;
            longitude = newLon;

            Bundle bundleTraining = new Bundle();
            bundleTraining.putFloat("distance", distance * 1000);
            bundleTraining.putFloat("speed",currentLocation.getSpeed());
            bundleTraining.putInt("var",0);
            bundleTraining.putString("DistanceView", String.format("%.02f", distance) + " km");

            Message messageTraining = new Message();
            Message messageMap = new Message();
            messageTraining.setData(bundleTraining);
            messageMap.setData(bundleMap);

            try {
                client.send(messageTraining);
                mapClient.send(messageMap);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });

    public Location currentLocation;
    @Override
    public void onLocationChanged(Location location) {

        if(gpsEnabled){

            currentLocation = location;
            thread.run();
        }else {

            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void removeUpdates(){

        locationManager.removeUpdates(this);
        stopService(FragmentTraining.services);
        stopSelf();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(FragmentTraining.context,"Włączono GPS",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(FragmentTraining.context,"Wyłączono GPS",Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    public final int START_SERVICE = 0;
    public final int STOP_SERVICE = 1;
    public final int START_GPS_LISTNER = 2;
    public final int STOP_GPS_LISTNER = 3;
    public final int SET_MAP_CONNECT = 4;
    public final int CLOSE_MAP_CONNECT = 5;
    public int arg;

    private Messenger client;
    private Messenger mapClient;
    private Messenger messenger = new Messenger(new IncomingHandler());
    class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            arg = msg.getData().getInt("var");
            switch (arg) {
                case START_SERVICE:
                    client = msg.replyTo;
                    performGps();
                    break;

                case STOP_SERVICE:
                    client = null;
                    gpsEnabled = false;
                    removeUpdates();
                    break;

                case START_GPS_LISTNER:
                    startGps();
                    break;

                case STOP_GPS_LISTNER:
                    stopGps();
                    break;

                case SET_MAP_CONNECT:

                    mapClient = msg.replyTo;
                    break;
                case CLOSE_MAP_CONNECT:

                    mapClient = null;
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
