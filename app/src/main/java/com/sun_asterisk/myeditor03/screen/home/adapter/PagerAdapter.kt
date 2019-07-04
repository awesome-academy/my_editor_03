package com.sun_asterisk.myeditor03.screen.home.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fragments = ArrayList<Fragment>()
    private val fragmentsTitle = ArrayList<String>()

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentsTitle[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentsTitle.add(title)
    }
}
