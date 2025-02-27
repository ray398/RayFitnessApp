package com.example.rayfitnessapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WorkoutTypeAdapter extends RecyclerView.Adapter<WorkoutTypeAdapter.HomeViewHolder> {

    private List<WorkoutType> workoutTypes;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(WorkoutType workoutType);
    }

    public WorkoutTypeAdapter(Context context, List<WorkoutType> workoutTypes, OnItemClickListener onItemClickListener) {
        this.workoutTypes = workoutTypes;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        WorkoutType workoutType = workoutTypes.get(position);
        holder.textViewExerciseName.setText(workoutType.getName());

        String coverPhotoName = workoutType.getCoverPhoto();
        int drawableId = context.getResources().getIdentifier(coverPhotoName, "drawable", context.getPackageName());

        Glide.with(context)
                .load(drawableId != 0 ? drawableId : R.drawable.inclined3) // Fallback to a default image
                .placeholder(R.drawable.workout) // Placeholder image
                .into(holder.imageViewCoverPhotoH);

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(workoutType));
    }

    @Override
    public int getItemCount() {
        return workoutTypes.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewExerciseName;
        ImageView imageViewCoverPhotoH;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewExerciseName = itemView.findViewById(R.id.textViewSpecificExName);
            imageViewCoverPhotoH = itemView.findViewById(R.id.imageViewCoverPhotoH);
        }
    }
}
