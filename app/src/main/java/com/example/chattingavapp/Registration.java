package com.example.chattingavapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chattingavapp.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    TextView loginbut;
    EditText rg_username, rg_email, rg_password, rg_repassword;
    Button rg_signup;
    FirebaseAuth auth;
    String status;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseDatabase database;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);

        // Firebase instances
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        loginbut = findViewById(R.id.loginbut);
        rg_username = findViewById(R.id.rgusername);
        rg_email = findViewById(R.id.rgemial);
        rg_password = findViewById(R.id.rgpassword);
        rg_repassword = findViewById(R.id.rgrepassword);
        rg_signup = findViewById(R.id.signupbutton);

        // Redirect to login activity
        loginbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, login.class);
                startActivity(intent);
                finish();
            }
        });

        // Sign-up button logic
        rg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namee = rg_username.getText().toString();
                String emaill = rg_email.getText().toString();
                String Password = rg_password.getText().toString();
                String cPassword = rg_repassword.getText().toString();
                String status = "Hey, I'm using this application";

                // Input validation
                if (TextUtils.isEmpty(namee) || TextUtils.isEmpty(emaill) ||
                        TextUtils.isEmpty(Password) || TextUtils.isEmpty(cPassword)) {
                    Toast.makeText(Registration.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
                } else if (!emaill.matches(emailPattern)) {
                    rg_email.setError("Type a valid email");
                } else if (Password.length() < 6) {
                    rg_password.setError("Password must be at least 6 characters");
                } else if (!Password.equals(cPassword)) {
                    rg_password.setError("Passwords do not match");
                } else {
                    progressDialog.show();

                    // Create user with Firebase Authentication
                    auth.createUserWithEmailAndPassword(emaill, Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String id = task.getResult().getUser().getUid();
                                        DatabaseReference reference = database.getReference().child("user").child(id);

                                        // Create user object with default image URL
                                        String defaultImageUri = "https://example.com/default_image.png"; // Placeholder URL
                                        Users Users = new Users(id, namee, emaill, Password, defaultImageUri, status);

                                        // Save user to Firebase Realtime Database
                                        reference.setValue(Users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                if (task.isSuccessful()) {
                                                    Intent intent = new Intent(Registration.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(Registration.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
