package com.example.chungwei.placetogo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;


public class HomeFragment extends Fragment {

    public HomeFragment() {

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
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.home));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

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

        //search button
        view.findViewById(R.id.search_floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Search happening (natural language processing)");
                builder.create().show();
            }
        });

        //challenge card view on click
        view.findViewById(R.id.mission_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation,
                                R.anim.fade_in_animation, R.anim.fade_out_animation)
                        .replace(R.id.content_frameLayout, MissionFragment.newInstance())
                        .addToBackStack(MainActivity.fragment_nav_backstack_tag)
                        .commit();
            }
        });

        //nearby card view on click
        view.findViewById(R.id.nearby_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation,
                                R.anim.fade_in_animation, R.anim.fade_out_animation)
                        .replace(R.id.content_frameLayout, NearByFragment.newInstance())
                        .addToBackStack(MainActivity.fragment_nav_backstack_tag)
                        .commit();
            }
        });

        return view;
    }
}
