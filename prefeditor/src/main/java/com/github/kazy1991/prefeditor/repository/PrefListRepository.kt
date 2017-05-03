package com.github.kazy1991.prefeditor.repository

import io.reactivex.Single

interface PrefListRepository {

    fun fetch(): Single<List<String>>

}