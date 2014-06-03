package com.example.android.wearable.coachup;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;

//Android Wear Imports !!!!!!!!!!!!! ------------------------------------------
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.NotificationCompat;
// ----------------------------------------------------------------------------

import java.util.ArrayList;

import com.google.android.youtube.player.YouTubeIntents;

/**
* This class can retrieve the exercise data from the resources, build a notification and
* send it to the Android Wear Device
*/
public class ExerciseService extends Service {
    private NotificationManagerCompat mNotificationManager;
    private Binder mBinder = new LocalBinder();
    private Exercise mExercise;

    public class LocalBinder extends Binder {
        ExerciseService getService() {
            return ExerciseService.this;
        }
    }

    @Override
    public void onCreate() {
        mNotificationManager = NotificationManagerCompat.from(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION_START_EXERCISE)) {
            createNotification(intent);
            return START_STICKY;
        }
        return START_NOT_STICKY;
    }

    //This method creates a notification to show in the Android Wear Device
    private void createNotification(Intent intent) {
    	//We retrieve the exercise from the resources
        mExercise = Exercise.fromBundle(intent.getBundleExtra(Constants.EXTRA_EXERCISE));
        //We retrieve the resources of the exercise main card
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        if (mExercise.exerciseImage != null) {
            Bitmap exerciseImage = Bitmap.createScaledBitmap(
                    AssetUtils.loadBitmapAsset(this, mExercise.exerciseImage),
                    Constants.NOTIFICATION_IMAGE_WIDTH, Constants.NOTIFICATION_IMAGE_HEIGHT, false);
            builder.setLargeIcon(exerciseImage);
        }
        builder.setContentTitle(mExercise.titleText);
        builder.setContentText(mExercise.summaryText);
        builder.setSmallIcon(R.mipmap.ic_notification_recipe);
        
        String videoId = mExercise.videoId;
        Intent videoIntent = YouTubeIntents.createPlayVideoIntentWithOptions(this, videoId, true, false);
        PendingIntent videoPendingIntent =
                PendingIntent.getActivity(this, 0, videoIntent, 0);
        
        WearableNotifications.Action action = new WearableNotifications.Action.Builder(
        		R.drawable.youtube,
        		getString(R.string.video_youtube),videoPendingIntent)
                .build();

        //We build the Notification including the recipe and all its pages
        Notification notification = new WearableNotifications.Builder(builder)
        		.addAction( action )
                .build();
        mNotificationManager.notify(Constants.NOTIFICATION_ID, notification);
    }
}
