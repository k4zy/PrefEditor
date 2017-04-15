package com.github.kazy1991.prefeditor.sample

import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.kazy1991.prefeditor.sample.databinding.CellPrefListBinding
import io.reactivex.subjects.PublishSubject

class PrefListAdapter : RecyclerView.Adapter<PrefListAdapter.ViewHolder>() {

    val list = ArrayList<Pair<String, String>>()

    val valueClickSubject = PublishSubject.create<Triple<Int, String, String>>()!!

    fun updateItem(position: Int, key: String, value: String) {
        list.add(position, Pair(key, value))
        list.removeAt(position + 1)
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<CellPrefListBinding>(inflater, R.layout.cell_pref_list, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.binding.root.context
        val item = list[position]
        holder.binding.pefKeyView.text = item.first

        holder.binding.pefValueView.apply {
            text = item.second
            setOnClickListener {
                valueClickSubject.onNext(Triple(position, item.first, item.second))
            }
        }


        if (position % 2 == 0) {
            holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        } else {
            holder.binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.indigo50))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: CellPrefListBinding) : RecyclerView.ViewHolder(binding.root)

}
