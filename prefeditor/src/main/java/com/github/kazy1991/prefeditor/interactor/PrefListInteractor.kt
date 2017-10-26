package com.github.kazy1991.prefeditor.interactor

import android.content.Context
import com.github.kazy1991.prefeditor.contract.PrefListContract
import io.reactivex.Observable
import io.reactivex.Single

class PrefListInteractor(val context: Context, val prefName: String) : PrefListContract.Interactor {

    override fun keyValuePairs(): Single<List<Pair<String, String>>> {
        val sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
        return Observable.just(sharedPref)
                .flatMap { Observable.fromIterable(it.all.toList()) }
                .map { (key, value) -> Pair(key, value.toString()) }
                .toList()
    }
}