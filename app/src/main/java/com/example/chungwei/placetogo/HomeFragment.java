package com.example.chungwei.placetogo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import jp.wasabeef.blurry.Blurry;


public class HomeFragment extends Fragment {

    private ImageView background_imageView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        background_imageView = view.findViewById(R.id.background_imageView);
        background_imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                blurBackground(view.getContext());
                background_imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        //disable auto focus
        ScrollView scrollView = view.findViewById(R.id.content_scrollView);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.requestFocusFromTouch();
                return false;
            }
        });

        view.findViewById(R.id.search_floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Search happening (natural language processing)");
                builder.create().show();
            }
        });

        view.findViewById(R.id.mission_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation)
                        .replace(R.id.content_frameLayout, MissionFragment.newInstance())
                        .commit();
                activity.setTitle(R.string.mission);
            }
        });

        return view;
    }

    private void blurBackground(Context context) {
        Blurry.with(context)
                .radius(25)
                .sampling(6)
                .color(Color.argb(100, 0, 0, 0))
                .capture(background_imageView)
                .into(background_imageView);
    }
}
