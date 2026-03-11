package com.example.mobiletechapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextText;
    private TextView textViewOutput;
    private Button buttonOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textViewOutput = findViewById(R.id.textViewOutput);
        editTextText = findViewById(R.id.editTextText);
        buttonOk = findViewById(R.id.buttonOk);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String msg = extras.getString("message");
            if (msg != null) {
                textViewOutput.setText(msg);
            }
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMessage(view);
            }
        });
    }

    public void displayMessage(View view) {
        String message = editTextText.getText().toString();
        textViewOutput.setText(message);

        Toast.makeText(this, "OK button clicked.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(MainActivity.this, RealtimeDatabaseActivity.class);
        startActivity(intent);
    }
}