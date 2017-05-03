package com.github.kazy1991.prefeditor.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import com.github.kazy1991.prefeditor.R

class PrefEditorActivity : AppCompatActivity() {

    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pref_editor)
        replaceFragment(PrefEditorFragment.newInstance())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu?.findItem(R.id.toolbar_menu_search)
        searchView = (MenuItemCompat.getActionView(menuItem) as SearchView).apply {
            setIconifiedByDefault(true)
            isSubmitButtonEnabled = false
//            setOnSearchClickListener { presenter.startSearch() }
//            setOnQueryTextListener(object : SearchViewWrapperCallback {
//                override fun onTextChange(newText: String): Boolean {
//                    presenter.onSearchTextChanged(newText)
//                    return false
//                }
//            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }

}
