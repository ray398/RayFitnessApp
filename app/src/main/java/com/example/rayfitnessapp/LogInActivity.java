package com.example.rayfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogIn;
    private CheckBox checkBoxRememberMe;
    private TextView signUpTXT;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        editTextEmail = findViewById(R.id.editTextEmailLG);
        editTextPassword = findViewById(R.id.editTextPasswordLg);
        buttonLogIn = findViewById(R.id.buttonLogIn);
        checkBoxRememberMe = findViewById(R.id.checkBoxRememberMe);
        signUpTXT = findViewById(R.id.textViewSignUp);

        if (sharedPreferences.getBoolean("rememberMe",false)){
            editTextEmail.setText(sharedPreferences.getString("email", ""));
            checkBoxRememberMe.setChecked(true);
        }
        buttonLogIn.setOnClickListener(view -> logInUser());

        // To sign up page
        signUpTXT.setOnClickListener(v -> {
            startActivity(new Intent(LogInActivity.this, SignInActivity.class));
        });
    }

    private void logInUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        if (checkBoxRememberMe.isChecked()){
                            sharedPreferences.edit().putString("email", email)
                                    .putBoolean("rememberMe", true).apply();
                        }else {
                            sharedPreferences.edit().clear().apply();
                        }
                        Toast.makeText(this, "Log in successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                        finish();
                    }else {
                        Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}