package com.github.kazy1991.prefeditor.callback

interface SpinnerWrapperCallback : android.widget.AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
        // No impl
    }

    override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
        parent?.getItemAtPosition(position)?.let {
            if (it is com.github.kazy1991.prefeditor.model.PrefItem) {
                onItemSelected(it)
            }
        }
    }

    fun onItemSelected(item: com.github.kazy1991.prefeditor.model.PrefItem)
}