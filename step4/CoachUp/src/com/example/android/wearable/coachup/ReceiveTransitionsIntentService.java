/**
 * 
 */
package com.example.android.wearable.coachup;

import java.util.List;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author Matias
 *
 */
public class ReceiveTransitionsIntentService extends IntentService {

    /**
     * Sets an identifier for this class' background thread
     */
    public ReceiveTransitionsIntentService() {
        super("ReceiveTransitionsIntentService");
    }

    /**
     * Handles incoming intents
     * @param intent The Intent sent by Location Services. This Intent is provided
     * to Location Services (inside a PendingIntent) when you call addGeofences()
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        // Create a local broadcast Intent
        Intent broadcastIntent = new Intent();

        // Give it the category for all intents sent by the Intent Service
        broadcastIntent.addCategory("com.example.android.wearable.coachup.CATEGORY_LOCATION_SERVICES");

        // First check for errors
        if (LocationClient.hasError(intent)) {

            // Get the error code
            int errorCode = LocationClient.getErrorCode(intent);

        // If there's no error, get the transition type and create a notification
        } else {

            // Get the type of transition (entry or exit)
            int transition = LocationClient.getGeofenceTransition(intent);

            // Test that a valid transition was reported
            if (
                    (transition == Geofence.GEOFENCE_TRANSITION_ENTER)
                    ||
                    (transition == Geofence.GEOFENCE_TRANSITION_EXIT)
               ) {

                // Post a notification
                List<Geofence> geofences = LocationClient.getTriggeringGeofences(intent);
                String[] geofenceIds = new String[geofences.size()];
                for (int index = 0; index < geofences.size() ; index++) {
                    geofenceIds[index] = geofences.get(index).getRequestId();
                }
                String ids = TextUtils.join(",",geofenceIds);
                
                if (transition == Geofence.GEOFENCE_TRANSITION_ENTER){
                	//sendNotification(transitionType, ids);
                }
            } else {
//                // Always log as an error
//                Log.e(GeofenceUtils.APPTAG,
//                        getString(R.string.geofence_transition_invalid_type, transition));
            }
        }
    }

    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the main Activity.
     * @param transitionType The type of transition that occurred.
     *
     */
    private void sendNotification(String transitionType, String ids) {

        // Create an explicit content Intent that starts the main Activity
        Intent notificationIntent =
                new Intent(getApplicationContext(),MainActivity.class);

        // Construct a task stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the main Activity to the task stack as the parent
        stackBuilder.addParentStack(MainActivity.class);

        // Push the content Intent onto the stack
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

//        // Set the notification contents
//        builder.setSmallIcon(R.drawable.ic_notification)
//               .setContentTitle(
//                       getString(R.string.geofence_transition_notification_title,
//                               transitionType, ids))
//               .setContentText(getString(R.string.geofence_transition_notification_text))
//               .setContentIntent(notificationPendingIntent);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(0, builder.build());
    }

}