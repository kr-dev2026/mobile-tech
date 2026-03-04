package com.example.mobiletechapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        // Get button from XML
        Button button = findViewById(R.id.buttonUIEvent);

        // Add click listener
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Create intent
                Intent intent = new Intent(StartActivity.this, MainActivity.class);

                // Step 13: Send data to MainActivity
                intent.putExtra("message", "Hello World!");

                // Open MainActivity
                startActivity(intent);
            }
        });
    }
}