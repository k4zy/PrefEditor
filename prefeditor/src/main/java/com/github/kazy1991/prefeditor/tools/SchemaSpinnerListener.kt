package com.github.kazy1991.prefeditor.tools

import android.view.View
import android.widget.AdapterView

import com.github.kazy1991.prefeditor.entity.SchemaItem
import io.reactivex.subjects.PublishSubject


class SchemaSpinnerListener : AdapterView.OnItemSelectedListener {

    val spinnerSelectedItems = PublishSubject.create<SchemaItem>()!!

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val navigationItem = parent.getItemAtPosition(position) as SchemaItem
        spinnerSelectedItems.onNext(navigationItem)
    }
}
