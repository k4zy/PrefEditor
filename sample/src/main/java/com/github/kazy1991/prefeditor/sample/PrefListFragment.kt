package com.github.kazy1991.prefeditor.sample

import android.content.Context
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.kazy1991.prefeditor.sample.databinding.FragmentPrefListBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PrefListFragment : Fragment() {

    val adapter = PrefListAdapter()

    val compositeDisposable = CompositeDisposable()

    lateinit var binding: FragmentPrefListBinding

    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentPrefListBinding>(inflater!!, R.layout.fragment_pref_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val prefName = arguments.getString(ARGS_PREF_NAME)
        sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

        Observable.just(sharedPref)
                .flatMap { Observable.fromIterable(it.all.toList()) }
                .map { (key, value) -> Pair(key, value.toString()) }
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it ->
                    adapter.list.addAll(it)
                    binding.recyclerView.adapter = adapter
                }
                .let { compositeDisposable.add(it) }

        adapter.valueClickSubject
                .subscribe({ it ->
                    EditDialogFragment.newInstance(it.first, it.second).show(fragmentManager, "dialog")
                })
                .let { compositeDisposable.add(it) }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
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
