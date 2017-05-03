package com.github.kazy1991.prefeditor.presenter

import com.github.kazy1991.prefeditor.model.PrefItem
import com.github.kazy1991.prefeditor.usecase.PrefEditorUseCase
import com.github.kazy1991.prefeditor.view.PrefEditorView

class PrefEditorPresenter(val view: PrefEditorView, prefEditorUseCase: PrefEditorUseCase) {

    init {
        prefEditorUseCase
                .fetchItemList()
                .subscribe { it ->
                    view.updatePrefNameList(it)
                    it.firstOrNull()?.name?.let {
                        view.replacePrefView(it)
                    }
                }
    }

    fun onItemTapped(item: PrefItem) {
        view.replacePrefView(item.name)
    }

    fun startSearch() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onSearchTextChanged(newText: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
