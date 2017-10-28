package com.github.kazy1991.prefeditor.contract

import android.support.v4.app.FragmentManager
import com.github.kazy1991.prefeditor.view.dialog.EditDialogFragment
import io.reactivex.Flowable
import io.reactivex.Single

interface PrefListContract {

    interface View : EditDialogFragment.Companion.Callback {

        val valueClickSubject: Flowable<Triple<Int, String, String>>

        val fragmentManagerProxy: FragmentManager

        fun updateKeyValueList(list: List<Pair<String, String>>)

        fun clearList()

    }

    interface Interactor {

        fun keyValuePairs(): Single<List<Pair<String, String>>>

    }

    interface Routing {

        fun showEditDialogFragment(prefName: String, key: String, value: String, position: Int, fragmentManager: FragmentManager)

    }

    interface ViewHelper {

    }
}