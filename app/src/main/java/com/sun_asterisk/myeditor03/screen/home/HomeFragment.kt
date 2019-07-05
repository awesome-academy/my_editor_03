package com.sun_asterisk.myeditor03.screen.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.screen.collections.CollectionsFragment
import com.sun_asterisk.myeditor03.screen.home.adapter.PagerAdapter
import com.sun_asterisk.myeditor03.screen.photos.PhotosFragment
import com.sun_asterisk.myeditor03.utils.CommonUtils
import kotlinx.android.synthetic.main.fragment_home.view.tabLayoutHome
import kotlinx.android.synthetic.main.fragment_home.view.viewPagerHome

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        val adapter = PagerAdapter(fragmentManager)
        adapter.addFragment(PhotosFragment.instance(), CommonUtils.PHOTO)
        adapter.addFragment(CollectionsFragment.instance(), CommonUtils.COLLECTION)
        view.viewPagerHome.adapter = adapter
        view.tabLayoutHome.setupWithViewPager(view.viewPagerHome)
    }

    companion object {
        val TAG = HomeFragment::class.java.simpleName
        fun instance(): HomeFragment {
            return HomeFragment()
        }
    }
}
