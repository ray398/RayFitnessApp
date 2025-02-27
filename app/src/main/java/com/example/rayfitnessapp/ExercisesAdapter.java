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

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ExerciseViewHolder> {

    private Context context;
    private List<Exercise> exerciseList;

    public ExercisesAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }

    public void updateData(List<Exercise> newExerciseList) {
        this.exerciseList = newExerciseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_row, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.textViewSpecificExName.setText(exercise.getName());
        holder.textViewReps.setText(exercise.getDurationOrSets());

        String gifName = exercise.getGifPath();
        int drawableGif = context.getResources().getIdentifier(gifName, "drawable", context.getPackageName());

        Glide.with(context)
                .load(drawableGif != 0 ? drawableGif : R.drawable.gif_curls)
                .placeholder(R.drawable.gif_curls)
                .into(holder.imageViewGIF);
    }

    @Override
    public int getItemCount() {
        return exerciseList != null ? exerciseList.size() : 0;
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSpecificExName, textViewReps;
        ImageView imageViewGIF;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSpecificExName = itemView.findViewById(R.id.textViewSpecificExName);
            textViewReps = itemView.findViewById(R.id.textViewReps);
            imageViewGIF = itemView.findViewById(R.id.imageViewGIF);
        }
    }
}
