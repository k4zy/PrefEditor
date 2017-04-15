package com.github.kazy1991.prefeditor.sample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kazy1991.prefeditor.sample.databinding.ActivityMainBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main);

        Observable.just(File(applicationInfo.dataDir, "shared_prefs"))
                .filter { it.exists() && it.isDirectory }
                .flatMap { Observable.fromIterable(it.list().toList()) }
                .map { it.substring(0, it.lastIndexOf('.')) }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    binding.viewPager.adapter = PrefListPagerAdapter(it, supportFragmentManager)
                }
    }
}