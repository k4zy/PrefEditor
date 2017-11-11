package com.github.kazy1991.prefeditor.routing

import android.support.v4.app.FragmentManager
import com.github.kazy1991.prefeditor.R
import com.github.kazy1991.prefeditor.contract.PrefEditorContract
import com.github.kazy1991.prefeditor.view.fragment.PrefListFragment

class PrefEditorRouting : PrefEditorContract.Routing {

    override fun replacePrefSchema(prefName: String, fragmentManager: FragmentManager) {
        val fragment = PrefListFragment.newInstance(prefName)
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit()
    }
}