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
        return setupView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return setupView(position, convertView, parent)
    }

    private fun setupView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        convertView?.let {
            val holder = it.tag as ViewHolder
            holder.textView.text = item!!.normalizedName()
            return it
        }.run {
            val view = inflater.inflate(R.layout.cell_spinner, parent, false)
            val holder = ViewHolder(view)
            holder.textView.text = item!!.normalizedName()
            view.tag = holder
            return view
        }
    }

    class ViewHolder(view: View) {
        val textView by lazy { view.findViewById<TextView>(R.id.text)!! }
    }

}
