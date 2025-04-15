package com.example.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class HomeActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String userName = getIntent().getStringExtra("userName");
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome, " + userName + "!");

        Button logoutBtn = findViewById(R.id.logoutBtn);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        logoutBtn.setOnClickListener(v -> {
            mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            });
        });
    }
}
