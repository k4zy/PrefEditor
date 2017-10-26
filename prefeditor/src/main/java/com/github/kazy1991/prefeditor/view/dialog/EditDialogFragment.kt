package com.github.kazy1991.prefeditor.view.dialog


import android.annotation.SuppressLint
import android.app.Dialog
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

    interface Callback {

        fun onItemUpdate(position: Int, key: String, newValue: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    var editText: EditText? = null

    var booleanSpinner: Spinner? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context, R.style.CustomAlertDialogStyle).apply {
            setView(customView())
            setTitle(title())
            setPositiveButton("上書き", { _, _ ->
                if (parentFragment is Callback) {
                    (parentFragment as Callback).onItemUpdate(position(), key(), newValue())
                }
            })
            setNegativeButton("キャンセル", null)
        }.create()
    }

    fun customView(): View {
        when (valueType()) {
            ValueType.BOOLEAN -> {
                return booleanCustomView()
            }
            else -> {
                return normalCustomView()
            }
        }
    }

    @SuppressLint("InflateParams")
    fun normalCustomView(): View {
        val inflater = LayoutInflater.from(context)
        val customView = inflater.inflate(R.layout.dialog_edit, null, false)
        (customView.findViewById<EditText>(R.id.value_edit_text)).also {
            this.editText = it
            it.inputType = inputType()
            it.append(value())
        }
        return customView
    }

    @SuppressLint("InflateParams")
    fun booleanCustomView(): View {
        val inflater = LayoutInflater.from(context)
        val customView = inflater.inflate(R.layout.dialog_boolean_edit, null, false)
        (customView.findViewById<Spinner>(R.id.boolean_spinner)).also {
            this.booleanSpinner = it
            it.setSelection(selection())
        }
        return customView
    }

    fun selection(): Int {
        when (value()) {
            "true" -> return 0
            "false" -> return 1
            else -> return 1
        }
    }

    enum class ValueType {
        NUMBER, TEXT, BOOLEAN
    }

    fun inputType(): Int {
        when (valueType()) {
            ValueType.NUMBER -> {
                return InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            }
            ValueType.BOOLEAN -> {
                return InputType.TYPE_CLASS_TEXT
            }
            else -> {
                return InputType.TYPE_CLASS_TEXT
            }
        }
    }

    fun valueType(): ValueType {
        if ("""(true|false)""".toRegex().matches(value())) {
            return ValueType.BOOLEAN
        } else if ("""(\d+|\d+.\d+)""".toRegex().matches(value())) {
            return ValueType.NUMBER
        } else {
            return ValueType.TEXT
        }
    }

    fun newValue(): String {
        when (valueType()) {
            ValueType.BOOLEAN -> {
                return booleanSpinner?.selectedItem.toString()
            }
            else -> {
                return editText?.text.toString()
            }
        }
    }

    fun position(): Int {
        return arguments.getInt(ARGS_POSITION)
    }

    fun key(): String {
        return arguments.getString(ARGS_KEY)
    }

    fun value(): String {
        return arguments.getString(ARGS_VALUE)
    }

    fun title(): String {
        val key = arguments.getString(ARGS_KEY)
        return "$key を変更"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    companion object {
        val ARGS_POSITION = "args_position"
        val ARGS_KEY = "args_key"
        val ARGS_VALUE = "args_value"
        val TAG = EditDialogFragment::class.java.simpleName!!

        private fun newInstance(position: Int, key: String, value: String): EditDialogFragment {
            return EditDialogFragment().also {
                it.arguments = Bundle().apply {
                    putInt(ARGS_POSITION, position)
                    putString(ARGS_KEY, key)
                    putString(ARGS_VALUE, value)
                }
            }
        }

        fun show(item: Triple<Int, String, String>, fragmentManager: FragmentManager) {
            newInstance(item.first, item.second, item.third).show(fragmentManager, TAG)
        }
    }
}
