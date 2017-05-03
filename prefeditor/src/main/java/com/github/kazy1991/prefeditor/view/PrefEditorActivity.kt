package com.github.kazy1991.prefeditor.view

import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import com.github.kazy1991.prefeditor.presenter.PrefEditorPresenter
import com.github.kazy1991.prefeditor.view.PrefEditorView
import java.io.File
import android.support.v4.widget.SearchViewCompat.setOnQueryTextListener
import com.github.kazy1991.prefeditor.*


class PrefEditorActivity : AppCompatActivity(), PrefEditorView {

    val navigationAdapter = NavigationAdapter(ArrayList())

    val spinner by lazy { findViewById(R.id.spinner) as Spinner }

    var searchView : SearchView? = null

    lateinit var presenter: PrefEditorPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pref_editor)
        val basedir = File(applicationInfo.dataDir, "shared_prefs")
        val defaultPrefName = "${application.packageName}_preferences"
        presenter = PrefEditorPresenter(this, basedir, defaultPrefName)
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

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                parent?.getItemAtPosition(position)?.let {
                    if (it is NavigationItem) {
                        onNavigationItemTapped(it)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu?.findItem(R.id.toolbar_menu_search)
        searchView = (MenuItemCompat.getActionView(menuItem) as SearchView).also {
            it.setIconifiedByDefault(true)
            it.isSubmitButtonEnabled = false
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
//                suggestion_search(newText)
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun setupDefaultFragment(prefName: String) {
        val fragment = PrefListFragment.newInstance(prefName)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }

    override fun onNavigationItemTapped(item: NavigationItem) {
        val fragment = PrefListFragment.newInstance(item.name)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }
}
