package com.sun_asterisk.myeditor03.screen.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.screen.collections.CollectionsFragment
import com.sun_asterisk.myeditor03.screen.home.adapter.PagerAdapter
import com.sun_asterisk.myeditor03.screen.photos.PhotosFragment
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_search.imageBack
import kotlinx.android.synthetic.main.fragment_search.tabLayoutSearch
import kotlinx.android.synthetic.main.fragment_search.viewPager

class SearchFragment : Fragment(), OnClickListener {
    private val photosFragment: PhotosFragment by lazy { PhotosFragment.instance() }
    private val collectionsFragment: CollectionsFragment by lazy { CollectionsFragment.instance() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.imageBack) {
            removeFragment(SearchFragment::class.java.simpleName)
        }
    }

    private fun initView() {
        val adapter = PagerAdapter(fragmentManager)
        adapter.addFragment(photosFragment, CommonUtils.PHOTO)
        adapter.addFragment(collectionsFragment, CommonUtils.COLLECTION)
        viewPager.adapter = adapter
        tabLayoutSearch.setupWithViewPager(viewPager)
        imageBack.setOnClickListener(this)
    }

    companion object {
        fun instance() = SearchFragment()
    }
}
