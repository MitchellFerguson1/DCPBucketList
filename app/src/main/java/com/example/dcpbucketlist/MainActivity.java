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

    private void copyAssets() throws IOException {
        String[] parkNames = {"MagicKingdom", "Epcot", "HollywoodStudios", "AnimalKingdom"};
        String[] files = {"mkfood.txt", "epfood.txt", "hsfood.txt", "akfood.txt"};

        // Loop through all the park preferences specified above
        for (int i = 0; i < files.length; i++) {
            // Get shared preferences with all the park names specified above
            SharedPreferences pref = getApplicationContext().getSharedPreferences(parkNames[i], 0);
            // Create an editor with the preferences
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
            // Open or create the file for the specific park. Basic file readin
            InputStream is = getApplicationContext().openFileInput(files[i]);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Scanner s = null;
            String line;
            while ((line = br.readLine()) != null) {
                s = new Scanner(line).useDelimiter("\\t");
                String name = s.next();
                // Check to see if the restaurant is in the preference
                if (pref.contains(name))
                    // If it is break since I don't plan on updating this until before I leave
                    break;
                // Set the value to false
                editor.putBoolean(name, false).apply();
            }
            // Close everything
            if (s != null)
                s.close();
            br.close();
            is.close();
        }
    }
}
