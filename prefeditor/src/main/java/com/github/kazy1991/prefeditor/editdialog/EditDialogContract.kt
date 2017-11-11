package com.github.kazy1991.prefeditor.editdialog

import io.reactivex.Flowable

class EditDialogContract {
    interface View {

        val submitButtonStream: Flowable<Int>

        val inputValue: String

        val booleanValue: String

        fun setDialogTitle(title: String)

        fun setupInputField(inputType: Int, value: String)

        fun setupBooleanSpinner(selection: Int)

        fun onItemUpdate(position: Int, key: String, newValue: String)
    }

    interface Interactor {

        fun updatePreference(newValue: String)

    }
}
