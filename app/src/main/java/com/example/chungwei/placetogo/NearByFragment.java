package com.example.chungwei.placetogo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.chungwei.placetogo.adapters.NearbyRecyclerViewAdapter;
import com.example.chungwei.placetogo.services.foursquare.FoursquareService;
import com.example.chungwei.placetogo.services.foursquare.IFoursquareResponse;
import com.example.chungwei.placetogo.services.foursquare.models.RecommendationResult;


public class NearByFragment extends Fragment {

    private static final int LOCATION_PERMISSIONS_CODE = 0;
    private Context context;
    private FoursquareService foursquareService;
    private RecyclerView nearby_recyclerView;
    private RecyclerView.LayoutManager recyclerViewlayoutManager;
    private RecyclerView.Adapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private boolean isRefreshManuallyTrigger = false;

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
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.near_by));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_near_by, container, false);
        context = view.getContext();

        nearby_recyclerView = view.findViewById(R.id.nearBy_recyclerView);
        recyclerViewlayoutManager = new LinearLayoutManager(context);
        nearby_recyclerView.setLayoutManager(recyclerViewlayoutManager);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(this::loadNearByLocation);

        loadNearByLocation();

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSIONS_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadNearByLocation();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_nearby_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh_menuItem) {
            loadNearByLocation();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadNearByLocation() {
        isRefreshManuallyTrigger = true;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSIONS_CODE);

            return;
        }

        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("GPS is disabled");
            builder.setMessage("Please turn on the GPS and enable the permission to access location.");
            builder.setPositiveButton("Go to GPS settings", (dialogInterface, i) -> {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                dialogInterface.dismiss();
            });
            builder.setNegativeButton("Close", null);
            builder.create().show();

            return;
        }

        swipeRefreshLayout.setRefreshing(true);

        // GPS_PROVIDER got problem, use NETWORK_PROVIDER instead ///NETWORK_PROVIDER
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new
                LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (!isRefreshManuallyTrigger) return;

                        //get coordinate
                        String ll = String.valueOf(location.getLatitude()) +
                                "," + String.valueOf(location.getLongitude());

                        //fetch data
                        foursquareService = new FoursquareService(context);
                        foursquareService.getVenueRecommendation(new IFoursquareResponse<RecommendationResult>() {
                            @Override
                            public void onResponse(RecommendationResult recommendationResult) {
                                recyclerViewAdapter =
                                        new NearbyRecyclerViewAdapter(
                                                context,
                                                recommendationResult.getResponse().getGroups().get(0).getItems(),
                                                foursquareService);
                                nearby_recyclerView.setAdapter(recyclerViewAdapter);

                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                swipeRefreshLayout.setRefreshing(false);

                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Error Occur");
                                builder.setMessage("Something went wrong. Please try again later.");
                                builder.setPositiveButton("Close", null);
                            }
                        }, ll, null, 20);

                        isRefreshManuallyTrigger = false;
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });
    }
}
