package com.github.kazy1991.prefeditor.editdialog.presenter

import android.content.Context
import android.os.Bundle
import com.github.kazy1991.prefeditor.base.Presenter
import com.github.kazy1991.prefeditor.editdialog.EditDialogContract
import com.github.kazy1991.prefeditor.editdialog.interactor.EditDialogInteractor
import com.github.kazy1991.prefeditor.editdialog.interactor.EditDialogInteractor.ValueType.*
import io.reactivex.disposables.CompositeDisposable


class EditDialogPresenter(val context: Context, val view: EditDialogContract.View, arguments: Bundle) : Presenter {

    private val interactor = EditDialogInteractor(context, arguments)

    private val compositeDisposable = CompositeDisposable()

    override fun onAttach() {
        view.setDialogTitle("${interactor.key} を更新する")
        when (interactor.valueType) {
            BOOLEAN -> {
                view.setupBooleanSpinner(interactor.selection)
            }
            NUMBER, TEXT -> {
                view.setupInputField(interactor.inputType, interactor.value)
            }
        }
        view.submitButtonStream
                .map {
                    when (interactor.valueType) {
                        BOOLEAN -> {
                            view.booleanValue
                        }
                        NUMBER, TEXT -> {
                            view.inputValue
                        }
                    }
                }
                .subscribe {
                    interactor.updatePreference(it)
                    view.onItemUpdate(interactor.position, interactor.key, it)
                }
                .let {
                    compositeDisposable.add(it)
                }
    }

    override fun onDetach() {
        compositeDisposable.clear()
    }
}