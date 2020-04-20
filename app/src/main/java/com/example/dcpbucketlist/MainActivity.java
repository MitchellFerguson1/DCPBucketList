package com.example.dcpbucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            copyAssets();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startEvent(String park) {
        Intent parkIntent = new Intent(this, parkFoodList.class);
        parkIntent.putExtra("Park name", park);
        this.startActivity(parkIntent);
    }

    public void magicClick(View view) { startEvent("MagicKingdom"); }

    public void hollywoodClick(View view) { startEvent("HollywoodStudios"); }

    public void animalClick(View view) { startEvent("AnimalKingdom"); }

    public void epcotClick(View view) { startEvent("Epcot"); }


    // Gotten (and modified) from https://gist.github.com/thinzaroo/5aef6e81638529a89995
    private void copyAssets() throws IOException {
        String[] parkNames = {"MagicKingdom", "Epcot", "HollywoodStudios", "AnimalKingdom"};
        String[] files = {"mkfood.txt", "epfood.txt", "hsfood.txt", "akfood.txt"};

        for (int i = 0; i < files.length; i++) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences(parkNames[i], 0);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
            InputStream is = getApplicationContext().openFileInput(files[i]);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Scanner s = null;
            String line;
            while ((line = br.readLine()) != null) {
                s = new Scanner(line).useDelimiter("\\t");
                String name = s.next();
                if (pref.contains(name))
                    break;
                editor.putBoolean(name, false).apply();
            }
            if (s != null)
                s.close();
            br.close();
            is.close();
        }
    }
}
