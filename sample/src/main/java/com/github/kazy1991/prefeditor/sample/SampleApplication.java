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
    }
}
