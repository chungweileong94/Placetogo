package com.example.chungwei.placetogo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.chungwei.placetogo.helpers.PreferencesManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class AchievementFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PreferencesManager preferencesManager;

    private OnFragmentInteractionListener mListener;

    public AchievementFragment() {
        // Required empty public constructor
    }


    public static AchievementFragment newInstance() {
        AchievementFragment fragment = new AchievementFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.achievement));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_achievement, container, false);

        FloatingActionButton pearlExplorerShareButton = view.findViewById(R.id.pearlExplorerShareButton);
        CardView pearlExplorerR1CardView = view.findViewById(R.id.pearlExplorerR1CardView);
        AppCompatImageView imageViewLockPE = view.findViewById(R.id.imageViewLockPE);

        preferencesManager = new PreferencesManager(getActivity());

        if (!preferencesManager.isChallenge1IsOPEN()) {
            pearlExplorerShareButton.setVisibility(View.VISIBLE);
            imageViewLockPE.setVisibility(View.GONE);
        }
        else{
            pearlExplorerShareButton.setVisibility(View.GONE);
            imageViewLockPE.setVisibility(View.VISIBLE);
        }

        pearlExplorerR1CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation hideAni;
                hideAni = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_animation);

                preferencesManager.setChallenge1IsOPEN(false);
                pearlExplorerShareButton.setVisibility(View.VISIBLE);
                imageViewLockPE.startAnimation(hideAni);
                imageViewLockPE.setVisibility(View.GONE);
            }
        });

        pearlExplorerShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageWriteChecker(getActivity());
            }
        });

        return view;
    }

    private void storageWriteChecker(Context context) {
        final int REQUEST_STORAGE_PERMISSION = 619;

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Allow and try again.", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", null)
                    .show();
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);

            Bitmap bitmap;
            OutputStream output;

            bitmap = BitmapFactory.decodeResource(getResources(),
                    R.drawable.cert1);

            File filepath = Environment.getExternalStorageDirectory();

            File dir = new File(filepath.getAbsolutePath() + "/PlaceToGo/");
            dir.mkdirs();

            // Create a name for the saved image
            File file = new File(dir, "achievement.png");

            try {

                // Share Intent
                Intent share = new Intent(Intent.ACTION_SEND);

                // Type of file to share
                share.setType("image/jpeg");

                output = new FileOutputStream(file);

                // Compress into png format image from 0% - 100%
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                output.flush();
                output.close();

                // Locate the image to Share
                Uri uri = Uri.fromFile(file);

                // Pass the image into an Intnet
                share.putExtra(Intent.EXTRA_STREAM, uri);

                // Show the social share chooser list
                startActivity(Intent.createChooser(share, "PlaceToGo Achievement Sharing"));

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
