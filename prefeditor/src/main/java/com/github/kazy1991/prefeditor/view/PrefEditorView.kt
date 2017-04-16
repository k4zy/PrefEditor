package com.github.kazy1991.prefeditor.view

import com.github.kazy1991.prefeditor.NavigationItem

interface PrefEditorView {

    fun updateNavigation(list: List<NavigationItem>)

    fun setupDefaultFragment(prefName: String)

    fun onNavigationItemTapped(item: NavigationItem)
}