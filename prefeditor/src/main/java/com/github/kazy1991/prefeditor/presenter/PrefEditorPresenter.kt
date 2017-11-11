package com.github.kazy1991.prefeditor.presenter

import android.content.Context
import com.github.kazy1991.prefeditor.base.Presenter
import com.github.kazy1991.prefeditor.contract.PrefEditorContract
import com.github.kazy1991.prefeditor.interactor.PrefEditorInteractor
import com.github.kazy1991.prefeditor.routing.PrefEditorRouting
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PrefEditorPresenter(val view: PrefEditorContract.View, context: Context) : Presenter {

    private val interactor = PrefEditorInteractor(context)

    private val compositeDisposable = CompositeDisposable()

    private val routing = PrefEditorRouting()

    override fun onAttach() {
        interactor.schemaItems
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    view.updateSchemaItems(it)
                    it.firstOrNull()?.let {
                        routing.replacePrefSchema(it.name, view.fragmentManagerProxy)
                    }
                }
                .let { compositeDisposable.add(it) }

        view.spinnerSelectedItems
                .subscribe { routing.replacePrefSchema(it.name, view.fragmentManagerProxy) }
                .let { compositeDisposable.add(it) }
    }

    override fun onDetach() {
        compositeDisposable.clear()
    }

}
