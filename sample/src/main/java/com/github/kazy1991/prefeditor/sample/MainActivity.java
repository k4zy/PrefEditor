package com.github.kazy1991.prefeditor.sample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        File prefsDir = new File(getApplicationInfo().dataDir, "shared_prefs");
        if (prefsDir.exists() && prefsDir.isDirectory()) {
            String[] list = prefsDir.list();
            for (String fileName : list) {
                String prefName = fileName.substring(0, fileName.lastIndexOf('.'));
                Log.d(MainActivity.class.getSimpleName(), prefName);
                SharedPreferences pref = getSharedPreferences(prefName, Context.MODE_PRIVATE);
                for (Map.Entry<String, ?> entry : pref.getAll().entrySet()) {
                    Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                }
            }
        }

    }
}
