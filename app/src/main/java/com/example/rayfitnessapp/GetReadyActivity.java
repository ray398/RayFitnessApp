package com.example.rayfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetReadyActivity extends AppCompatActivity {
    private TextView textViewCountdown;
    private Button buttonSkipGR;
    private CountDownTimer countDownTimerGR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ready);

        textViewCountdown = findViewById(R.id.textViewCountDown);
        buttonSkipGR = findViewById(R.id.buttonStartGR);

        countDownTimerGR = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                startNextActivity();
            }
        };
        countDownTimerGR.start();

        buttonSkipGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimerGR.cancel();
                startNextActivity();
            }
        });
    }
    private void startNextActivity(){
        Intent intent = new Intent(GetReadyActivity.this, TrainingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimerGR != null){
            countDownTimerGR.cancel();
        }
    }
}