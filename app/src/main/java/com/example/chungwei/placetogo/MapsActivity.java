package com.example.chungwei.placetogo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Declaration for the google map.
    private GoogleMap mMap;
    //Map layout type static variable
    static int maptypevalue = 1;

    FloatingActionButton marker,type;
    String name;
    double longi, latit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Floating icon declaration for the marker and map type.
        marker = (FloatingActionButton) findViewById(R.id.markerposition);
        type = (FloatingActionButton) findViewById(R.id.maptype);
        //Programmatically clicking the marker button
        marker.performClick();

        //Marker floating button declaration
        marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMapReady(mMap);
            }
        });

        //Map type floating button declaration
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If else to change the map type.
                if(maptypevalue == 0){
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    maptypevalue=1;
                    Snackbar snackbar = Snackbar
                            .make(view, "Map is now changed to NORMAL type.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if(maptypevalue == 1){
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    maptypevalue=2;
                    Snackbar snackbar = Snackbar
                            .make(view, "Map is now changed to HYBRID type.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if(maptypevalue == 2){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    maptypevalue=3;
                    Snackbar snackbar = Snackbar
                            .make(view, "Map is now changed to SATELLITE type.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else if(maptypevalue == 3){
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    maptypevalue=0;
                    Snackbar snackbar = Snackbar
                            .make(view, "Map is now changed to TERRAIN type.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                else{
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Snackbar snackbar = Snackbar
                            .make(view, "No changes", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
    }

    public void onLocationChanged(Location location) {
        mMap.clear();
        MarkerOptions mp = new MarkerOptions();
        mp.position(new LatLng(location.getLatitude(), location.getLongitude()));
        mp.title("my position");

        mMap.addMarker(mp);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        View v = null;

        Intent intent = getIntent();

        name = intent.getExtras().getString("placeName");
        longi = intent.getExtras().getDouble("place_long");;
        latit = intent.getExtras().getDouble("place_lati");;

        mMap = googleMap;

        LatLng location = new LatLng(latit,longi);
        int permissionLocation = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionLocation == PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
        moveToCurrentLocation(location);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().isMyLocationButtonEnabled();
        googleMap.getUiSettings().isMapToolbarEnabled();
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.addMarker(new
                MarkerOptions().position(location).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }


    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

}