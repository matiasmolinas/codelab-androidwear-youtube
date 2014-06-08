package com.example.android.wearable.coachup;

/**
* Constants.
*/
public final class Constants {
    private Constants() {
    }
    
    public static final String EXERCISE_NAME_TO_LOAD = "exercise_name";

    public static final String EXERCISE_FIELD_IMAGE = "img";
    public static final String EXERCISE_FIELD_VIDEO_ID = "video_id";
    public static final String EXERCISE_FIELD_LATITUDE = "latitude";
    public static final String EXERCISE_FIELD_LONGITUDE = "longitude";
    public static final String EXERCISE_FIELD_NAME = "name";
    public static final String EXERCISE_FIELD_SUMMARY = "summary";
    public static final String EXERCISE_FIELD_TEXT = "text";
    public static final String EXERCISE_FIELD_TITLE = "title";

    static final String ACTION_START_EXERCISE =
            "com.example.android.wearable.coachup.START_EXERCISE";
    public static final String EXTRA_EXERCISE = "exercise";
    public static final String EXTRA_EXERCISE_BMP = "exercise_bmp";

    public static final int NOTIFICATION_ID = 0;
    public static final int NOTIFICATION_IMAGE_WIDTH = 280;
    public static final int NOTIFICATION_IMAGE_HEIGHT = 280;
    
    //YouTube service related constants
    public static final String DEVELOPER_KEY = "AIzaSyAmtBaNWuzEYGw1Ocmf2PemhRwrHSwsHfE";
    public static final String PLAYLIST_ID = "PLF-2B3_5y4x_Jlp74b-UAaOfPdXqMDpRF";
    public static final String PLAYLIST_ITEMS_URL = 
    		"https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" 
    				+ PLAYLIST_ID + "&key=" + DEVELOPER_KEY;
    public static final String VIDEOS_URL = "https://www.googleapis.com/youtube/v3/videos?part=snippet%2CrecordingDetails&key="
    		+ DEVELOPER_KEY +"&id=";
    
    
}
