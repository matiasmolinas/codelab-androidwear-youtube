package com.example.android.wearable.coachup;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

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
 * ListAdapter to retrieve the list of exercises from the resources and to show
 * it in the phone
 */
public class ExerciseListAdapter implements ListAdapter {
	private String TAG = "ExerciseListAdapter";

	private class Item {
		String title;
		String name;
		String summary;
		Bitmap image;
	}

	private List<Item> mItems = new ArrayList<Item>();
	private Context mContext;
	private DataSetObserver mObserver;

	public ExerciseListAdapter(Context context) {
		mContext = context;
		//loadExerciseList();
		
		// call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute(Constants.PLAYLIST_ITEMS_URL,Constants.VIDEOS_URL);
	}

	private void loadExerciseList() {
		JSONObject jsonObject = AssetUtils.loadJSONAsset(mContext,
				Constants.EXERCISE_LIST_FILE);
		if (jsonObject != null) {
			List<Item> items = parseJson(jsonObject);
			appendItemsToList(items);
		}
	}

	public void loadExercises(JSONObject jsonObject) {
		if (jsonObject != null) {
			List<Item> items = parseJson(jsonObject);
			appendItemsToList(items);
		}
	}

	private List<Item> parseJson(JSONObject json) {
		List<Item> result = new ArrayList<Item>();
		try {
			JSONArray items = json.getJSONArray(Constants.EXERCISE_FIELD_LIST);
			for (int i = 0; i < items.length(); i++) {
				JSONObject item = items.getJSONObject(i);
				Item parsed = new Item();
				parsed.name = item.getString(Constants.EXERCISE_FIELD_NAME);
				parsed.title = item.getString(Constants.EXERCISE_FIELD_TITLE);
				if (item.has(Constants.EXERCISE_FIELD_IMAGE)) {
					String imageFile = item
							.getString(Constants.EXERCISE_FIELD_IMAGE);
					parsed.image = AssetUtils.loadBitmapAsset(mContext,
							imageFile);
				}
				parsed.summary = item
						.getString(Constants.EXERCISE_FIELD_SUMMARY);
				result.add(parsed);
			}
		} catch (JSONException e) {
			Log.e(TAG, "Failed to parse exercise list: " + e);
		}
		return result;
	}

	private void appendItemsToList(List<Item> items) {
		mItems.addAll(items);
		if (mObserver != null) {
			mObserver.onChanged();
		}
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inf = LayoutInflater.from(mContext);
			view = inf.inflate(R.layout.list_item, null);
		}
		Item item = (Item) getItem(position);
		TextView titleView = (TextView) view.findViewById(R.id.textTitle);
		TextView summaryView = (TextView) view.findViewById(R.id.textSummary);
		ImageView iv = (ImageView) view.findViewById(R.id.imageView);

		titleView.setText(item.title);
		summaryView.setText(item.summary);
		if (item.image != null) {
			iv.setImageBitmap(item.image);
		} else {
			iv.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.ic_noimage));
		}
		return view;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return mItems.isEmpty();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		mObserver = observer;
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		mObserver = null;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	public String getItemName(int position) {
		return mItems.get(position).name;
	}

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

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) mContext
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}
	
	private String getVideoIdsFromPlaylistItemsJson(JSONObject playlistItems){
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

	private class HttpAsyncTask extends AsyncTask<String, Void, JSONObject> {
		@Override
		protected JSONObject doInBackground(String... urls) {
			JSONObject playlistItems = callService(urls[0]);
			//Get video ids from playlistitems json
			String videoIds = getVideoIdsFromPlaylistItemsJson(playlistItems);
			//Retrieve videos json from YouTube service
			JSONObject videos = callService(urls[1] + videoIds);
			return videos;
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(JSONObject videosJson) {
			ExerciseListAdapter.this.loadExercises(videosJson);
		}
	}
}
