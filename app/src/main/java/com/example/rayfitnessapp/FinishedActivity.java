package com.example.rayfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FinishedActivity extends AppCompatActivity {

    private TextView textViewTime2, textViewCalories2, textViewPoint, textViewStreak;
    private ProgressBar progressBarStreaks;
    private Button buttonFinished;
    private WorkoutDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);

        textViewTime2 = findViewById(R.id.textViewTime2);
        textViewCalories2 = findViewById(R.id.textViewCalories2);
        textViewPoint = findViewById(R.id.textViewPoint);
        textViewStreak = findViewById(R.id.textViewStreak);
        progressBarStreaks = findViewById(R.id.progressBarStreaks);
        buttonFinished = findViewById(R.id.buttonFinished);

        dbHelper = new WorkoutDatabaseHelper(this);

        // Get workout time from intent
        int workoutTime = getIntent().getIntExtra("WORKOUT_TIME", 0);
        textViewTime2.setText(workoutTime + " min");

        // Calculate calories burned (example formula)
        double caloriesBurned = workoutTime * 0.12; // Adjust based on exercise type
        textViewCalories2.setText(String.format(Locale.getDefault(), "%.1f", caloriesBurned));

        // Award points based on workout duration
        int points = calculatePoints(workoutTime);
        textViewPoint.setText(String.valueOf(points));

        // Save to database
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            String username = user.getDisplayName() != null ? user.getDisplayName() : "Unknown";
            dbHelper.saveCompletedWorkout(userEmail, username, workoutTime, caloriesBurned, points);
        }

        // Update and display workout streak
        updateWorkoutStreak();

        // Handle Done button click
        buttonFinished.setOnClickListener(v -> {
            startActivity(new Intent(FinishedActivity.this, MainActivity.class));
        });

    }
    private int calculatePoints(int workoutTime) {
        if (workoutTime >= 10) {
            return 3;
        } else if (workoutTime >= 5) {
            return 2;
        } else {
            return 1;
        }
    }
    private void updateWorkoutStreak(){
        SharedPreferences prefs = getSharedPreferences("FitnessPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String lastWorkoutDate = prefs.getString("LAST_WORKOUT_DATE", "");
        int currentStreak = prefs.getInt("WORKOUT_STREAK", 0);

        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        if (!todayDate.equals(lastWorkoutDate)) {
            currentStreak = lastWorkoutDate.equals("") ? 1 : currentStreak + 1;
            editor.putString("LAST_WORKOUT_DATE", todayDate);
            editor.putInt("WORKOUT_STREAK", currentStreak);
            editor.apply();
        }

        textViewStreak.setText(currentStreak + " Day Streak");
        progressBarStreaks.setProgress(currentStreak * 10);
    }

}