package com.github.kazy1991.prefeditor.sample

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefsDir = File(applicationInfo.dataDir, "shared_prefs")
        if (prefsDir.exists() && prefsDir.isDirectory) {
            for (fileName in prefsDir.list()) {
                val prefName = fileName.substring(0, fileName.lastIndexOf('.'))
                Log.d(MainActivity::class.java.simpleName, prefName)
                val pref = getSharedPreferences(prefName, Context.MODE_PRIVATE)
                for ((key, value) in pref.all) {
                    Log.d("map values", key + ": " + value.toString())
                }
            }
        }

    }
}
