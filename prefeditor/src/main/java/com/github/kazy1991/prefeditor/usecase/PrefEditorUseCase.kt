package com.github.kazy1991.prefeditor.usecase

import com.github.kazy1991.prefeditor.PrefItem
import io.reactivex.Single

interface PrefEditorUseCase {

    fun fetchItemList(): Single<List<PrefItem>>

}