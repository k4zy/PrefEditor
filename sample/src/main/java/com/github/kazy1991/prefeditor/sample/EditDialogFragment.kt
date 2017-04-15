package com.github.kazy1991.prefeditor.sample


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import android.widget.EditText

class EditDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var editText: EditText

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity.layoutInflater
        val customView = inflater.inflate(R.layout.dialog_edit, null).apply {
            editText = (findViewById(R.id.value_edit_text) as EditText)
                    .also { it.append(arguments.getString(ARGS_VALUE)) }
        }

        return AlertDialog.Builder(context, R.style.CustomAlertDialogStyle).apply {
            setView(customView)
            setTitle(title())
            setPositiveButton("上書き", { _, _ ->
                if (parentFragment is EditDialogCallback) {
                    (parentFragment as EditDialogCallback).onItemUpdate(position(), key(), newValue())
                }
            })
            setNegativeButton("キャンセル", null)
        }.create()
    }

    fun newValue(): String {
        return editText.text.toString()
    }

    fun position(): Int {
        return arguments.getInt(ARGS_POSITION)
    }

    fun key(): String {
        return arguments.getString(ARGS_KEY)
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
        val TAG = EditDialogFragment.javaClass.simpleName!!

        private fun newInstance(position: Int, key: String, value: String): EditDialogFragment {
            return EditDialogFragment().also {
                it.arguments = Bundle().apply {
                    putInt(ARGS_POSITION, position)
                    putString(ARGS_KEY, key)
                    putString(ARGS_VALUE, value)
                }
            }
        }

        fun show(item: Triple<Int, String, String>, fragment: Fragment) {
            newInstance(item.first, item.second, item.third).show(fragment.childFragmentManager, TAG)
        }
    }
}