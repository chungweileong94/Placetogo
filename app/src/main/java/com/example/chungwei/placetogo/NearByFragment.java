package com.example.chungwei.placetogo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.chungwei.placetogo.services.foursquare.FoursquareService;
import com.example.chungwei.placetogo.services.foursquare.IFoursquareResponse;
import com.example.chungwei.placetogo.services.foursquare.Result;


public class NearByFragment extends Fragment {

    private FoursquareService foursquareService;
    private RecyclerView nearBy_RecyclerView;
    private RecyclerView.LayoutManager recyclerViewlayoutManager;

    public NearByFragment() {
        // Required empty public constructor
    }

    public static NearByFragment newInstance() {
        NearByFragment fragment = new NearByFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_near_by, container, false);

        nearBy_RecyclerView = view.findViewById(R.id.nearBy_RecyclerView);
        recyclerViewlayoutManager = new LinearLayoutManager(view.getContext());
        nearBy_RecyclerView.setLayoutManager(recyclerViewlayoutManager);

        foursquareService = new FoursquareService(view.getContext());
        foursquareService.getVenueRecommendation(new IFoursquareResponse<Result>() {
            @Override
            public void onResponse(Result result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Error Occur");
                builder.setMessage("Something went wrong. Please try again later.");
                builder.setPositiveButton("Close", null);
            }
        }, "5.4159986,100.3410788", null, 10);

        return view;
    }
}
