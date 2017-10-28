package com.github.kazy1991.prefeditor.presenter

import android.content.Context
import com.github.kazy1991.prefeditor.base.Presenter
import com.github.kazy1991.prefeditor.contract.PrefListContract
import com.github.kazy1991.prefeditor.interactor.PrefListInteractor
import com.github.kazy1991.prefeditor.routing.PrefListRouting
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PrefListPresenter(val view: PrefListContract.View, val context: Context, private val prefName: String) : Presenter {

    private val interactor = PrefListInteractor(context, prefName)
    private val routing = PrefListRouting()
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach() {
        interactor.keyValuePairs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { view.clearList() }
                .subscribe { list, _ ->
                    view.updateKeyValueList(list)
                }
                .let { compositeDisposable.add(it) }

        view.valueClickSubject
                .subscribe({ it -> routing.showEditDialogFragment(prefName, it.second, it.third, it.first, view.fragmentManagerProxy) })
                .let { compositeDisposable.add(it) }
    }

    override fun onDetach() {
        compositeDisposable.clear()
    }
}