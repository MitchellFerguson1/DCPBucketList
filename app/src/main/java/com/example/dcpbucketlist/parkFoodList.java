package com.example.dcpbucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeMap;

public class parkFoodList extends AppCompatActivity {
    String parkName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_food_list);
        try {
            loadList(Objects.requireNonNull(getIntent().getStringExtra("Park name")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadList(String park) throws IOException {
        LinearLayout lr = findViewById(R.id.linearLayout);
        parkName = park;

        // Get the proper preference
        SharedPreferences pref = getApplicationContext().getSharedPreferences(park, 0);
        // Create a tree map of all the entries to make it easier to loop through
        TreeMap<String, ?> entries = new TreeMap<String, Object>(pref.getAll());
        for (Map.Entry<String, ?> entry : entries.entrySet()) {
            // Get the restaurant name (The key)
            String name = entry.getKey();
            // Get whether the restaurant has been eaten at (Boolean, Value)
            boolean checked = Boolean.parseBoolean(entry.getValue().toString());
            // Create a new checkbox with the Key as the label and set the Value
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(name);
            cb.setChecked(checked);
            // Add it to the layout
            lr.addView(cb);
        }
    }

    public void finish(View view) {
        if (parkName == null) finish();

        LinearLayout lr = findViewById(R.id.linearLayout);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(parkName, 0);
        SharedPreferences.Editor edit = pref.edit();

        for (int i = 0; i < lr.getChildCount(); i++) {
            View v = lr.getChildAt(i);
            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                edit.putBoolean(cb.getText().toString(), cb.isChecked());
            }
        }
        edit.apply();
        finish();
    }
}
