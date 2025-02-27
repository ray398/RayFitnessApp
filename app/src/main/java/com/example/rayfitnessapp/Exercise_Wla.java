package com.example.rayfitnessapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Exercise_Wla implements Parcelable {
    private String name;
    private String gifUrl;
    private String duration;

    public Exercise_Wla(String name, String gifUrl, String duration) {
        this.name = name;
        this.gifUrl = gifUrl;
        this.duration = duration;
    }
    protected Exercise_Wla(Parcel in){
        name = in.readString();
        gifUrl = in.readString();
        duration = in.readString();
    }
    public static final Creator<Exercise_Wla> CREATOR = new Creator<Exercise_Wla>() {
        @Override
        public Exercise_Wla createFromParcel(Parcel in) {
            return new Exercise_Wla(in);
        }

        @Override
        public Exercise_Wla[] newArray(int size) {
            return new Exercise_Wla[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(gifUrl);
        dest.writeString(duration);
    }
}
