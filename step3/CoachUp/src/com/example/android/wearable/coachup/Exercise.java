package com.example.android.wearable.coachup;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
    public Bitmap bmp;

    public Exercise() {
    }

    public static Exercise fromJson(JSONObject videoJson) {
    	Exercise exercise = new Exercise();
        try {
        	exercise.videoId = videoJson.getString("id");
        	JSONObject snippet = videoJson.getJSONObject("snippet");
        	exercise.titleText = snippet.getString("title");
        	exercise.summaryText = snippet.getString("description");
        	JSONObject thumbnails = snippet.getJSONObject("thumbnails");
        	JSONObject standard = thumbnails.getJSONObject("standard");
        	exercise.exerciseImage = standard.getString("url");
        	
        	JSONObject recordingDetails = videoJson.getJSONObject("recordingDetails");
        	JSONObject location = recordingDetails.getJSONObject("location");
        	exercise.latitude = location.getDouble("latitude");
        	exercise.longitude = location.getDouble("latitude");
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
