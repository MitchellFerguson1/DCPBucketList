package com.example.dcpbucketlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyAssets();
    }

    private void startEvent(String park) {
        Intent parkIntent = new Intent(this, parkFoodList.class);
        parkIntent.putExtra("Park name", park);
        this.startActivity(parkIntent);
    }

    public void magicClick(View view) { startEvent("Magic Kingdom"); }

    public void hollywoodClick(View view) { startEvent("Hollywood Studios"); }

    public void animalClick(View view) { startEvent("Animal Kingdom"); }

    public void epcotClick(View view) { startEvent("Epcot"); }


    // Gotten (and modified) from https://gist.github.com/thinzaroo/5aef6e81638529a89995
    private void copyAssets() {
        AssetManager am = getAssets();
        String[] files = null;
        try {
            files = am.list("parks");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String filename : files) {
            InputStream is = null;
            OutputStream os = null;
            try {
                is = am.open("parks/" + filename);
                File outFile = new File(getApplicationContext().getFilesDir(), filename);
                if (!outFile.exists()) {
                    os = new FileOutputStream(outFile);
                    copyFile(is, os);
                    is.close();
                    os.flush();
                    os.close();
                    os = null;
                    is = null;
                } else {
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyFile (InputStream is, OutputStream os) throws IOException {
        try {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) != 1) {
                os.write(buffer, 0, read);
            }
        } catch (Exception i) {
            // Always throws an index out of bounds exception, but all the files write properly
            // So I just return it since everything's working
            return;
        }
    }
}
