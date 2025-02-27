package com.example.rayfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    AnalyticsFragment analyticsFragment = new AnalyticsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* WorkoutDatabaseHelper dbHelper = new WorkoutDatabaseHelper(this);
        dbHelper.fixIncorrectGifPaths(); */

        navigationBar();
    }
    public void navigationBar(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, homeFragment).commit();
                    return true;
                } else if (itemId == R.id.analytics) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, analyticsFragment).commit();
                    return true;
                } else if (itemId == R.id.profile) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, profileFragment).commit();
                    return true;
                }
                return false;
            }
        });

    }

}