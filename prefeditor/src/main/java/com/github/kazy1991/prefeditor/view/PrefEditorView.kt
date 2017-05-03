package com.github.kazy1991.prefeditor.view

import com.github.kazy1991.prefeditor.model.PrefItem

interface PrefEditorView {

    fun updatePrefNameList(list: List<PrefItem>)

    fun replacePrefView(prefName: String)
}