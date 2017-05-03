package com.github.kazy1991.prefeditor.usecase

import android.content.Context
import com.github.kazy1991.prefeditor.PrefItem
import com.github.kazy1991.prefeditor.repository.PrefListRepository
import io.reactivex.Observable
import io.reactivex.Single

class PrefEditorUseCaseImpl(val context: Context, val prefListRepository: PrefListRepository) : PrefEditorUseCase {

    val defaultPrefName = "${context.applicationContext.packageName}_preferences"

    override fun fetchItemList(): Single<List<PrefItem>> {
        return prefListRepository.fetch()
                .map { sortPrefList(it) }
                .flatMapObservable { Observable.fromIterable(it) }
                .map { convertToPrefItem(it) }
                .toList()
    }

    fun convertToPrefItem(fileName: String): PrefItem {
        when (fileName) {
            defaultPrefName -> {
                return PrefItem(fileName, "DefaultPref")
            }
            else -> {
                return PrefItem(fileName)
            }
        }
    }

    fun sortPrefList(list: List<String>): List<String> {
        if (list.contains(defaultPrefName)) {
            return list
                    .filter { it != defaultPrefName }
                    .toMutableList()
                    .apply { add(0, defaultPrefName) }
        } else {
            return list
        }
    }
}