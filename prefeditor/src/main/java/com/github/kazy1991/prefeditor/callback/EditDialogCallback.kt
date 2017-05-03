package com.github.kazy1991.prefeditor.callback


interface EditDialogCallback {

    fun onItemUpdate(position:Int, key: String, newValue: String)
}
