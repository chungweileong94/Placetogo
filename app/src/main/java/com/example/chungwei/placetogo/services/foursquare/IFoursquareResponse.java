package com.example.chungwei.placetogo.services.foursquare;

import com.android.volley.VolleyError;

public interface IFoursquareResponse<T> {
    void onResponse(T result);

    void onErrorResponse(VolleyError error);
}
