package com.github.kazy1991.prefeditor.sample


import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.kazy1991.prefeditor.sample.databinding.CellPrefListBinding

class PrefListAdaper(val list: List<Pair<String, String>>) : RecyclerView.Adapter<PrefListAdaper.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = DataBindingUtil.inflate<CellPrefListBinding>(inflater, R.layout.cell_pref_list, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.pefKeyView.text = item.first
        holder.binding.pefValueView.text = item.second
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(val binding: CellPrefListBinding) : RecyclerView.ViewHolder(binding.root)

}
