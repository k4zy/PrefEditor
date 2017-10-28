package com.github.kazy1991.prefeditor.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kazy1991.prefeditor.R
import com.github.kazy1991.prefeditor.contract.PrefListContract
import com.github.kazy1991.prefeditor.presenter.PrefListPresenter
import com.github.kazy1991.prefeditor.view.recyclerview.adapter.PrefListAdapter
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.fragment_pref_list.*

class PrefListFragment : Fragment(), PrefListContract.View {

    override val fragmentManagerProxy: FragmentManager
        get() = childFragmentManager

    override val valueClickSubject: Flowable<Triple<Int, String, String>>
        get() = adapter.valueClickSubject.toFlowable(BackpressureStrategy.LATEST)

    private val prefName: String
        get() = arguments.getString(ARGS_PREF_NAME)

    private val adapter = PrefListAdapter()

    private lateinit var presenter: PrefListPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pref_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView.adapter = adapter
        presenter = PrefListPresenter(this, context, prefName)
        presenter.onAttach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetach()
    }

    override fun clearList() {
        adapter.clear()
    }

    override fun updateKeyValueList(list: List<Pair<String, String>>) {
        adapter.list.addAll(list)
        adapter.notifyDataSetChanged()
    }

    override fun onItemUpdate(position: Int, key: String, newValue: String) {
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
