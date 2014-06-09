package com.example.android.wearable.coachup;

import android.text.TextUtils;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
* This class provides helper methods to load resources.
*/
final class AssetUtils {
    private static final String TAG = "CoachUp";

    private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;
	}

	public static JSONObject callService(String url) {
		InputStream inputStream = null;
		JSONObject result = null;
		try {
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			// convert inputstream to string
			if (inputStream != null){
				String response = convertInputStreamToString(inputStream);
				result = new JSONObject(response);
			}
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		return result;
	}
    
    public static String getVideoIdsFromPlaylistItemsJson(JSONObject playlistItems){
		String ids = "";
		List<String> videoIds = new ArrayList<String>();
		try {
			JSONArray items = playlistItems.getJSONArray("items"); // get playlistitems array
			for (int i =0;i<items.length(); i++){
				JSONObject item = items.getJSONObject(i);
				JSONObject snippet = item.getJSONObject("snippet");
				JSONObject resourceId = snippet.getJSONObject("resourceId");
				String videoId = resourceId.getString("videoId");
				videoIds.add(videoId);
			}
			ids = TextUtils.join(",", videoIds);
		} catch (JSONException e) {
			Log.d("getVideoIdsFromPlaylistItemsJson", e.getLocalizedMessage());
		}
		return ids;
	}
	
    public static List<Exercise> getExerciseListFromVideosJson(JSONObject videos){
		List<Exercise> exerciseList = new ArrayList<Exercise>();
		
		try {
			JSONArray items = videos.getJSONArray("items"); // get videos array
			for (int i =0;i<items.length(); i++){
				JSONObject videoJson = items.getJSONObject(i);
				Exercise exercise = Exercise.fromJson(videoJson);
				exerciseList.add(exercise);
			}
		} catch (JSONException e) {
			Log.d("getExerciseListFromVideosJson", e.getLocalizedMessage());
		}
		return exerciseList;
	}
}
