package com.example.mobiletechapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class SQLiteActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> items = new ArrayList<>();
    ArrayAdapter<String> adapter;
    MyDbHelper dbHelper;
    String selectedItem = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sqlite);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new MyDbHelper(SQLiteActivity.this, "MobileTechResults", null, 1);

        items = dbHelper.readAll();
        items.add(0, "Select an item");

        spinner = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void save(View view) {
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextMark = findViewById(R.id.editTextMark);

        String name = editTextName.getText().toString().trim();
        String mark = editTextMark.getText().toString().trim();

        if (name.isEmpty() || mark.isEmpty()) {
            Toast.makeText(this, "Please enter both name and mark", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Double.parseDouble(mark);
            dbHelper.create(name, mark);
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();

            editTextName.setText("");
            editTextMark.setText("");
            refreshSpinner();
        } catch (Exception e) {
            Toast.makeText(this, "Not a valid mark", Toast.LENGTH_LONG).show();
        }
    }

    public void update(View view) {
        EditText editTextNewMark = findViewById(R.id.editTextNewMark);

        if (selectedItem.equals("Select an item")) {
            Toast.makeText(this, "Please select an item", Toast.LENGTH_SHORT).show();
            return;
        }

        String newMark = editTextNewMark.getText().toString().trim();

        if (newMark.isEmpty()) {
            Toast.makeText(this, "Please enter a new mark", Toast.LENGTH_SHORT).show();
            return;
        }

        int from = selectedItem.indexOf(':') + 2;
        int to = selectedItem.indexOf(',');
        String name = selectedItem.substring(from, to);

        try {
            Double.parseDouble(newMark);
            dbHelper.update(name, newMark);
            Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show();

            editTextNewMark.setText("");
            refreshSpinner();
        } catch (Exception e) {
            Toast.makeText(this, "Not a valid mark", Toast.LENGTH_LONG).show();
        }
    }

    public void delete(View view) {
        if (selectedItem.equals("Select an item")) {
            Toast.makeText(this, "Please select an item", Toast.LENGTH_SHORT).show();
            return;
        }

        int from = selectedItem.indexOf(':') + 2;
        int to = selectedItem.indexOf(',');
        String name = selectedItem.substring(from, to);

        dbHelper.delete(name);
        Toast.makeText(this, "Data deleted", Toast.LENGTH_SHORT).show();
        refreshSpinner();
    }

    private void refreshSpinner() {
        items = dbHelper.readAll();
        items.add(0, "Select an item");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        selectedItem = "Select an item";
    }
}