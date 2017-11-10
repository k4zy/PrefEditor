package com.github.kazy1991.prefeditor.view.activity

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.Spinner
import com.github.kazy1991.prefeditor.R
import com.github.kazy1991.prefeditor.contract.PrefEditorContract
import com.github.kazy1991.prefeditor.entity.SchemaItem
import com.github.kazy1991.prefeditor.presenter.PrefEditorPresenter
import com.github.kazy1991.prefeditor.tools.SchemaSpinnerListener
import com.github.kazy1991.prefeditor.view.spinner.adapter.SchemaSpinnerAdapter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class PrefEditorActivity : AppCompatActivity(), PrefEditorContract.View {

    override val fragmentManagerProxy: FragmentManager
        get() = supportFragmentManager

    override val spinnerSelectedItems: Flowable<SchemaItem>
        get() = schemaSpinnerListener.spinnerSelectedItems.toFlowable(BackpressureStrategy.LATEST)

    private val schemaSpinnerListener = SchemaSpinnerListener()

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
        spinner.onItemSelectedListener = schemaSpinnerListener
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun updateSchemaItems(list: List<SchemaItem>) {
        adapter.addAll(list)
        adapter.notifyDataSetChanged()
    }
}
