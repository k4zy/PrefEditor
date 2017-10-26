package com.github.kazy1991.prefeditor.routing

import android.support.v4.app.FragmentManager
import com.github.kazy1991.prefeditor.contract.PrefListContract
import com.github.kazy1991.prefeditor.view.dialog.EditDialogFragment

class PrefListRouting : PrefListContract.Routing {

    override fun showEditDialogFragment(item: Triple<Int, String, String>, fragmentManager: FragmentManager) {
        EditDialogFragment.show(item, fragmentManager)
    }
}