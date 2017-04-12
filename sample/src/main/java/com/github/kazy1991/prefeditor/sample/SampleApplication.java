package com.github.kazy1991.prefeditor.sample;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.github.kazy1991.prefkit.PrefKit;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PrefKit prefKit = new PrefKit(this);
        SampleOneSchema sampleOneSchema = prefKit.create(SampleOneSchema.class);
        sampleOneSchema.setFeatureFlag1(true);
        SharedPreferences defaultPref = PreferenceManager.getDefaultSharedPreferences(this);
        defaultPref.edit().putString("SampleKey", "Hello world").apply();
        defaultPref.edit().putString("UserName", "Kazuki Yoshida").apply();
        defaultPref.edit().putInt("age", 25).apply();
        defaultPref.edit().putBoolean("isDead", false).apply();
        defaultPref.edit().putFloat("shoeSize", 29.5f).apply();
    }
}
