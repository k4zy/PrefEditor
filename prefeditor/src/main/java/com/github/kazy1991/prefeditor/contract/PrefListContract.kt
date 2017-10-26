package com.github.kazy1991.prefeditor.contract

import android.support.v4.app.FragmentManager
import com.github.kazy1991.prefeditor.view.dialog.EditDialogFragment
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface PrefListContract {

    interface View : EditDialogFragment.Callback {

        val valueClickSubject: PublishSubject<Triple<Int, String, String>>

        fun fragmentManager(): FragmentManager

        fun updateKeyValueList(list: List<Pair<String, String>>)

        fun clearList()

    }

    interface Interactor {

        fun keyValuePairs(): Single<List<Pair<String, String>>>

    }

    interface Routing {

        fun showEditDialogFragment(item: Triple<Int, String, String>, fragmentManager: FragmentManager)

    }

    interface ViewHelper {

    }
}