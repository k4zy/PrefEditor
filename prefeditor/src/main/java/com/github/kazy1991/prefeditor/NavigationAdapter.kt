package com.github.kazy1991.prefeditor

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.reactivex.subjects.PublishSubject
import java.util.*

class NavigationAdapter(items: MutableList<NavigationItem>) : RecyclerView.Adapter<NavigationAdapter.ViewHolder>() {

    val itemTappedSubject = PublishSubject.create<String>()!!

    private val items = ArrayList<NavigationItem>()

    init {
        this.items.addAll(items)
    }

    fun addAll(items: List<NavigationItem>) {
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = inflater.inflate(R.layout.cell_navigation, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.normalizedName()
        holder.view.setOnClickListener { itemTappedSubject.onNext(item.name) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name by lazy { itemView.findViewById(R.id.name) as TextView }
    }

}
