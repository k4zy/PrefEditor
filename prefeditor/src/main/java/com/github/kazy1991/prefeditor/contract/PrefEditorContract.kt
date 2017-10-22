package com.github.kazy1991.prefeditor.contract

import com.github.kazy1991.prefeditor.entity.NavigationItem

interface PrefEditorContract {

    interface View {

        fun updateNavigation(list: List<NavigationItem>)

        fun setupDefaultFragment(prefName: String)

        fun onNavigationItemTapped(item: NavigationItem)
    }

    interface Interactor {

    }

    interface Routing {

    }

    interface ViewHelper {

    }
}