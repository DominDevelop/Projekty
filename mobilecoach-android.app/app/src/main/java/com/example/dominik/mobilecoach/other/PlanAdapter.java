package com.example.dominik.mobilecoach.other;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.activities.MainActivity;
import com.example.dominik.mobilecoach.fragments.FragmentPlan;
import com.example.dominik.mobilecoach.model.TrainingPlan;
import com.example.dominik.mobilecoach.model.SerwisPlan;

/**
 * Created by Dominik on 2015-09-27.
 */
public class PlanAdapter extends ArrayAdapter<String> {

    private Context content;
    private TrainingPlan plan[];

    public PlanAdapter(Context context, int resource) {
        super(context, resource);

        content = context;
        SerwisPlan database = new SerwisPlan(MainActivity.activity.getApplicationContext(), null);
        SQLiteDatabase db = database.getReadableDatabase();
        plan = database.getPlanTable();
        database.close();
    }

    @Override
    public int getCount() {
        return plan.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        MyViewHolder holder = null;
        View row = convertView;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) content.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.item_plan, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }

        String COLUMN_ACTIVITY = "activity";
        String COLUMN_TIME = "time";
    //    holder.layout.setBackgroundColor(Color.parseColor("#AF4B4B"));
        SerwisPlan db = new SerwisPlan(content, null);
        Cursor cursor = db.getActivities(plan[position].getId());
        boolean spr = cursor.moveToFirst();
        int index = cursor.getColumnIndex(COLUMN_ACTIVITY);
        int index2 = cursor.getColumnIndex(COLUMN_TIME);
        String txt = "", txt2 = "";
        while (spr) {

            txt += " " + cursor.getString(index);
            txt2 += " " + cursor.getString(index2);
            spr = cursor.moveToNext();
        }

        if (txt.contains("Regeneracja")) {
            holder.imageBtn.setVisibility(View.INVISIBLE);
        }else {
            holder.imageBtn.setVisibility(View.VISIBLE);
        }

        if (plan[position].isAccepted()) {
            holder.imageBtn.setImageResource(R.mipmap.ok);
        } else {
            holder.imageBtn.setImageResource(R.mipmap.no);
        }

        holder.activity.setText(txt);
        holder.week.setText(String.valueOf(plan[position].getWeek()));
        if (!txt.contains("Regeneracja")) {
            holder.time.setText(txt2);
        }else {
            holder.time.setText("");
        }
        holder.date.setText(plan[position].getDate());
        holder.imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("kliknieto", "position " + position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.activity);
                builder.setTitle(R.string.confirm).setMessage(R.string.cinfirmDialog);
                builder.setPositiveButton(R.string.acceptTraining, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SerwisPlan db = new SerwisPlan(MainActivity.activity, null);
                        db.updateAcceptetColumn(position + 1);
                        db.close();
                        FragmentPlan.clearListView();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.cancelTraining, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create();
                builder.show();
            }
        });

        return row;
    }

    class MyViewHolder {
        private ImageView imageBtn;
        private TextView week;
        private TextView activity;
        private TextView time;
        private TextView date;
        private RelativeLayout layout;

        public MyViewHolder(View view) {
            imageBtn = (ImageView) view.findViewById(R.id.imageButton);
            week = (TextView) view.findViewById(R.id.weekNumber);
            activity = (TextView) view.findViewById(R.id.activity);
            time = (TextView) view.findViewById(R.id.timeTraining);
            date = (TextView) view.findViewById(R.id.date);
            layout = (RelativeLayout) view.findViewById(R.id.planRow);
        }
    }
}
