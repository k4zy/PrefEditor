package com.github.kazy1991.prefeditor.repository

import android.content.Context
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

class FileSystemPrefListRepository(context: Context) : PrefListRepository {

    val prefDirPath = "shared_prefs"

    val prefDir: File = File(context.applicationInfo.dataDir, prefDirPath)

    override fun fetch(): Single<List<String>> {
        return Observable.just(prefDir)
                .filter { it.exists() && it.isDirectory }
                .flatMap { Observable.fromIterable(it.list().toList()) }
                .map { it.substring(0, it.lastIndexOf('.')) }
                .toList()
    }
}