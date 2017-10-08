package com.example.chungwei.placetogo.services.foursquare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class FoursquareService {

    private Context context;
    private RequestQueue requestQueue;
    private final static String client_id = "S0VBO5HFPGFXPEU23HOIOUXNP2IXFAOBRFEU0C0NMGPYNVDO";
    private final static String client_secret = "ZN0UHF0F234HLLHIHNGZXBS3IPKP2TSTESI24HUOUNQ212JQ";
    private final static String version = "20170801";

    private Gson gson;

    public FoursquareService(@NonNull Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create();
    }

    public void getVenueRecommendation(@NonNull final IFoursquareResponse<Result> callback, @Nullable String ll, @Nullable String query, @NonNull int limit) {
        String url = setupURL("https://api.foursquare.com/v2/venues/explore") +
                applyParameter("ll", ll) +
                applyParameter("near", ll == null ? "Penang, Malaysia" : "") +
                applyParameter("query", query) +
                applyParameter("limit", String.valueOf(limit));

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Result result = gson.fromJson(response, Result.class);
                        callback.onResponse(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onErrorResponse(error);
                    }
                });

        requestQueue.add(request);
    }

    private String setupURL(@NonNull String url) {
        return String.format(
                "%s?client_id=%s&client_secret=%s&v=%s",
                url,
                client_id,
                client_secret,
                version);
    }

    private String applyParameter(@NonNull String parameter, @Nullable String value) {
        if (value != null && !value.trim().equals("")) {
            return String.format("&%s=%s", parameter, value);
        }

        return "";
    }
}
