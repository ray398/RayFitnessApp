package com.example.rayfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    private ImageView imageViewBackWA, imageViewGIFTA, imageViewPlay;
    private TextView textViewExerciseType, textViewSets, textViewWKTime;
    private ProgressBar progressBarTraining;
    private Button buttonDone, buttonBackExercise, buttonSkipExercise;

    private List<Exercise> exercises;
    private int currentExerciseIndex = 0;
    private int workoutTypeId;
    private long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        // Initialize UI elements
        imageViewBackWA = findViewById(R.id.imageViewBackWA);
        imageViewGIFTA = findViewById(R.id.imageViewGIFTA);
        imageViewPlay = findViewById(R.id.imageViewPlay);
        textViewExerciseType = findViewById(R.id.textViewExerciseType);
        textViewSets = findViewById(R.id.textViewSets);
        textViewWKTime = findViewById(R.id.textViewWkTime);
        progressBarTraining = findViewById(R.id.progressBarTraining);
        buttonDone = findViewById(R.id.buttonDone);
        buttonBackExercise = findViewById(R.id.buttonBackExercise);
        buttonSkipExercise = findViewById(R.id.buttonBackSkipEx);

        startTime = System.currentTimeMillis();
        // Retrieve workoutTypeId from intent (dynamic workout selection)
        workoutTypeId = getIntent().getIntExtra("workoutTypeId", 1); // Default to 1 if not passed

        // Retrieve exercise index from RestActivity (if returning)
        if (getIntent().hasExtra("nextExerciseIndex")) {
            currentExerciseIndex = getIntent().getIntExtra("nextExerciseIndex", 0);
        }

        // Load exercises from database
        exercises = getExercisesForWorkout(workoutTypeId);
        if (!exercises.isEmpty()) {
            loadExercise(currentExerciseIndex);
        }

        // Navigate to RestActivity and move to the next exercise
        buttonDone.setOnClickListener(view -> {
            if (currentExerciseIndex < exercises.size() - 1) {
                Intent intent = new Intent(TrainingActivity.this, RestActivity.class);
                intent.putExtra("nextExerciseIndex", currentExerciseIndex + 1);
                intent.putExtra("workoutTypeId", workoutTypeId); // Preserve workout type
                startActivity(intent);
            } else {
                long endTime = System.currentTimeMillis();
                int workoutTime = (int) ((endTime - startTime) / 60000);
                Intent intent = new Intent(TrainingActivity.this, FinishedActivity.class);
                intent.putExtra("WORKOUT_TIME", workoutTime);
                startActivity(intent);
                finish(); // End workout session
            }
        });

        // Navigate to the previous exercise
        buttonBackExercise.setOnClickListener(view -> {
            if (currentExerciseIndex > 0) {
                currentExerciseIndex--;
                loadExercise(currentExerciseIndex);
            }
        });

        // Skip current exercise and move to the next one
        buttonSkipExercise.setOnClickListener(view -> {
            if (currentExerciseIndex < exercises.size() - 1) {
                currentExerciseIndex++;
                loadExercise(currentExerciseIndex);
            }
        });

    }

    private void loadExercise(int index) {
        Exercise exercise = exercises.get(index);
        textViewExerciseType.setText(exercise.getName());
        textViewSets.setText(exercise.getDurationOrSets());

        String gifName = exercise.getGifPath();
        int drawableGif = this.getResources().getIdentifier(gifName,"drawable",this.getPackageName());

        Glide.with(this)
                .load(drawableGif != 0 ? drawableGif : R.drawable.gif_curls)
                .placeholder(R.drawable.gif_curls)
                .into(imageViewGIFTA);
    }

    private List<Exercise> getExercisesForWorkout(int workoutType) {
        WorkoutDatabaseHelper dbHelper = new WorkoutDatabaseHelper(this);
        return dbHelper.getExercisesForWorkoutType(workoutType);
    }
}
