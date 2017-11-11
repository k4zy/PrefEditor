package com.github.kazy1991.prefeditor.editdialog.interactor

import android.content.Context
import android.os.Bundle
import android.text.InputType
import com.github.kazy1991.prefeditor.editdialog.EditDialogContract
import com.github.kazy1991.prefeditor.editdialog.view.EditDialogFragment

class EditDialogInteractor(val context: Context, val arguments: Bundle) : EditDialogContract.Interactor {

    enum class ValueType {
        NUMBER, TEXT, BOOLEAN
    }

    private val prefName: String
        get() = arguments.getString(EditDialogFragment.ARGS_PREF_NAME)

    val key: String
        get() = arguments.getString(EditDialogFragment.ARGS_KEY)

    val value: String
        get() = arguments.getString(EditDialogFragment.ARGS_VALUE)

    val position: Int
        get() = arguments.getInt(EditDialogFragment.ARGS_POSITION)

    val selection: Int
        get() {
            return when (value) {
                "true" -> 0
                "false" -> 1
                else -> 1
            }
        }
    val valueType: ValueType
        get() {
            return when {
                """(true|false)""".toRegex().matches(value) -> ValueType.BOOLEAN
                """(\d+|\d+.\d+)""".toRegex().matches(value) -> ValueType.NUMBER
                else -> ValueType.TEXT
            }
        }

    val inputType: Int
        get() {
            return when (valueType) {
                ValueType.NUMBER -> {
                    InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                }
                ValueType.BOOLEAN -> {
                    InputType.TYPE_CLASS_TEXT
                }
                else -> {
                    InputType.TYPE_CLASS_TEXT
                }
            }
        }

    override fun updatePreference(newValue: String) {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE).apply {
            edit().putString(key, newValue).apply()
        }
    }
}