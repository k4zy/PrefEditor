package com.github.kazy1991.prefeditor

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class PrefListPagerAdapter(val list: List<String>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        val prefName = list[position]
        val fragment = PrefListFragment.newInstance(prefName);
        return fragment
    }

    override fun getCount(): Int {
        return list.size
    }
}
