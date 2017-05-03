package com.github.kazy1991.prefeditor.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kazy1991.prefeditor.callback.EditDialogCallback
import com.github.kazy1991.prefeditor.adapter.PrefListAdapter
import com.github.kazy1991.prefeditor.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PrefListFragment : Fragment(), EditDialogCallback {

    val adapter = PrefListAdapter()

    val compositeDisposable = CompositeDisposable()

    val recyclerView by lazy { view?.findViewById(R.id.recycler_view) as RecyclerView }

    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pref_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val prefName = arguments.getString(ARGS_PREF_NAME)
        sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

        Observable.just(sharedPref)
                .doOnSubscribe { adapter.clear() }
                .flatMap { Observable.fromIterable(it.all.toList()) }
                .map { (key, value) -> Pair(key, value.toString()) }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    adapter.list.addAll(it)
                    recyclerView.adapter = adapter
                }
                .let { compositeDisposable.add(it) }

        adapter.valueClickSubject
                .subscribe({ it -> EditDialogFragment.show(it, this) })
                .let { compositeDisposable.add(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    override fun onItemUpdate(position: Int, key: String, newValue: String) {
        sharedPref.edit().putString(key, newValue).apply()
        adapter.updateItem(position, key, newValue)
    }

    companion object {
        val ARGS_PREF_NAME = "args_pref_name"
        fun newInstance(prefName: String): PrefListFragment {
            return PrefListFragment().also {
                it.arguments = Bundle().apply { putString(ARGS_PREF_NAME, prefName) }
            }
        }
    }
}
