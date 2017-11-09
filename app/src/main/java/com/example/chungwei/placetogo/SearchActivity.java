package com.example.chungwei.placetogo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.example.chungwei.placetogo.adapters.SearchRecyclerViewAdapter;
import com.example.chungwei.placetogo.services.foursquare.FoursquareService;
import com.example.chungwei.placetogo.services.foursquare.IFoursquareResponse;
import com.example.chungwei.placetogo.services.foursquare.models.RecommendationResult;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView location_RecyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private ProgressBar loading_progressBar;
    private FoursquareService foursquareService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Here's what we got");

        location_RecyclerView = findViewById(R.id.location_RecyclerView);
        location_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loading_progressBar = findViewById(R.id.loading_progressBar);

        String query = getIntent().getStringExtra("searchQuery");

        Context context = this;
        //fetch data
        loading_progressBar.setVisibility(View.VISIBLE);
        foursquareService = new FoursquareService(this);
        foursquareService.getSearchVenue(new IFoursquareResponse<RecommendationResult>() {
            @Override
            public void onResponse(RecommendationResult recommendationResult) {
                recyclerViewAdapter =
                        new SearchRecyclerViewAdapter(
                                context,
                                recommendationResult.getResponse().getGroups().get(0).getItems(),
                                foursquareService);
                location_RecyclerView.setAdapter(recyclerViewAdapter);
                loading_progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Error Occur");
                builder.setMessage("Something went wrong. Please try again later.");
                builder.setPositiveButton("Close", null);
                loading_progressBar.setVisibility(View.GONE);
            }
        }, null, query, 10);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
