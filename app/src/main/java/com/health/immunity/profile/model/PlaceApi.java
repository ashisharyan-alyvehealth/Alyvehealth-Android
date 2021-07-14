package com.health.immunity.profile.model;


import android.util.Log;

import com.health.immunity.IConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PlaceApi {

    public ArrayList<String> autoComplete(String input) {
        ArrayList<String> arrayList = new ArrayList<>();
        HttpURLConnection urlConnection = null;
        StringBuilder jsonResult = new StringBuilder();
        try {
            StringBuilder builder = new StringBuilder("https://maps.googleapis.com/maps/api/place/autocomplete/json?");
            builder.append("input=" + input);
            builder.append("&key=" + IConstant.API_KEY);
            Log.e(TAG, "autoComplete: " + builder.toString());
            URL url = new URL(builder.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            int read;
            char[] buff = new char[1024];
            while ((read = inputStreamReader.read(buff)) != -1) {
                jsonResult.append(buff, 0, read);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        try {
            JSONObject jsonObject = new JSONObject(jsonResult.toString());
            JSONArray prediction = jsonObject.getJSONArray("predictions");
            for (int i = 0; i < prediction.length(); i++) {
                arrayList.add(prediction.getJSONObject(i).getString("description"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}

