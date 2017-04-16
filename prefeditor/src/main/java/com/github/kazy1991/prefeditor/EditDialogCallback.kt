package com.github.kazy1991.prefeditor


interface EditDialogCallback {

    fun onItemUpdate(position:Int, key: String, newValue: String)
}
