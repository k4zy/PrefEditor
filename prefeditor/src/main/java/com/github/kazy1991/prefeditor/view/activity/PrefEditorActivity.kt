package com.github.kazy1991.prefeditor.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Spinner
import com.github.kazy1991.prefeditor.R
import com.github.kazy1991.prefeditor.contract.PrefEditorContract
import com.github.kazy1991.prefeditor.entity.SchemaItem
import com.github.kazy1991.prefeditor.presenter.PrefEditorPresenter
import com.github.kazy1991.prefeditor.tools.SchemaSpinnerListener
import com.github.kazy1991.prefeditor.view.fragment.PrefListFragment
import com.github.kazy1991.prefeditor.view.spinner.adapter.SchemaSpinnerAdapter
import io.reactivex.subjects.PublishSubject


class PrefEditorActivity : AppCompatActivity(), PrefEditorContract.View {

    override val spinnerSelectedItems = PublishSubject.create<SchemaItem>()!!

    lateinit var adapter: SchemaSpinnerAdapter

    lateinit var presenter: PrefEditorPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pref_editor)
        presenter = PrefEditorPresenter(this, this)
        presenter.onAttach()

        adapter = SchemaSpinnerAdapter(this)
        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = SchemaSpinnerListener(spinnerSelectedItems)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun updateSchemaItems(list: List<SchemaItem>) {
        adapter.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun replacePrefSchema(prefName: String) {
        val fragment = PrefListFragment.newInstance(prefName)
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }
}
