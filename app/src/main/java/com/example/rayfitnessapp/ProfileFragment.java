package com.example.rayfitnessapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {


    private TextView textViewUserNamePF, textViewPointsPA, textViewTimePA2, textViewCaloriesPA2,textViewEmail;
    private WorkoutDatabaseHelper dbHelper;
    public ProfileFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewUserNamePF = view.findViewById(R.id.textViewUNamePF);
        textViewPointsPA = view.findViewById(R.id.textViewPointsPA);
        textViewTimePA2 = view.findViewById(R.id.textViewTimePA2);
        textViewCaloriesPA2 = view.findViewById(R.id.textViewCaloriesPA2);
        textViewEmail = view.findViewById(R.id.textViewEmail);

        // Initialize database helper
        dbHelper = new WorkoutDatabaseHelper(getContext());

        // Load and display profile data
        loadProfileData();

        return view;
    }
    private void loadProfileData() {
        // Get current user from Firebase Authentication
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Fetch and display username and email from Firebase
            String username = user.getDisplayName();
            String email = user.getEmail();
            textViewUserNamePF.setText(username != null ? username : "User");
            textViewEmail.setText(email != null ? email : "No email");

            // Fetch and display totals from SQLite database
            int totalTime = dbHelper.getTotalTime(email);
            double totalCalories = dbHelper.getTotalCalories(email);
            int totalPoints = dbHelper.getTotalPoints(email);

            textViewTimePA2.setText(String.valueOf(totalTime)); // Total time in minutes
            textViewCaloriesPA2.setText(String.format("%.1f", totalCalories)); // Total calories with 1 decimal
            textViewPointsPA.setText(String.valueOf(totalPoints)); // Total points
        } else {
            // Handle case where user is not logged in
            textViewUserNamePF.setText("Not logged in");
            textViewEmail.setText("N/A");
            textViewTimePA2.setText("0");
            textViewCaloriesPA2.setText("0.0");
            textViewPointsPA.setText("0");
        }
    }
}