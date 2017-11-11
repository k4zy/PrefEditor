package com.github.kazy1991.prefeditor.interactor

import android.content.Context
import android.support.annotation.VisibleForTesting
import com.github.kazy1991.prefeditor.contract.PrefEditorContract
import com.github.kazy1991.prefeditor.entity.SchemaItem
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

class PrefEditorInteractor(context: Context) : PrefEditorContract.Interactor {

    private val prefDir = File(context.applicationInfo.dataDir, "shared_prefs")

    @VisibleForTesting
    val defaultPrefName = "${context.applicationInfo.packageName}_preferences"

    @VisibleForTesting
    val defaultPrefAnnotatedName = "DefaultPreference"

    override val schemaItems: Single<List<SchemaItem>>
        get() = Observable.just(prefDir)
                .filter { it.exists() && it.isDirectory }
                .flatMap { Observable.fromIterable(it.list().toList()) }
                .map { it.substring(0, it.lastIndexOf('.')) }
                .toList()
                .map { sortPrefList(it) }
                .flatMapObservable { Observable.fromIterable(it) }
                .map { convertToNavigationItem(it) }
                .toList()

    override fun convertToNavigationItem(fileName: String): SchemaItem {
        return when (fileName) {
            defaultPrefName -> {
                SchemaItem(fileName, defaultPrefAnnotatedName)
            }
            else -> {
                SchemaItem(fileName)
            }
        }
    }

    override fun sortPrefList(list: List<String>): List<String> {
        return if (list.contains(defaultPrefName)) {
            list.filter { it != defaultPrefName }.toMutableList().apply { add(0, defaultPrefName) }
        } else {
            list
        }
    }

}