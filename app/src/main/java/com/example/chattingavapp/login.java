package com.example.chattingavapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    Button button;
    EditText email, password;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Hide ActionBar if present
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        button = findViewById(R.id.logbutton);
        email = findViewById(R.id.editTextLogEmail);
        password = findViewById(R.id.editTextLogPassword);

        // Handle login button click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                // Validate inputs
                if (TextUtils.isEmpty(Email)) {
                    email.setError("Enter your email");
                    return;
                }
                if (!Email.matches(emailPattern)) {
                    email.setError("Enter a valid email address");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Enter your password");
                    return;
                }
                if (pass.length() < 6) {
                    password.setError("Password must be at least 6 characters");
                    return;
                }

                // Authenticate with Firebase
                auth.signInWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Login successful, navigate to MainActivity
                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Login failed, show error message
                            Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
