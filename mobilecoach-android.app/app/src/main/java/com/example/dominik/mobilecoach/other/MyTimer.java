package com.example.dominik.mobilecoach.other;

import android.os.Handler;
import android.os.Messenger;
import android.util.Log;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * Created by Dominik on 2015-09-17.
 */
public class MyTimer extends TimerTask {

    public Handler handler;
    public long sek,min,hor,time;
    public TextView timerClock;

    public MyTimer(Handler h, TextView t){
        handler = h;
        timerClock = t;
        sek = 0;
        min = 0;
        hor = 0;
        time = 0;
    }

    @Override
    public void run() {

        handler.post(new Runnable() {
            @Override
            public void run() {

                time++;
                sek++;
                if(sek==60){
                    sek = 0;
                    min++;
                    if(min == 60){
                        min = 0;
                        hor++;
                    }
                }
                timerClock.setText(String.format("%02d:%02d:%02d", hor, min, sek));
            }
        });

        handler.postDelayed(this,1000);
    }
}
