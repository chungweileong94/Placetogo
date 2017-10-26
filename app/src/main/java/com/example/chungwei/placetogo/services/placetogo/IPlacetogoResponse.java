package com.example.chungwei.placetogo.services.placetogo;

import com.android.volley.VolleyError;

public interface IPlacetogoResponse<T> {
    void onResponse(T result);

    void onErrorResponse(VolleyError error);
}
