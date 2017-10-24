package com.github.kazy1991.prefeditor.tools

import android.view.View
import android.widget.AdapterView

import com.github.kazy1991.prefeditor.entity.SchemaItem
import io.reactivex.subjects.PublishSubject


class SchemaSpinnerListener(private val subject: PublishSubject<SchemaItem>) : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val navigationItem = parent.getItemAtPosition(position) as SchemaItem
        subject.onNext(navigationItem)
    }
}
