package com.sun_asterisk.myeditor03.screen.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sun_asterisk.myeditor03.R.layout
import com.sun_asterisk.myeditor03.screen.collections.CollectionsFragment
import com.sun_asterisk.myeditor03.screen.home.adapter.PagerAdapter
import com.sun_asterisk.myeditor03.screen.photos.PhotosFragment
import com.sun_asterisk.myeditor03.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_main.tabLayoutHome
import kotlinx.android.synthetic.main.activity_main.viewPagerHome

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        innitView()
    }

    private fun innitView() {
        val pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(PhotosFragment.getInstance(), CommonUtils.Genres.PHOTO)
        pagerAdapter.addFragment(CollectionsFragment.getInstance(), CommonUtils.Genres.COLLECTION)
        viewPagerHome.adapter = pagerAdapter
        tabLayoutHome.setupWithViewPager(viewPagerHome)
    }
}
