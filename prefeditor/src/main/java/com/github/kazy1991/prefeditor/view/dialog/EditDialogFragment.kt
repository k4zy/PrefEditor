package com.github.kazy1991.prefeditor.view.dialog


import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Spinner
import com.github.kazy1991.prefeditor.R

class EditDialogFragment : DialogFragment() {

    enum class ValueType {
        NUMBER, TEXT, BOOLEAN
    }

    private var editText: EditText? = null

    private var booleanSpinner: Spinner? = null

    private val prefName: String
        get() = arguments.getString(ARGS_PREF_NAME)

    private val key: String
        get() = arguments.getString(ARGS_KEY)

    private val value: String
        get() = arguments.getString(ARGS_VALUE)

    private val position: Int
        get() = arguments.getInt(ARGS_POSITION)

    private val selection: Int
        get() {
            return when (value) {
                "true" -> 0
                "false" -> 1
                else -> 1
            }
        }

    private val valueType: ValueType
        get() {
            return when {
                """(true|false)""".toRegex().matches(value) -> ValueType.BOOLEAN
                """(\d+|\d+.\d+)""".toRegex().matches(value) -> ValueType.NUMBER
                else -> ValueType.TEXT
            }
        }

    private val newValue: String
        get() {
            return when (valueType) {
                ValueType.BOOLEAN -> {
                    booleanSpinner?.selectedItem.toString()
                }
                else -> {
                    editText?.text.toString()
                }
            }
        }

    private val inputType: Int
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context, R.style.CustomAlertDialogStyle).apply {
            setView(customView())
            setTitle("$key を更新する")
            setPositiveButton("上書き", { _, _ ->
                if (parentFragment is Callback) {
                    context.getSharedPreferences(prefName, Context.MODE_PRIVATE).apply {
                        edit().putString(key, newValue).apply()
                    }
                    (parentFragment as Callback).onItemUpdate(position, key, newValue)
                }
            })
            setNegativeButton("キャンセル", null)
        }.create()
    }

    private fun customView(): View {
        return when (valueType) {
            ValueType.BOOLEAN -> {
                booleanCustomView()
            }
            else -> {
                normalCustomView()
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun normalCustomView(): View {
        val inflater = LayoutInflater.from(context)
        val customView = inflater.inflate(R.layout.dialog_edit, null, false)
        (customView.findViewById<EditText>(R.id.value_edit_text)).also {
            this.editText = it
            it.inputType = inputType
            it.append(value)
        }
        return customView
    }

    @SuppressLint("InflateParams")
    private fun booleanCustomView(): View {
        val inflater = LayoutInflater.from(context)
        val customView = inflater.inflate(R.layout.dialog_boolean_edit, null, false)
        (customView.findViewById<Spinner>(R.id.boolean_spinner)).also {
            this.booleanSpinner = it
            it.setSelection(selection)
        }
        return customView
    }

    companion object {
        private val TAG = EditDialogFragment::class.java.simpleName!!
        val ARGS_PREF_NAME = "args_pref_name"
        val ARGS_KEY = "args_key"
        val ARGS_VALUE = "args_value"
        val ARGS_POSITION = "args_position"

        interface Callback {
            fun onItemUpdate(position: Int, key: String, newValue: String)
        }

        private fun newInstance(prefName: String, key: String, value: String, position: Int): EditDialogFragment {
            return EditDialogFragment().also {
                it.arguments = Bundle().apply {
                    putString(ARGS_PREF_NAME, prefName)
                    putString(ARGS_KEY, key)
                    putString(ARGS_VALUE, value)
                    putInt(ARGS_POSITION, position)
                }
            }
        }

        fun show(prefName: String, key: String, value: String, position: Int, fragmentManager: FragmentManager) {
            newInstance(prefName, key, value, position).show(fragmentManager, TAG)
        }
    }
}
