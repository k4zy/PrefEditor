package com.github.kazy1991.prefeditor.presenter

import android.content.Context
import com.github.kazy1991.prefeditor.contract.PrefEditorContract
import com.github.kazy1991.prefeditor.interactor.PrefEditorInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PrefEditorPresenter(val view: PrefEditorContract.View, context: Context) {

    private val interactor = PrefEditorInteractor(context)

    init {
        interactor.schemaItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    view.updateNavigation(it)
                    it.firstOrNull()?.let {
                        view.setupDefaultFragment(it.name)
                    }
                }
        view.spinnerSelectedItems
                .subscribe { view.onSchemaItemTapped(it) }
    }


}
