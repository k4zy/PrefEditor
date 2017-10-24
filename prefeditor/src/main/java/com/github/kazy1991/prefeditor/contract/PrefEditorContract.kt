package com.github.kazy1991.prefeditor.contract

import com.github.kazy1991.prefeditor.entity.SchemaItem
import io.reactivex.subjects.PublishSubject

interface PrefEditorContract {

    interface View {

        val spinnerSelectedItems: PublishSubject<SchemaItem>

        fun updateNavigation(list: List<SchemaItem>)

        fun setupDefaultFragment(prefName: String)

        fun onSchemaItemTapped(item: SchemaItem)
    }

    interface Interactor {

    }

    interface Routing {

    }

    interface ViewHelper {

    }
}