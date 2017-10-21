package com.example.chungwei.placetogo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.chungwei.placetogo.services.apiai.APIAIService;

import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.model.AIError;
import ai.api.model.AIResponse;


public class HomeFragment extends Fragment implements AIListener{
    private APIAIService apiaiService;
    //private static final int RECORD_AUDIO_PERMISSION = 0;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiaiService = new APIAIService(getContext(),this);
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
        final EditText editText = view.findViewById(R.id.search_editText);


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



        //search button//
        view.findViewById(R.id.search_floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                SendRequest(text);
            }
        });

        //challenge card view on click
        view.findViewById(R.id.mission_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(),ChallengeActivity.class);
                startActivity(intent);

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

    private void SendRequest(String text) {
        AsyncTask<String, Integer, AIResponse> task = new AsyncTask<String, Integer, AIResponse>() {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = new ProgressDialog(getContext());
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                dialog.setMax(100);
                dialog.show();
            }

            @Override
            protected AIResponse doInBackground(String... strings) {

                AIResponse result=null;
                try {
                    for(int i = 0; i<2;i++){
                        publishProgress(50);
                        result = apiaiService.textRequest(strings[0]);
                    }
                    dialog.dismiss();

                    return result;
                } catch (AIServiceException e) {
                    //handle error
                    return null;
                }
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                dialog.incrementProgressBy(values[0]);
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                //TODO
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                switch (aiResponse.getResult().getAction().toString()){
                    case "maps.nearby_location" :
                        builder.setMessage("Here is what I can get for you!");
                        builder.create().show();
                        nearByFragment(); break;
                    case "maps.current_location":
                        builder.setMessage("Show current location");
                        builder.create().show();
                        break;
                    case "maps.search":
                        builder.setMessage("Places : "+aiResponse.getResult().getParameters().get("Places").getAsString());
                        builder.create().show();
                        break;
                    default:
                        builder.setMessage("I do not understand what you said.");
                        builder.create().show();
                }
            }
        };

        task.execute(text);
    }

    public void nearByFragment(){
        //TODO --fix the function
        AppCompatActivity activity = (AppCompatActivity) getView().findViewById(R.id.nearby_cardView).getContext();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation,
                        R.anim.fade_in_animation, R.anim.fade_out_animation)
                .replace(R.id.content_frameLayout, NearByFragment.newInstance())
                .addToBackStack(MainActivity.fragment_nav_backstack_tag)
                .commit();
    }

    @Override
    public void onResult(AIResponse result) {

    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}
