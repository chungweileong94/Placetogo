package com.example.chungwei.placetogo.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "PlaceToGo";
    private static final String FIRST_TIME_LAUNCH = "FirstTimeLaunch";
    private static final String CHALLENGE_CLEAR = "ChallengeClear";

    public PreferencesManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(FIRST_TIME_LAUNCH, true);
    }

    public void setChallengeClear(int challengeClear){
        editor.putInt(CHALLENGE_CLEAR,challengeClear);
        editor.commit();
    }

    public int getChallengeClear(){return pref.getInt(CHALLENGE_CLEAR,0);}

}