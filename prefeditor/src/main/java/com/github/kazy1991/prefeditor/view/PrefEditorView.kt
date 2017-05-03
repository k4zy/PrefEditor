package com.github.kazy1991.prefeditor.view

import com.github.kazy1991.prefeditor.PrefItem

interface PrefEditorView {

    fun updatePrefNameList(list: List<PrefItem>)

    fun setupDefaultPrefList(prefName: String)

    fun onItemTapped(item: PrefItem)
}