package com.example.chungwei.placetogo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chungwei.placetogo.services.TTS;
import com.example.chungwei.placetogo.services.apiai.APIAIService;

import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.model.AIError;
import ai.api.model.AIResponse;


public class HomeFragment extends Fragment {
    private APIAIService apiaiService;
    private static final int RECORD_AUDIO_PERMISSION = 0;

    private FloatingActionButton search_floatingActionButton;
    AlertDialog dlg;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiaiService = new APIAIService(getContext(), new AIListener() {
            @Override
            public void onResult(AIResponse result) {
                searchResponse(result, true);
            }

            @Override
            public void onError(AIError error) {

            }

            @Override
            public void onAudioLevel(float level) {

            }

            @Override
            public void onListeningStarted() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setPositiveButton("Cancel", (d, w) -> {
                    apiaiService.stopListening();
                });
                builder.setMessage("Listening");
                builder.setCancelable(true);
                dlg = builder.create();
                dlg.show();

            }

            @Override
            public void onListeningCanceled() {

            }

            @Override
            public void onListeningFinished() {
                dlg.dismiss();
            }
        });
        TTS.init(getContext());
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
        final EditText search_editText = view.findViewById(R.id.search_editText);

        search_editText.setOnEditorActionListener((v, actionId, event) -> {
            String text = search_editText.getText().toString().trim();

            if (!text.isEmpty()) {
                SendRequest(text);
            }

            return true;
        });

        //search button
        search_floatingActionButton = view.findViewById(R.id.search_floatingActionButton);
        search_floatingActionButton.setOnClickListener(v -> {
            String text = search_editText.getText().toString().trim();

            if (!text.isEmpty()) {
                SendRequest(text);
            }
        });

        search_floatingActionButton.setOnLongClickListener(v -> {
            checkAudioRecordPermission();
            apiaiService.startListening();
            return true;
        });

        //challenge card view on click
        view.findViewById(R.id.mission_cardView).setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), ChallengeActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void SendRequest(String text) {
        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, Integer, AIResponse> task = new AsyncTask<String, Integer, AIResponse>() {
            Dialog dialog;

            @Override
            protected void onPreExecute() {
                dialog = new Dialog(getContext(), android.R.style.Theme_Material_Dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.search_loading_popup_layout);
                dialog.setCancelable(false);
                dialog.show();
            }

            @Override
            protected AIResponse doInBackground(String... strings) {
                AIResponse result = null;
                try {
                    result = apiaiService.textRequest(strings[0]);
                    dialog.dismiss();

                    return result;
                } catch (AIServiceException e) {
                    //handle error
                    return null;
                }
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                searchResponse(aiResponse, false);
            }
        };

        task.execute(text);
    }

    private void searchResponse(AIResponse aiResponse, boolean tts) {
        switch (aiResponse.getResult().getAction()) {
            case APIAIService.NEARBY_LOCATION:
                AppCompatActivity activity = (AppCompatActivity) getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in_animation, R.anim.fade_out_animation,
                                R.anim.fade_in_animation, R.anim.fade_out_animation)
                        .replace(R.id.content_frameLayout, NearByFragment.newInstance())
                        .addToBackStack(MainActivity.fragment_nav_backstack_tag)
                        .commit();
                if (tts) TTS.speak("Searching for nearby location for you!");
                break;
            case APIAIService.SEARCH:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("searchQuery", aiResponse.getResult().getParameters().get("Places").getAsString());
                getContext().startActivity(intent);
                break;
            default:
                Toast.makeText(getContext(), "I do not understand what you said.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    protected void checkAudioRecordPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.RECORD_AUDIO)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        RECORD_AUDIO_PERMISSION);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RECORD_AUDIO_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                }
                return;
            }
        }
    }
}
