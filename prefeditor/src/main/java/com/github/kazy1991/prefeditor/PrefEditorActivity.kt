package com.github.kazy1991.prefeditor

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import com.github.kazy1991.prefeditor.presenter.PrefEditorPresenter
import com.github.kazy1991.prefeditor.view.PrefEditorView
import java.io.File


class PrefEditorActivity : AppCompatActivity(), PrefEditorView {

    val navigationFrame by lazy { findViewById(R.id.navigation_frame) as RecyclerView }

    val drawerLayout by lazy { findViewById(R.id.drawer_layout) as DrawerLayout }

    val actionBarToggle by lazy {
        ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
    }

    val navigationAdapter = NavigationAdapter(ArrayList())

    val spinner by lazy { findViewById(R.id.spinner) as Spinner }

    lateinit var presenter: PrefEditorPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pref_editor)
        val basedir = File(applicationInfo.dataDir, "shared_prefs")
        val defaultPrefName = "${application.packageName}_preferences"
        presenter = PrefEditorPresenter(this, basedir, defaultPrefName)

        drawerLayout.addDrawerListener(actionBarToggle)
        navigationFrame.adapter = navigationAdapter
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        navigationAdapter
                .itemTappedSubject
                .subscribe { it ->
                    onNavigationItemTapped(it)
                }
    }

    override fun updateNavigation(list: List<NavigationItem>) {
        navigationAdapter.addAll(list)
        navigationAdapter.notifyDataSetChanged()
        val adapter = SchemaSpinnerAdapter(this, list)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, _: View?, position: Int, _: Long) {
                parent?.getItemAtPosition(position)?.let {
                    if (it is NavigationItem) {
                        onNavigationItemTapped(it)
                    }
                }
            }
        }
    }

    override fun setupDefaultFragment(prefName: String) {
        val fragment = PrefListFragment.newInstance(prefName)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }

    override fun onNavigationItemTapped(item: NavigationItem) {
        drawerLayout.closeDrawers()
        val fragment = PrefListFragment.newInstance(item.name)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
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
