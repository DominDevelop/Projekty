package com.example.dominik.mobilecoach.other;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dominik.mobilecoach.R;

/**
 * Created by Dominik on 2015-08-18.
 */
public class MyAdapter extends ArrayAdapter<String> {

    private String[] list;
    private int[] images;
    private Context content;

    public MyAdapter(Context context, int resource, String[] menu, int[] image) {
        super(context, resource);

        list = menu;
        images = image;
        content = context;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder holder = null;
        View row = null;

        /*
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) content.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.item_list, parent, false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        }else {

            holder = (MyViewHolder) row.getTag();
        } */

        LayoutInflater layoutInflater = (LayoutInflater) content.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = layoutInflater.inflate(R.layout.item_list, parent, false);
        holder = new MyViewHolder(row);

        if(holder != null) {
            holder.imageView.setImageResource(images[position]);
            holder.textView.setText(list[position]);
            holder.textView.setTextColor(Color.parseColor("#000000"));
        }
        return row;
    }

    class MyViewHolder{
        private ImageView imageView;
        private TextView textView;

        public MyViewHolder(View view){
            imageView = (ImageView)view.findViewById(R.id.imageItem);
            textView = (TextView)view.findViewById(R.id.textItem);
        }
    }

}
