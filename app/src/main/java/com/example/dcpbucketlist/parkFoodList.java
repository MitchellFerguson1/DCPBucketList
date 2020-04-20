package com.example.dcpbucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Scanner;

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

        try {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Scanner s;
            while ( (line = br.readLine()) != null) {
                s = new Scanner(line).useDelimiter("\\t");
                CheckBox cb = new CheckBox(getApplicationContext());
                cb.setText(s.next());
                if (s.next().equals("Y"))
                    cb.setChecked(true);
                lr.addView(cb);
                s.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void finish(View view) {
        finish();
    }
}
