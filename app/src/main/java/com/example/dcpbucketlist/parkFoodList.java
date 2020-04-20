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

public class parkFoodList extends AppCompatActivity {
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

        SharedPreferences pref = getApplicationContext().getSharedPreferences(park, 0);
        Map<String, ?> entries = pref.getAll();
        for (Map.Entry<String, ?> entry : entries.entrySet()) {
            String name = entry.getKey();
            boolean checked = Boolean.parseBoolean(entry.getValue().toString());
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(name);
            cb.setChecked(checked);
            lr.addView(cb);
        }
    }

    public void finish(View view) {
        finish();
    }
}
