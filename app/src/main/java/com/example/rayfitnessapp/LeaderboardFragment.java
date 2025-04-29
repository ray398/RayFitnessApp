package com.example.rayfitnessapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class LeaderboardFragment extends Fragment {
    private TextView textViewPosition, textViewUserNameLB, textViewPointsLBA;
    private RecyclerView recyclerViewLeaderBoard;
    private LeaderboardAdapter adapter;
    private WorkoutDatabaseHelper dbHelper;
    public LeaderboardFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        textViewPosition = view.findViewById(R.id.textViewPosition);
        textViewUserNameLB = view.findViewById(R.id.textViewUserNameLB);
        textViewPointsLBA = view.findViewById(R.id.textViewPointsLBA);
        recyclerViewLeaderBoard = view.findViewById(R.id.recyclerViewLeaderBoard);

        dbHelper = new WorkoutDatabaseHelper(getContext());

        recyclerViewLeaderBoard.setLayoutManager(new LinearLayoutManager(getContext()));
        loadLeaderboard();

        return view;
    }
    private void loadLeaderboard() {
        List<LeaderboardEntry> leaderboard = dbHelper.getLeaderboard();
        adapter = new LeaderboardAdapter(leaderboard);
        recyclerViewLeaderBoard.setAdapter(adapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String currentUsername = currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "Unknown";
            for (int i = 0; i < leaderboard.size(); i++) {
                if (leaderboard.get(i).getUsername().equals(currentUsername)) {
                    textViewPosition.setText(String.valueOf(i + 1)); // Rank (1-based)
                    textViewUserNameLB.setText(currentUsername);
                    textViewPointsLBA.setText(leaderboard.get(i).getPoints() + " Points");
                    break;
                }
            }
        }
    }
}