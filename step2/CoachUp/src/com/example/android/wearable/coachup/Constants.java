package com.example.android.wearable.coachup;

/**
* Constants.
*/
public final class Constants {
    private Constants() {
    }
    public static final String EXERCISE_LIST_FILE = "exerciselist.json";
    public static final String EXERCISE_NAME_TO_LOAD = "exercise_name";

    public static final String EXERCISE_FIELD_LIST = "exercise_list";
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

    public static final int NOTIFICATION_ID = 0;
    public static final int NOTIFICATION_IMAGE_WIDTH = 280;
    public static final int NOTIFICATION_IMAGE_HEIGHT = 280;
    
    
}
