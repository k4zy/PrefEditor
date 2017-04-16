package com.github.kazy1991.prefeditor.sample

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class MainActivity : AppCompatActivity() {

    val navigationFrame by lazy { findViewById(R.id.navigation_frame) as RecyclerView }

    val drawerLayout by lazy { findViewById(R.id.drawer_layout) as DrawerLayout }

    val actionBarToggle by lazy {
        ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout.addDrawerListener(actionBarToggle)
        val navigationAdapter = NavigationAdapter(ArrayList())
        navigationFrame.adapter = navigationAdapter
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

//        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main);

        Observable.just(File(applicationInfo.dataDir, "shared_prefs"))
                .filter { it.exists() && it.isDirectory }
                .flatMap { Observable.fromIterable(it.list().toList()) }
                .map { it.substring(0, it.lastIndexOf('.')) }
                .map { NavigationItem(it) }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    navigationAdapter.addAll(it)
                    navigationAdapter.notifyDataSetChanged()
                    it.firstOrNull()?.let {
                        val fragment = PrefListFragment.newInstance(it.title)
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.content_frame, fragment)
                                .commit()
                    }
                }

        navigationAdapter
                .itemTappedSubject
                .subscribe { it ->
                    drawerLayout.closeDrawers()
                    val fragment = PrefListFragment.newInstance(it)
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit()
                }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
