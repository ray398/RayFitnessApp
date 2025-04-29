package com.example.rayfitnessapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerviewHome;
    private WorkoutTypeAdapter workoutTypeAdapter;
    private List<WorkoutType> workoutTypeList;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerviewHome = view.findViewById(R.id.recyclerviewHome);
        recyclerviewHome.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load workout types from the database in the background
        new LoadWorkoutTypesTask().execute();

        return view;
    }
    @SuppressLint("StaticFieldLeak")
    private class LoadWorkoutTypesTask extends AsyncTask<Void, Void, List<WorkoutType>> {

        @Override
        protected List<WorkoutType> doInBackground(Void... voids) {
            return loadWorkoutTypesFromDatabase();
        }

        @Override
        protected void onPostExecute(List<WorkoutType> workoutTypes) {
            workoutTypeList = workoutTypes;

            if (workoutTypeList != null && !workoutTypeList.isEmpty()) {
                workoutTypeAdapter = new WorkoutTypeAdapter(getContext(), workoutTypeList, workoutType -> {
                    Intent intent = new Intent(getContext(), WorkoutListActivity.class);
                    intent.putExtra("WORKOUT_TYPE_ID", workoutType.getId());
                    intent.putExtra("workoutName", workoutType.getName());
                    intent.putExtra("workoutCover", workoutType.getCoverPhoto());
                    startActivity(intent);
                });
                recyclerviewHome.setAdapter(workoutTypeAdapter);
            } else {
                Log.e("HomeFragment", "No workout types found in the database.");
            }
        }
    }

    @SuppressLint("Range")
    private List<WorkoutType> loadWorkoutTypesFromDatabase() {
        List<WorkoutType> workoutTypes = new ArrayList<>();
        WorkoutDatabaseHelper databaseHelper = new WorkoutDatabaseHelper(getContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query("workout_types", null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    workoutTypes.add(new WorkoutType(
                            cursor.getInt(cursor.getColumnIndex("id")),
                            cursor.getString(cursor.getColumnIndex("name")),
                            cursor.getString(cursor.getColumnIndex("duration")),
                            cursor.getString(cursor.getColumnIndex("cover_photo"))
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error reading from the database: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return workoutTypes;
    }
}
