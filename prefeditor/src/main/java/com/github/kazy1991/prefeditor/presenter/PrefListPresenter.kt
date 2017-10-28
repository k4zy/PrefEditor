package com.github.kazy1991.prefeditor.presenter

import android.content.Context
import com.github.kazy1991.prefeditor.contract.PrefListContract
import com.github.kazy1991.prefeditor.interactor.PrefListInteractor
import com.github.kazy1991.prefeditor.routing.PrefListRouting
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PrefListPresenter(val view: PrefListContract.View, val context: Context, val prefName: String) {

    private val interactor = PrefListInteractor(context, prefName)
    private val routing = PrefListRouting()

    init {
        interactor.keyValuePairs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { view.clearList() }
                .subscribe { list, _ ->
                    view.updateKeyValueList(list)
                }

        view.valueClickSubject
                .subscribe({ it -> routing.showEditDialogFragment(prefName, it.second, it.third, it.first, view.fragmentManagerProxy) })
    }
}