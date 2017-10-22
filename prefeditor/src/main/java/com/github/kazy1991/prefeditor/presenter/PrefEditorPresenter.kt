package com.github.kazy1991.prefeditor.presenter

import com.github.kazy1991.prefeditor.contract.PrefEditorContract
import com.github.kazy1991.prefeditor.entity.NavigationItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File


class PrefEditorPresenter(val view: PrefEditorContract.View, val prefDir: File, val defaultPrefName: String) {

    init {
        Observable.just(prefDir)
                .filter { it.exists() && it.isDirectory }
                .flatMap { Observable.fromIterable(it.list().toList()) }
                .map { it.substring(0, it.lastIndexOf('.')) }
                .toList()
                .map { sortPrefList(it) }
                .flatMapObservable { Observable.fromIterable(it) }
                .map { convertToNavigationItem(it) }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    view.updateNavigation(it)
                    it.firstOrNull()?.let {
                        view.setupDefaultFragment(it.name)
                    }
                }
    }

    fun convertToNavigationItem(fileName: String): NavigationItem {
        when (fileName) {
            defaultPrefName -> {
                return NavigationItem(fileName, "DefaultPref")
            }
            else -> {
                return NavigationItem(fileName)
            }
        }
    }

    fun sortPrefList(list: List<String>): List<String> {
        if (list.contains(defaultPrefName)) {
            return list.filter { it != defaultPrefName }.toMutableList().apply { add(0, defaultPrefName) }
        } else {
            return list
        }
    }
}
