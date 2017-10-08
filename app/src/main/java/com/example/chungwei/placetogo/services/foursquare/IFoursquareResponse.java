package com.example.chungwei.placetogo.services.foursquare;

import com.android.volley.VolleyError;

public interface IFoursquareResponse<T> {
    public void onResponse(T result);

    public void onErrorResponse(VolleyError error);
}
