package com.github.kazy1991.prefeditor.sample

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.github.kazy1991.prefkit.PrefKit

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val prefKit = PrefKit(this)
        val sampleOneSchema = prefKit.create(SampleOneSchema::class.java)
        sampleOneSchema.setFeatureFlag1(true)
        val defaultPref = PreferenceManager.getDefaultSharedPreferences(this)
        defaultPref.edit().putString("SampleKey", "Hello world").apply()
        defaultPref.edit().putString("UserName", "Kazuki Yoshida").apply()
        defaultPref.edit().putInt("age", 25).apply()
        defaultPref.edit().putBoolean("isDead", false).apply()
        defaultPref.edit().putFloat("shoeSize", 29.5f).apply()
    }
}
