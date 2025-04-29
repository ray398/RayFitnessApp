package com.example.rayfitnessapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

public class WorkoutDatabaseHelper extends SQLiteOpenHelper {

    //...Database information
    private static final String DATABASE_NAME = "fitness_app_db";
    private static final int DATABASE_VERSION = 1;

    //...Workout type Table
    public static final String TABLE_WORKOUT_TYPES = "workout_types";
    public static final String COLUMN_WORKOUT_TYPE_ID = "id";
    public static final String COLUMN_WORKOUT_TYPE_NAME = "name";
    public static final String COLUMN_WORKOUT_TYPE_DESCRIPTION = "description";
    public static final String COLUMN_WORKOUT_TYPE_DURATION = "duration";
    public static final String COLUMN_WORKOUT_TYPE_COVER_PHOTO = "cover_photo";

    //...Exercise Table
    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_EXERCISE_ID = "_id";
    public static final String COLUMN_EXERCISE_WORKOUT_ID = "workout_id";
    public static final String COLUMN_EXERCISE_NAME = "name";
    public static final String COLUMN_EXERCISE_DESCRIPTION = "description";
    public static final String COLUMN_EXERCISE_DURATION_OR_SETS = "duration_or_sets";
    public static final String COLUMN_EXERCISE_GIF_PATH = "gif_path";

    // New Completed Workouts Table
    public static final String TABLE_COMPLETED_WORKOUTS = "completed_workouts";
    public static final String COLUMN_COMPLETED_ID = "id";
    public static final String COLUMN_USER_EMAIL = "user_email"; // To associate with user
    public static final String COLUMN_TIME_SPENT = "time_spent"; // in minutes
    public static final String COLUMN_CALORIES_BURNED = "calories_burned";
    public static final String COLUMN_POINTS = "points";
    public static final String COLUMN_USERNAME = "username";


    // SQL to Create Workout Types Table
    private static final String CREATE_TABLE_WORKOUT_TYPES =
            "CREATE TABLE " + TABLE_WORKOUT_TYPES + " (" +
             COLUMN_WORKOUT_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
             COLUMN_WORKOUT_TYPE_NAME + " TEXT NOT NULL, " +
             COLUMN_WORKOUT_TYPE_DESCRIPTION + " TEXT, " +
             COLUMN_WORKOUT_TYPE_DURATION + " INTEGER, " +
             COLUMN_WORKOUT_TYPE_COVER_PHOTO + " TEXT);";
    //..................
    //SQL for Exercises table
    private static final String CREATE_TABLE_EXERCISES =
            "CREATE TABLE " + TABLE_EXERCISES + " (" +
                    COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_EXERCISE_WORKOUT_ID + " INTEGER NOT NULL, " +
                    COLUMN_EXERCISE_NAME + " TEXT NOT NULL, " +
                    COLUMN_EXERCISE_DESCRIPTION + " TEXT, " +
                    COLUMN_EXERCISE_DURATION_OR_SETS + " TEXT, " +
                    COLUMN_EXERCISE_GIF_PATH + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_EXERCISE_WORKOUT_ID + ") REFERENCES " +
                    TABLE_WORKOUT_TYPES + "(" + COLUMN_WORKOUT_TYPE_ID+ "));";
    //...................

    private static final String CREATE_TABLE_COMPLETED_WORKOUTS =
            "CREATE TABLE " + TABLE_COMPLETED_WORKOUTS + " (" +
                    COLUMN_COMPLETED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_TIME_SPENT + " INTEGER, " +
                    COLUMN_CALORIES_BURNED + " REAL, " +
                    COLUMN_POINTS + " INTEGER, " +
                    COLUMN_USERNAME + " TEXT NOT NULL);";

    public WorkoutDatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @SuppressLint("Range")
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_WORKOUT_TYPES);
        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_COMPLETED_WORKOUTS);

        addPredefinedWorkoutTypes(db);
        addPredefinedExercises(db);

        // Check if the exercises table has data
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXERCISES, null);
        if (cursor.getCount() == 0) {
            Log.d("Database", "No data in the exercises table");
        } else {
            Log.d("Database", "Data exists in the exercises table: " + cursor.getCount() + " rows");
            while (cursor.moveToNext()) {
                Log.d("Database", "Exercise: " + cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE_NAME)));
            }
        }
        cursor.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Upgrade from version 1 to 2: Add username column to existing table
            db.execSQL("ALTER TABLE " + TABLE_COMPLETED_WORKOUTS + " ADD COLUMN " + COLUMN_USERNAME + " TEXT NOT NULL DEFAULT 'Unknown'");
        }
        if (newVersion > 2) {
            // For future upgrades beyond version 2: Drop and recreate all tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT_TYPES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLETED_WORKOUTS);
            onCreate(db);
        }
    }
    //...............Method to prepopulate the workout types
    private void addPredefinedWorkoutTypes(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_TYPE_NAME,"Push day");
        values.put(COLUMN_WORKOUT_TYPE_DESCRIPTION, "It targets the chest, shoulders and triceps");
        values.put(COLUMN_WORKOUT_TYPE_DURATION,"1800");
        values.put(COLUMN_WORKOUT_TYPE_COVER_PHOTO, "overhead3");
        db.insert(TABLE_WORKOUT_TYPES, null, values);
        //....

        values.put(COLUMN_WORKOUT_TYPE_NAME, "Pull Day");
        values.put(COLUMN_WORKOUT_TYPE_DESCRIPTION, "Targets back and biceps");
        values.put(COLUMN_WORKOUT_TYPE_DURATION, 3600); // 1 hour
        values.put(COLUMN_WORKOUT_TYPE_COVER_PHOTO, "overhead3");
        db.insert(TABLE_WORKOUT_TYPES, null, values);
        //....
        values.put(COLUMN_WORKOUT_TYPE_NAME, "Presentation ");
        values.put(COLUMN_WORKOUT_TYPE_DESCRIPTION, "Targets back and biceps");
        values.put(COLUMN_WORKOUT_TYPE_DURATION, 3600); // 1 hour
        values.put(COLUMN_WORKOUT_TYPE_COVER_PHOTO, "inclined3");
        db.insert(TABLE_WORKOUT_TYPES, null, values);

    }

    private void addPredefinedExercises(SQLiteDatabase db){
        addExercises(db,1,"Bench press","Targets chest muscles", "3 sets", "gif_bench");
        addExercises(db,1,"Incline Dumbbell Press", "Upper chest focus", "3 sets", "gif_inclined_db");
        addExercises(db, 2, "Pull-ups", "Great for back and biceps", "4 sets", "gif_pullup_gif");
        addExercises(db, 2, "Barbell Rows", "Back strength and thickness", "3 sets", "gif_bent_row");
        addExercises(db, 3, "Barbell Rows", "Back strength and thickness", "3 sets", "gif_bent_row");
        addExercises(db, 3, "Lateral raises", "For building shoulders", "3 sets", "gif_lateral");



    }
    //.................Add Exercises with GIFs
    private void addExercises(SQLiteDatabase db,int workoutTypeID, String name, String description, String durationOrSets, String gifPath){
        ContentValues values = new ContentValues();

        values.put(COLUMN_EXERCISE_WORKOUT_ID, workoutTypeID);
        values.put(COLUMN_EXERCISE_NAME, name);
        values.put(COLUMN_EXERCISE_DESCRIPTION, description);
        values.put(COLUMN_EXERCISE_DURATION_OR_SETS, durationOrSets);
        values.put(COLUMN_EXERCISE_GIF_PATH, gifPath);

        db.insert(TABLE_EXERCISES,null,values);
    }

    //................Retrieve Exercises for a Specific Workout Type

    public List<Exercise> getExercisesForWorkoutType(int workoutTypeId) {

        List<Exercise> exercises = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXERCISES, null,
                COLUMN_EXERCISE_WORKOUT_ID + "=?",
                new String[]{String.valueOf(workoutTypeId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Exercise exercise = new Exercise(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXERCISE_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISE_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISE_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISE_DURATION_OR_SETS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXERCISE_GIF_PATH))
                );
                exercises.add(exercise);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return exercises;
    }

    // Save completed workout
    public void saveCompletedWorkout(String userEmail, String username, int timeSpent, double caloriesBurned, int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, userEmail);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_TIME_SPENT, timeSpent);
        values.put(COLUMN_CALORIES_BURNED, caloriesBurned);
        values.put(COLUMN_POINTS, points);
        db.insert(TABLE_COMPLETED_WORKOUTS, null, values);
        db.close();
    }

    // Get total stats for a user
    public int getTotalTime(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_TIME_SPENT + ") as total_time FROM " +
                TABLE_COMPLETED_WORKOUTS + " WHERE " + COLUMN_USER_EMAIL + " = ?", new String[]{userEmail});
        int totalTime = 0;
        if (cursor.moveToFirst()) {
            totalTime = cursor.getInt(cursor.getColumnIndexOrThrow("total_time"));
        }
        cursor.close();
        return totalTime;
    }

    public double getTotalCalories(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_CALORIES_BURNED + ") as total_calories FROM " +
                TABLE_COMPLETED_WORKOUTS + " WHERE " + COLUMN_USER_EMAIL + " = ?", new String[]{userEmail});
        double totalCalories = 0;
        if (cursor.moveToFirst()) {
            totalCalories = cursor.getDouble(cursor.getColumnIndexOrThrow("total_calories"));
        }
        cursor.close();
        return totalCalories;
    }

    public int getTotalPoints(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_POINTS + ") as total_points FROM " +
                TABLE_COMPLETED_WORKOUTS + " WHERE " + COLUMN_USER_EMAIL + " = ?", new String[]{userEmail});
        int totalPoints = 0;
        if (cursor.moveToFirst()) {
            totalPoints = cursor.getInt(cursor.getColumnIndexOrThrow("total_points"));
        }
        cursor.close();
        return totalPoints;
    }
    // New method to get leaderboard data
    public List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> leaderboard = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_USERNAME + ", SUM(" + COLUMN_POINTS + ") as total_points " +
                "FROM " + TABLE_COMPLETED_WORKOUTS + " GROUP BY " + COLUMN_USER_EMAIL + ", " + COLUMN_USERNAME +
                " ORDER BY total_points DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                int totalPoints = cursor.getInt(cursor.getColumnIndexOrThrow("total_points"));
                leaderboard.add(new LeaderboardEntry(username, totalPoints));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return leaderboard;
    }
    // Method to fix incorrect GIF paths in the database
    /*
    public void fixIncorrectGifPaths() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the incorrect value and the corrected value
        String oldValue = "overhead3.png";
        String newValue = "overhead3";

        // Update the row where the incorrect value exists
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_TYPE_COVER_PHOTO, newValue);

        int rowsAffected = db.update(
                TABLE_WORKOUT_TYPES,
                values,
                COLUMN_WORKOUT_TYPE_COVER_PHOTO + "=?",
                new String[]{oldValue}
        );

        // Log the result
        Log.d("DatabaseFix", "Rows updated: " + rowsAffected);
    } */

}
