package com.example.android.wearable.coachup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

/**
* This activity retrieves a exercise from the resources, shows it in the phone, 
* and calls the Exercise Service to send a notification to the Android Wear device 
* in order to show the exercise in this device as a coach assistant  
*/
public class ExerciseActivity extends Activity {
    private static final String TAG = "CoachUp";
    private String mExerciseName;
    private Exercise mExercise;
    private ImageView mImageView;
    private TextView mTitleTextView;
    private TextView mSummaryTextView;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        mExerciseName = intent.getStringExtra(Constants.EXERCISE_NAME_TO_LOAD);
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Intent: " + intent.toString() + " " + mExerciseName);
        }
        loadExercise();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);
        mTitleTextView = (TextView) findViewById(R.id.exerciseTextTitle);
        mSummaryTextView = (TextView) findViewById(R.id.exerciseTextSummary);
        mImageView = (ImageView) findViewById(R.id.exerciseImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_exercise:
                startExercise();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadExercise() {
        JSONObject jsonObject = AssetUtils.loadJSONAsset(this, mExerciseName);
        if (jsonObject != null) {
            mExercise = Exercise.fromJson(this, jsonObject);
            if (mExercise != null) {
                displayExercise(mExercise);
            }
        }
    }

    private void displayExercise(Exercise exercise) {
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        mTitleTextView.setAnimation(fadeIn);
        mTitleTextView.setText(exercise.titleText);
        mSummaryTextView.setText(exercise.summaryText);
        if (exercise.exerciseImage != null) {
            mImageView.setAnimation(fadeIn);
            Bitmap exerciseImage = AssetUtils.loadBitmapAsset(this, exercise.exerciseImage);
            mImageView.setImageBitmap(exerciseImage);
        }
    }

    private void startExercise() {
        Intent intent = new Intent(this, ExerciseService.class);
        intent.setAction(Constants.ACTION_START_EXERCISE);
        intent.putExtra(Constants.EXTRA_EXERCISE, mExercise.toBundle());
        startService(intent);
    }
}
