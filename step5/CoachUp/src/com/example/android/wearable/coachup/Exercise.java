package com.example.android.wearable.coachup;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
* Object model class to represent a exercise
*/
public class Exercise {
    private static final String TAG = "CoachUp";

    public String titleText;
    public String summaryText;
    public String exerciseImage;
    public String videoId;
    public double latitude;
    public double longitude;

    public Exercise() {
    }

    public static Exercise fromJson(Context context, JSONObject json) {
    	Exercise exercise = new Exercise();
        try {
        	exercise.titleText = json.getString(Constants.EXERCISE_FIELD_TITLE);
        	exercise.summaryText = json.getString(Constants.EXERCISE_FIELD_SUMMARY);
            if (json.has(Constants.EXERCISE_FIELD_IMAGE)) {
            	exercise.exerciseImage = json.getString(Constants.EXERCISE_FIELD_IMAGE);
            }
            exercise.videoId = json.getString(Constants.EXERCISE_FIELD_VIDEO_ID);
            exercise.latitude = json.getDouble(Constants.EXERCISE_FIELD_LATITUDE);
            exercise.longitude = json.getDouble(Constants.EXERCISE_FIELD_LONGITUDE);
        } catch (JSONException e) {
            Log.e(TAG, "Error loading exercise: " + e);
            return null;
        }
        return exercise;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXERCISE_FIELD_TITLE, titleText);
        bundle.putString(Constants.EXERCISE_FIELD_SUMMARY, summaryText);
        bundle.putString(Constants.EXERCISE_FIELD_IMAGE, exerciseImage);
        bundle.putString(Constants.EXERCISE_FIELD_VIDEO_ID, videoId);
        bundle.putDouble(Constants.EXERCISE_FIELD_LATITUDE, latitude);
        bundle.putDouble(Constants.EXERCISE_FIELD_LONGITUDE, longitude);
        
        return bundle;
    }

    public static Exercise fromBundle(Bundle bundle) {
    	Exercise exercise = new Exercise();
    	exercise.titleText = bundle.getString(Constants.EXERCISE_FIELD_TITLE);
    	exercise.summaryText = bundle.getString(Constants.EXERCISE_FIELD_SUMMARY);
    	exercise.exerciseImage = bundle.getString(Constants.EXERCISE_FIELD_IMAGE);
    	exercise.videoId = bundle.getString(Constants.EXERCISE_FIELD_VIDEO_ID);
    	exercise.latitude = bundle.getDouble(Constants.EXERCISE_FIELD_LATITUDE);
    	exercise.longitude = bundle.getDouble(Constants.EXERCISE_FIELD_LONGITUDE);
        
        return exercise;
    }
}
