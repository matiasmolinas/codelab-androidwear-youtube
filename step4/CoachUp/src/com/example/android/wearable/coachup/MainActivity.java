
package com.example.android.wearable.coachup;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
* This is the main activity that displays a list of exercises
*/
public class MainActivity extends ListActivity implements android.location.LocationListener {

    private static final String TAG = "CoachUp";
    private ExerciseListAdapter mAdapter;
    
    // GPS location
    public LocationManager locationManager;
    public String provider; // location provider
    
    // Internal List of Geofence objects
    List<Geofence> mGeofenceList;
    
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
	    	// Getting LocationManager object from System Service LOCATION_SERVICE
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            if(location!=null){
                onLocationChanged(location);
            }
            
            // every 20secs request
            locationManager.requestLocationUpdates(provider, 20000, 0, this);
            
            // Instantiate the current List of geofences
            mGeofenceList = new ArrayList<Geofence>();

	    	mAdapter = new ExerciseListAdapter(this);
	    	setListAdapter(mAdapter);
	    }
    }
    
    public void setGeofenceList(List<Geofence> geofenceList){
    	mGeofenceList = geofenceList;
    	
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}
