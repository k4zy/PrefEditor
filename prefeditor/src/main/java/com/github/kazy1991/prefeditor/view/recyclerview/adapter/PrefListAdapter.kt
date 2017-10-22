package com.github.kazy1991.prefeditor.view.recyclerview.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kazy1991.prefeditor.R
import io.reactivex.subjects.PublishSubject

class PrefListAdapter : RecyclerView.Adapter<PrefListAdapter.ViewHolder>() {

    val list = ArrayList<Pair<String, String>>()

    val valueClickSubject = PublishSubject.create<Triple<Int, String, String>>()!!

    fun updateItem(position: Int, key: String, value: String) {
        list.add(position, Pair(key, value))
        list.removeAt(position + 1)
        notifyItemChanged(position)
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.cell_pref_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = list[position]
        holder.prefKeyView.text = item.first

        holder.prefValueView.apply {
            text = item.second
            setOnClickListener {
                valueClickSubject.onNext(Triple(position, item.first, item.second))
            }
        }


        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.indigo50))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val prefKeyView by lazy { itemView.findViewById(R.id.pref_key_view) as TextView }
        val prefValueView by lazy { itemView.findViewById(R.id.pref_value_view) as TextView }
    }

}
