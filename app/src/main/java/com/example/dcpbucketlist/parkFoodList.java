package com.example.dcpbucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public class parkFoodList extends AppCompatActivity {
    String file;
    InputStream is;
    OutputStream os;
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
        switch (park) {
            case "Magic Kingdom":
                is = getApplicationContext().openFileInput("mkfood.txt");
                break;

            case "Hollywood Studios":
                is = getApplicationContext().openFileInput("hsfood.txt");
                break;

            case "Animal Kingdom":
                is = getApplicationContext().openFileInput("akfood.txt");
                break;

            case "Epcot":
                is = getApplicationContext().openFileInput("epfood.txt");
                break;
        }

        LinearLayout lr = (LinearLayout) findViewById(R.id.linearLayout);

        for(int i = 0; i < 20; i++) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText("Something");
            lr.addView(cb);
        }
    }

    public void finish(View view) {
        finish();
    }
}
