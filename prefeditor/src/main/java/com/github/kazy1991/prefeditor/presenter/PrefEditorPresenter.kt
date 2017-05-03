package com.github.kazy1991.prefeditor.presenter

import android.content.Context
import com.github.kazy1991.prefeditor.repository.FileSystemPrefListRepository
import com.github.kazy1991.prefeditor.usecase.PrefEditorUseCase
import com.github.kazy1991.prefeditor.usecase.PrefEditorUseCaseImpl
import com.github.kazy1991.prefeditor.view.PrefEditorView

class PrefEditorPresenter(context: Context, val view: PrefEditorView) {

    val prefEditorUseCase: PrefEditorUseCase = PrefEditorUseCaseImpl(context, FileSystemPrefListRepository(context))

    init {
        prefEditorUseCase.fetchItemList()
                .subscribe { it ->
                    view.updatePrefNameList(it)
                    it.firstOrNull()?.let {
                        view.setupDefaultPrefList(it.name)
                    }
                }
    }
}
