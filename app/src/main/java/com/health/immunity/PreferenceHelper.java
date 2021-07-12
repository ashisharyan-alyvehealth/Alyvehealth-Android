package com.health.immunity;


import android.content.Context;
import android.content.SharedPreferences;

import com.health.immunity.IConstant;
import com.google.gson.Gson;

public class PreferenceHelper implements IConstant {

    public static final String PREF_NAME = PreferenceHelper.class.getName();
    public static final String PERMANENT_PREF_NAME = PreferenceHelper.class.getName() + "remember";
    private static SharedPreferences preferences;

    public static void setStringPreference(Context context, String KEY,
                                           String value) {
        preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY, "" + value);
        editor.commit();
    }

    public static void setGsonPreference(Context context, String KEY,
                                         Object value) {
        preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString(KEY, json);
        editor.commit();
    }

    public static String getGsonPreference(Context context, String KEY) {
        preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        String json = preferences.getString(KEY,"");
        return json;
    }


    public static String getStringPreference(Context context, String KEY) {
        preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return preferences.getString(KEY, "");
    }

    public static void setIntPreference(Context context, String KEY, int value) {
        preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY, value);
        editor.commit();
    }
    public static boolean contain(String key, Context con) {
        preferences= con.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return preferences.contains(key);
    }
    public static boolean delete(String key, Context con) {
        preferences= con.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        if (preferences.contains(key)) {
            preferences.edit().remove(key).commit();
            return true;
        } else {
            return false;
        }
    }
    public static int getIntPreference(Context context, String KEY) {
        preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        return preferences.getInt(KEY, 0);
    }

    public static void setBooleanPreference(Context context, String KEY,
                                            boolean value) {
        preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY, value);
        editor.commit();
    }

    public static boolean getBooleanPreference(Context context, String KEY) {
        preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        return preferences.getBoolean(KEY, false);
    }

    public static void clearAllPreferences(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);

        preferences.edit().clear().commit();

    }

    public static void setPermanentStringPreference(Context context, String KEY,
                                                    String value) {
        preferences = context.getSharedPreferences(PERMANENT_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY, "" + value);
        editor.commit();
    }

    public static String getPermanentStringPreference(Context context, String KEY) {
        preferences = context.getSharedPreferences(PERMANENT_PREF_NAME,
                Context.MODE_PRIVATE);
        return preferences.getString(KEY, "");
    }
}