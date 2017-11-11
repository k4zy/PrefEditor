package com.github.kazy1991.prefeditor.editdialog.view


import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Spinner
import com.github.kazy1991.prefeditor.R
import com.github.kazy1991.prefeditor.editdialog.EditDialogContract
import com.github.kazy1991.prefeditor.editdialog.presenter.EditDialogPresenter
import com.github.kazy1991.prefeditor.editdialog.tool.UpdateValueListener
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class EditDialogFragment : DialogFragment(), EditDialogContract.View {

    override val inputValue: String
        get() = editText.text.toString()

    override val booleanValue: String
        get() = booleanSpinner.selectedItem.toString()

    override val submitButtonStream: Flowable<Int>
        get() = updateValueListener.publishSubject.toFlowable(BackpressureStrategy.LATEST)

    private val updateValueListener = UpdateValueListener()

    private lateinit var presenter: EditDialogPresenter

    private lateinit var editText: EditText

    private lateinit var booleanSpinner: Spinner

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        presenter = EditDialogPresenter(context, this, arguments).apply { onAttach() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetach()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context, R.style.CustomAlertDialogStyle).apply {
            val view = View.inflate(context, R.layout.dialog_custom_view, null)
            editText = view.findViewById(R.id.value_edit_text)
            booleanSpinner = view.findViewById(R.id.boolean_spinner)
            setView(view)
            setPositiveButton("上書き", updateValueListener)
            setNegativeButton("キャンセル", null)
        }.create()
    }

    override fun setDialogTitle(title: String) {
        dialog.setTitle(title)
    }

    override fun setupInputField(inputType: Int, value: String) {
        booleanSpinner.visibility = View.GONE
        editText.visibility = View.VISIBLE
        editText.inputType = inputType
        editText.append(value)
    }

    override fun setupBooleanSpinner(selection: Int) {
        booleanSpinner.visibility = View.VISIBLE
        editText.visibility = View.GONE
        booleanSpinner.setSelection(selection)
    }

    override fun onItemUpdate(position: Int, key: String, newValue: String) {
        if (parentFragment is Callback) {
            (parentFragment as Callback).onItemUpdate(position, key, newValue)
        }
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
