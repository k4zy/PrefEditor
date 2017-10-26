package com.github.kazy1991.prefeditor.view.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kazy1991.prefeditor.R
import com.github.kazy1991.prefeditor.contract.PrefListContract
import com.github.kazy1991.prefeditor.presenter.PrefListPresenter
import com.github.kazy1991.prefeditor.view.recyclerview.adapter.PrefListAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class PrefListFragment : Fragment(), PrefListContract.View {

    override val valueClickSubject: PublishSubject<Triple<Int, String, String>>
        get() = adapter.valueClickSubject

    private val adapter = PrefListAdapter()

    private val compositeDisposable = CompositeDisposable()

    private val recyclerView by lazy { view?.findViewById<RecyclerView>(R.id.recycler_view) }

    // todo: remove
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pref_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val prefName = arguments.getString(ARGS_PREF_NAME)

        recyclerView?.adapter = adapter

        PrefListPresenter(this, context, prefName)

        sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    override fun clearList() {
        adapter.clear()
    }

    override fun updateKeyValueList(list: List<Pair<String, String>>) {
        adapter.list.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onItemUpdate(position: Int, key: String, newValue: String) {
        sharedPref.edit().putString(key, newValue).apply()
        adapter.updateItem(position, key, newValue)
    }

    // todo: Fix bad practice. thinking a good idea...
    override fun fragmentManager(): FragmentManager {
        return childFragmentManager
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
