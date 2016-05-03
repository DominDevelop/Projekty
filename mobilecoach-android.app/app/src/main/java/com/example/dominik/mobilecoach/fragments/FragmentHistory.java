package com.example.dominik.mobilecoach.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dominik.mobilecoach.R;
import com.example.dominik.mobilecoach.other.HistoryAdapter;

/**
 * Created by Dominik on 2015-11-26.
 */
public class FragmentHistory extends Fragment {

    public static ListView listView;
    public static Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        HistoryAdapter adapter = new HistoryAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, getActivity());
        listView = (ListView) view.findViewById(R.id.historyView);
        listView.setAdapter(adapter);
        activity = getActivity();

        adapter.getAllHistory();
        if (adapter.historyList.size() == 0){
            Toast.makeText(getActivity().getApplicationContext(),"Brak treningów",Toast.LENGTH_LONG).show();
        }

        return view;
    }

    public static void clearListView() {

        HistoryAdapter adapter = new HistoryAdapter(activity.getApplicationContext(), android.R.layout.simple_list_item_1, activity);
        listView.setAdapter(null);
        listView.setAdapter(adapter);
    }
}
