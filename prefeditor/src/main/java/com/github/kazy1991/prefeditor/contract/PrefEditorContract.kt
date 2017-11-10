package com.github.kazy1991.prefeditor.contract

import android.support.v4.app.FragmentManager
import com.github.kazy1991.prefeditor.entity.SchemaItem
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

interface PrefEditorContract {

    interface View {

        val fragmentManagerProxy: FragmentManager

        val spinnerSelectedItems: Flowable<SchemaItem>

        fun updateSchemaItems(list: List<SchemaItem>)
    }

    interface Interactor {

        val schemaItems: Single<List<SchemaItem>>

        fun convertToNavigationItem(fileName: String): SchemaItem

        fun sortPrefList(list: List<String>): List<String>

    }

    interface Routing {
        fun replacePrefSchema(prefName: String, fragmentManager: FragmentManager)
    }
}