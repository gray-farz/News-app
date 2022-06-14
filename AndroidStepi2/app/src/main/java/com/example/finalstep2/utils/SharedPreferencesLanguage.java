package com.example.finalstep2.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesLanguage {

    private SharedPreferences sharedPreferences;

    public SharedPreferencesLanguage(Context context)
    {
        sharedPreferences = context.getSharedPreferences("shared_ln",Context.MODE_PRIVATE);
    }

    public void saveLn(String language)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ln",language);
        editor.apply();
    }

    public String getLn()
    {
        return sharedPreferences.getString("ln","fa");
    }

}
