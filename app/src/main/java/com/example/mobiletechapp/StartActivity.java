package com.example.mobiletechapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonUIEvent = findViewById(R.id.buttonUIEvent);
        Button buttonMLKit = findViewById(R.id.btn_mlkit);
        Button buttonSQLite = findViewById(R.id.buttonSQLite);
        Button buttonAnimation = findViewById(R.id.buttonAnimation);
        Button buttonMultimedia = findViewById(R.id.buttonMultimedia);

        buttonUIEvent.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, MainActivity.class);
            intent.putExtra("message", "Hello World!");
            startActivity(intent);
        });

        buttonMLKit.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, MLKitActivity.class);
            startActivity(intent);
        });

        buttonSQLite.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, SQLiteActivity.class);
            startActivity(intent);
        });

        buttonAnimation.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, AnimationActivity.class);
            startActivity(intent);
        });

        buttonMultimedia.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, MultimediaActivity.class);
            startActivity(intent);
        });
    }
}