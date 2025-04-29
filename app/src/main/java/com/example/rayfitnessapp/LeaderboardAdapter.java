package com.example.rayfitnessapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    private List<LeaderboardEntry> leaderboard;

    public LeaderboardAdapter(List<LeaderboardEntry> leaderboard) {
        this.leaderboard = leaderboard;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.landboard_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LeaderboardEntry entry = leaderboard.get(position);
        holder.textViewCount.setText(String.valueOf(position + 1)); // Rank (1-based)
        holder.textViewPositionName.setText(entry.getUsername());
        holder.textViewHisUserPoints.setText(entry.getPoints() + " points");
    }

    @Override
    public int getItemCount() {
        return leaderboard.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCount, textViewPositionName, textViewHisUserPoints;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCount = itemView.findViewById(R.id.textViewCount);
            textViewPositionName = itemView.findViewById(R.id.textViewPositionName);
            textViewHisUserPoints = itemView.findViewById(R.id.textViewHisUserPoints);
        }
    }
}
