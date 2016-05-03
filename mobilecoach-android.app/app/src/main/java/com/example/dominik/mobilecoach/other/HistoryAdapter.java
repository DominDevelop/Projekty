package com.example.dominik.mobilecoach.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.TrainingDetail;
import com.example.dominik.mobilecoach.fragments.FragmentHistory;
import com.example.dominik.mobilecoach.model.TrainingSesion;
import com.example.dominik.mobilecoach.model.SerwisSession;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-11-26.
 */
public class HistoryAdapter extends ArrayAdapter<String> {

    public ArrayList<TrainingSesion> historyList;
    private Context context;
    private Activity activity;

    public HistoryAdapter(Context context, int resource,Activity activity) {
        super(context, resource);
        this.context = context;
        this.activity = activity;
        getAllHistory();
    }

    public void getAllHistory(){

        SerwisSession db = new SerwisSession(context,null);
        historyList = db.getHistory();
        db.close();
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyViewHolder holder;
        View row = convertView;

        if(convertView == null){

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.item_history, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        }else {
            holder = (MyViewHolder) row.getTag();
        }

        holder.date.setText(historyList.get(position).getDate());
        holder.distance.setText(String.format("%.02f km", historyList.get(position).getDistance() / 1000.0000));
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity.getApplicationContext(), TrainingDetail.class);

                Bundle bundle = new Bundle();
                bundle.putInt("id", historyList.get(position).getId());
                intent.putExtra("sesja", bundle);
                activity.startActivity(intent);
            }
        });
        holder.trashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SerwisSession db = new SerwisSession(activity.getApplicationContext(),null);
                db.deleteSession(historyList.get(position).getId());
                db.close();

                FragmentHistory.clearListView();
                Toast.makeText(context,"Usunieto",Toast.LENGTH_SHORT).show();
            }
        });

        return row;
    }

    public class MyViewHolder{

        public View view;
        public TextView date;
        public TextView distance;
        public ImageButton imageButton;
        public ImageButton trashButton;
        public MyViewHolder(View view) {
            this.view = view;
            date = (TextView)view.findViewById(R.id.dateView);
            distance = (TextView)view.findViewById(R.id.distanceTraining);
            imageButton = (ImageButton)view.findViewById(R.id.detailTraining);
            trashButton = (ImageButton)view.findViewById(R.id.trashBtn);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
