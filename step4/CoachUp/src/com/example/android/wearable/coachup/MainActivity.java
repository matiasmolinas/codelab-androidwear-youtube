
package com.example.android.wearable.coachup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
* This is the main activity that displays a list of exercises
*/
public class MainActivity extends ListActivity{
    private static final String TAG = "CoachUp";
    private ExerciseListAdapter mAdapter;
    
    

    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG , "onListItemClick " + position);
        }
        //We build an intent to start the activity that shows a exercise in the phone
        String itemName = mAdapter.getItemName(position);
        Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
        intent.putExtra(Constants.EXERCISE_NAME_TO_LOAD, itemName);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(android.R.layout.list_content);
        
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
        	int requestCode = 10;
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
	        dialog.show();
	    }else{
	    	mAdapter = new ExerciseListAdapter(this);
	    	setListAdapter(mAdapter);
	    }
    }

}
