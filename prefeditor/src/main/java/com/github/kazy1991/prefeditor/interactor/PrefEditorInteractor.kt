package com.github.kazy1991.prefeditor.interactor

import android.content.Context
import com.github.kazy1991.prefeditor.contract.PrefEditorContract
import com.github.kazy1991.prefeditor.entity.SchemaItem
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

class PrefEditorInteractor(context: Context) : PrefEditorContract.Interactor {

    private val prefDir = File(context.applicationInfo.dataDir, "shared_prefs")
    private val defaultPrefName = "${context.applicationInfo.packageName}_preferences"

    fun navigationItems(): Single<List<SchemaItem>> {
        return Observable.just(prefDir)
                .filter { it.exists() && it.isDirectory }
                .flatMap { Observable.fromIterable(it.list().toList()) }
                .map { it.substring(0, it.lastIndexOf('.')) }
                .toList()
                .map { sortPrefList(it) }
                .flatMapObservable { Observable.fromIterable(it) }
                .map { convertToNavigationItem(it) }
                .toList()
    }

    fun convertToNavigationItem(fileName: String): SchemaItem {
        return when (fileName) {
            defaultPrefName -> {
                SchemaItem(fileName, "DefaultPref")
            }
            else -> {
                SchemaItem(fileName)
            }
        }
    }

    fun sortPrefList(list: List<String>): List<String> {
        return if (list.contains(defaultPrefName)) {
            list.filter { it != defaultPrefName }.toMutableList().apply { add(0, defaultPrefName) }
        } else {
            list
        }
    }

}