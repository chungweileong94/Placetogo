package com.example.chungwei.placetogo.services.placetogo;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chungwei.placetogo.services.placetogo.models.ChallengeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class PlacetogoService {

    private Context context;
    private RequestQueue requestQueue;
    private Gson gson;

    public PlacetogoService(@NonNull Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        gson = new GsonBuilder().create();
    }

    public void getChallenges(IPlacetogoResponse<ChallengeResult> callback) {
        String url = "https://placetogo.herokuapp.com/api/challenge";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    response = "{\"challenges\": " + response + "}";
                    ChallengeResult challengeResult = gson.fromJson(response, ChallengeResult.class);
                    callback.onResponse(challengeResult);
                },
                error -> callback.onErrorResponse(error));

        requestQueue.add(request);
    }
}
