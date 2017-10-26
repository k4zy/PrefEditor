package com.github.kazy1991.prefeditor.view.spinner.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.github.kazy1991.prefeditor.R
import com.github.kazy1991.prefeditor.entity.SchemaItem
import java.util.*

class SchemaSpinnerAdapter @JvmOverloads constructor(context: Context, objects: List<SchemaItem> = ArrayList()) : ArrayAdapter<SchemaItem>(context, 0, objects) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = inflater.inflate(R.layout.cell_spinner, parent, false)
        (view.findViewById<TextView>(R.id.text)).text = item!!.normalizedName()
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val view = inflater.inflate(R.layout.cell_spinner, parent, false)
        (view.findViewById<TextView>(R.id.text)).text = item!!.normalizedName()
        return view
    }

}
