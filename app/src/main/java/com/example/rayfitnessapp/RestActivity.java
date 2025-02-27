package com.example.rayfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

public class RestActivity extends AppCompatActivity {

    private TextView textViewRestTime;
    private CountDownTimer countDownTimer;
    private Button buttonSkipRest;

    private int nextExerciseIndex;
    private int workoutTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        textViewRestTime = findViewById(R.id.textViewRestTime);
        buttonSkipRest = findViewById(R.id.buttonSkipRA); // Add this button in XML

        // Retrieve data from intent
        nextExerciseIndex = getIntent().getIntExtra("nextExerciseIndex", 0);
        workoutTypeId = getIntent().getIntExtra("workoutTypeId", 1); // Default to 1

        // Start 30-second countdown timer
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewRestTime.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                goToNextExercise();
            }
        }.start();

        // Skip rest and go to the next exercise immediately
        buttonSkipRest.setOnClickListener(view -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            goToNextExercise();
        });
    }

    private void goToNextExercise() {
        Intent intent = new Intent(RestActivity.this, TrainingActivity.class);
        intent.putExtra("nextExerciseIndex", nextExerciseIndex);
        intent.putExtra("workoutTypeId", workoutTypeId);
        startActivity(intent);
        finish(); // Finish RestActivity so it doesn't stack up
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
