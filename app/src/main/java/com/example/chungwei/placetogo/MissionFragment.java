package com.example.chungwei.placetogo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chungwei.placetogo.adapters.MissionRecyclerViewAdapter;

import java.util.ArrayList;


public class MissionFragment extends Fragment {

    private RecyclerView rView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public MissionFragment() {
        // Required empty public constructor
    }

    public static MissionFragment newInstance() {
        MissionFragment fragment = new MissionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.mission));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mission, container, false);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("Challenge " + (i + 1) + (i != 0 ? " (locked)" : ""));
        }

        rView = view.findViewById(R.id.rView);
        adapter = new MissionRecyclerViewAdapter(view.getContext(), list);
        layoutManager = new LinearLayoutManager(view.getContext());
        rView.setAdapter(adapter);
        rView.setLayoutManager(layoutManager);

        return view;
    }
}
