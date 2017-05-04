package com.github.kazy1991.prefeditor.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import com.github.kazy1991.prefeditor.R
import com.github.kazy1991.prefeditor.adapter.SchemaSpinnerAdapter
import com.github.kazy1991.prefeditor.callback.SpinnerWrapperCallback
import com.github.kazy1991.prefeditor.model.PrefItem
import com.github.kazy1991.prefeditor.presenter.PrefEditorPresenter
import com.github.kazy1991.prefeditor.repository.FileSystemPrefListRepository
import com.github.kazy1991.prefeditor.usecase.PrefEditorUseCaseImpl

class PrefEditorFragment : Fragment(), PrefEditorView {

    val spinner by lazy { view?.findViewById(R.id.spinner) as Spinner }

    var spinnerAdapter: SchemaSpinnerAdapter? = null

    lateinit var presenter: PrefEditorPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pref_editor, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // todo: DI
        val prefListRepository = FileSystemPrefListRepository(context)
        val prefEditorUseCase = PrefEditorUseCaseImpl(context, prefListRepository)
        presenter = PrefEditorPresenter(this, prefEditorUseCase)
    }

    private fun replaceFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }

    override fun updatePrefNameList(list: List<PrefItem>) {
        spinnerAdapter = SchemaSpinnerAdapter(context, list)
        spinner.adapter = spinnerAdapter
        spinnerAdapter?.notifyDataSetChanged()
        spinner.onItemSelectedListener = object : SpinnerWrapperCallback {
            override fun onItemSelected(item: PrefItem) {
                presenter.onItemTapped(item)
            }
        }
    }

    override fun replacePrefView(prefName: String) {
        replaceFragment(PrefListFragment.newInstance(prefName))
    }

    companion object {
        fun newInstance(): Fragment {
            return PrefEditorFragment()
        }
    }
}