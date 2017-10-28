package com.github.kazy1991.prefeditor.routing

import android.support.v4.app.FragmentManager
import com.github.kazy1991.prefeditor.contract.PrefListContract
import com.github.kazy1991.prefeditor.view.dialog.EditDialogFragment

class PrefListRouting : PrefListContract.Routing {

    override fun showEditDialogFragment(prefName: String, key: String, value: String, position: Int, fragmentManager: FragmentManager) {
        EditDialogFragment.show(prefName, key, value, position, fragmentManager)
    }
}