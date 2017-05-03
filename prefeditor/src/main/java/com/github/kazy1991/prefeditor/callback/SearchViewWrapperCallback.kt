package com.github.kazy1991.prefeditor.callback

import android.support.v7.widget.SearchView.OnQueryTextListener

interface SearchViewWrapperCallback : OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return newText?.let { onTextChange(it) } ?: false
    }

    fun onTextChange(newText: String): Boolean
}