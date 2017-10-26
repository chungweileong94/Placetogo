package com.example.chungwei.placetogo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.chungwei.placetogo.services.apiai.APIAIService;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView location_RecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter location_RecyclerView_Adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Here's what we got");

        location_RecyclerView = findViewById(R.id.location_RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        location_RecyclerView.setLayoutManager(layoutManager);

        String type = getIntent().getExtras().getString("searchType");

        switch (type) {
            case APIAIService.SEARCH:

                break;
        }
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
