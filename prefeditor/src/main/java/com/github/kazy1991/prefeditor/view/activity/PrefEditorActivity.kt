package com.github.kazy1991.prefeditor.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import com.github.kazy1991.prefeditor.*
import com.github.kazy1991.prefeditor.contract.PrefEditorContract
import com.github.kazy1991.prefeditor.entity.NavigationItem
import com.github.kazy1991.prefeditor.presenter.PrefEditorPresenter
import com.github.kazy1991.prefeditor.view.fragment.PrefListFragment
import com.github.kazy1991.prefeditor.view.recyclerview.adapter.NavigationAdapter
import com.github.kazy1991.prefeditor.view.spinner.adapter.SchemaSpinnerAdapter
import java.io.File


class PrefEditorActivity : AppCompatActivity(), PrefEditorContract.View {

    val navigationAdapter = NavigationAdapter(ArrayList())

    val spinner by lazy { findViewById(R.id.spinner) as Spinner }

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
