package com.example.rayfitnessapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WorkoutListActivity extends AppCompatActivity {

    ImageView imageViewCoverWactivity, imageViewBack;
    TextView textViewExerciseName2, textViewDuration;
    RecyclerView recyclerViewExercises;
    ExercisesAdapter exercisesAdapter;
    Button buttonStartEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        // Initialize UI components
        imageViewCoverWactivity = findViewById(R.id.imageViewCoverWactivity);
        textViewExerciseName2 = findViewById(R.id.textViewExerciseName2);
        textViewDuration = findViewById(R.id.textViewNoOfEx);
        imageViewBack = findViewById(R.id.imageViewBack);
        recyclerViewExercises = findViewById(R.id.recyclerViewExercises);
        buttonStartEx = findViewById(R.id.buttonStart);

        // Retrieve data from intent
        Intent intent = getIntent();
        String workoutName = intent.getStringExtra("workoutName");
        String duration = intent.getStringExtra("workoutDuration");
        String workoutCoverName = intent.getStringExtra("workoutCover");
        int workoutTypeId = intent.getIntExtra("WORKOUT_TYPE_ID", -1);

        // Update UI with workout details
        textViewExerciseName2.setText(workoutName);
        textViewDuration.setText(duration);
        int resourceId = getResources().getIdentifier(workoutCoverName, "drawable", getPackageName());
        if (resourceId != 0) {
            Glide.with(this).load(resourceId).into(imageViewCoverWactivity);
        } else {
            Toast.makeText(this, "Invalid resource name: " + workoutCoverName, Toast.LENGTH_SHORT).show();
        }

        // Setup RecyclerView
        exercisesAdapter = new ExercisesAdapter(this, null);
        recyclerViewExercises.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExercises.setAdapter(exercisesAdapter);

        // Fetch exercises from the database
        new FetchExercisesTask().execute(workoutTypeId);

        // Back button logic
        imageViewBack.setOnClickListener(v -> {
            startActivity(new Intent(WorkoutListActivity.this, MainActivity.class));
        });
        // Start button logic
        buttonStartEx.setOnClickListener(v -> {
            startActivity(new Intent(WorkoutListActivity.this, GetReadyActivity.class));
        });
    }

    private class FetchExercisesTask extends AsyncTask<Integer, Void, List<Exercise>> {

        @Override
        protected List<Exercise> doInBackground(Integer... params) {
            int workoutTypeId = params[0];
            WorkoutDatabaseHelper dbHelper = new WorkoutDatabaseHelper(WorkoutListActivity.this);
            return dbHelper.getExercisesForWorkoutType(workoutTypeId);
        }

        @Override
        protected void onPostExecute(List<Exercise> exercises) {
            if (exercises != null && !exercises.isEmpty()) {
                exercisesAdapter.updateData(exercises);
            } else {
                Toast.makeText(WorkoutListActivity.this, "No exercises found for this workout.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
