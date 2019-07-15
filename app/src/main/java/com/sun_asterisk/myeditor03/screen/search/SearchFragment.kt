package com.sun_asterisk.myeditor03.screen.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.screen.collections.CollectionsFragment
import com.sun_asterisk.myeditor03.screen.home.adapter.PagerAdapter
import com.sun_asterisk.myeditor03.screen.photos.PhotosFragment
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.hideKeyboard
import com.sun_asterisk.myeditor03.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_search.editTextSearch
import kotlinx.android.synthetic.main.fragment_search.groupSearchHistory
import kotlinx.android.synthetic.main.fragment_search.imageBack
import kotlinx.android.synthetic.main.fragment_search.tabLayoutSearch
import kotlinx.android.synthetic.main.fragment_search.viewPager

class SearchFragment : Fragment(), OnClickListener, TextView.OnEditorActionListener {
    private val photosFragment: PhotosFragment by lazy { PhotosFragment.instance(CommonUtils.ACTION_LOAD_SEARCH) }
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

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
            || actionId == EditorInfo.IME_ACTION_DONE
            || event?.action == KeyEvent.ACTION_DOWN
            && editTextSearch.text.toString().trim().isNotEmpty()
        ) {
            hideKeyboard()
            photosFragment.searchPhoto(editTextSearch.text.toString())
            groupSearchHistory.visibility = View.GONE
            viewPager.visibility = View.VISIBLE
            return true
        }
        return false
    }

    private fun initView() {
        val adapter = PagerAdapter(childFragmentManager)
        adapter.addFragment(photosFragment, CommonUtils.PHOTO)
        adapter.addFragment(collectionsFragment, CommonUtils.COLLECTION)
        viewPager.adapter = adapter
        tabLayoutSearch.setupWithViewPager(viewPager)
        imageBack.setOnClickListener(this)
        editTextSearch.setOnEditorActionListener(this)
    }

    companion object {
        fun instance() = SearchFragment()
    }
}
