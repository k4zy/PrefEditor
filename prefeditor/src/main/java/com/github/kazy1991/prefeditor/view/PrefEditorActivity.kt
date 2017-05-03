package com.github.kazy1991.prefeditor.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Spinner
import com.github.kazy1991.prefeditor.R
import com.github.kazy1991.prefeditor.adapter.SchemaSpinnerAdapter
import com.github.kazy1991.prefeditor.callback.SearchViewWrapperCallback
import com.github.kazy1991.prefeditor.callback.SpinnerWrapperCallback
import com.github.kazy1991.prefeditor.model.PrefItem
import com.github.kazy1991.prefeditor.presenter.PrefEditorPresenter
import com.github.kazy1991.prefeditor.repository.FileSystemPrefListRepository
import com.github.kazy1991.prefeditor.usecase.PrefEditorUseCaseImpl

class PrefEditorActivity : AppCompatActivity(), PrefEditorView {

    val spinner by lazy { findViewById(R.id.spinner) as Spinner }

    lateinit var presenter: PrefEditorPresenter

    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pref_editor)
        // todo: DI
        val prefListRepository = FileSystemPrefListRepository(this)
        val prefEditorUseCase = PrefEditorUseCaseImpl(this, prefListRepository)
        presenter = PrefEditorPresenter(this, prefEditorUseCase)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val menuItem = menu?.findItem(R.id.toolbar_menu_search)
        searchView = (MenuItemCompat.getActionView(menuItem) as SearchView).apply {
            setIconifiedByDefault(true)
            isSubmitButtonEnabled = false
            setOnSearchClickListener { presenter.startSearch() }
            setOnQueryTextListener(object : SearchViewWrapperCallback {
                override fun onTextChange(newText: String): Boolean {
                    presenter.onSearchTextChanged(newText)
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }

    override fun updatePrefNameList(list: List<PrefItem>) {
        spinner.adapter = SchemaSpinnerAdapter(this, list)
        spinner.onItemSelectedListener = object : SpinnerWrapperCallback {
            override fun onItemSelected(item: PrefItem) {
                presenter.onItemTapped(item)
            }
        }
    }

    override fun replacePrefView(prefName: String) {
        replaceFragment(PrefListFragment.newInstance(prefName))
    }

}
