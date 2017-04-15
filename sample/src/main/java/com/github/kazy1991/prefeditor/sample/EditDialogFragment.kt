package com.github.kazy1991.prefeditor.sample


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText


class EditDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setStyle(style, theme);
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity.layoutInflater
        val customView = inflater.inflate(R.layout.dialog_edit, null).apply {
            (findViewById(R.id.edit_text) as EditText).append(arguments.getString(ARGS_VALUE))
        }

        return AlertDialog.Builder(context).apply {
            setView(customView)
            setTitle(title())
            setPositiveButton("上書き", { _, _ ->
            })
            setNegativeButton("キャンセル", null)
        }.create()
    }

    fun title(): String {
        val key = arguments.getString(ARGS_KEY)
        return "$key を変更"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    companion object {

        val ARGS_VALUE = "args_value"
        val ARGS_KEY = "args_key"

        fun newInstance(key: String, value: String): EditDialogFragment {
            return EditDialogFragment().also {
                it.arguments = Bundle().apply {
                    putString(ARGS_KEY, key)
                    putString(ARGS_VALUE, value)
                }
            }
        }
    }
}
