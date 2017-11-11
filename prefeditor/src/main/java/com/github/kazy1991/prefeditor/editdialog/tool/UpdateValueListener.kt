package com.github.kazy1991.prefeditor.editdialog.tool

import android.content.DialogInterface
import io.reactivex.subjects.PublishSubject

class UpdateValueListener : DialogInterface.OnClickListener {
    val publishSubject = PublishSubject.create<Int>()!!

    override fun onClick(dialog: DialogInterface?, which: Int) {
        publishSubject.onNext(which)
    }
}