package com.sun_asterisk.myeditor03.screen.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Search
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.data.source.local.PhotoDatabase
import com.sun_asterisk.myeditor03.data.source.local.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.remote.PhotoRemoteDataSource
import com.sun_asterisk.myeditor03.screen.collections.CollectionsFragment
import com.sun_asterisk.myeditor03.screen.home.adapter.PagerAdapter
import com.sun_asterisk.myeditor03.screen.photos.PhotosFragment
import com.sun_asterisk.myeditor03.screen.search.adapter.SearchAdapter
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.MyViewModelFactory
import com.sun_asterisk.myeditor03.utils.OnItemSearchClickListener
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.myeditor03.utils.hideKeyboard
import com.sun_asterisk.myeditor03.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_search.editTextSearch
import kotlinx.android.synthetic.main.fragment_search.groupSearchHistory
import kotlinx.android.synthetic.main.fragment_search.imageBack
import kotlinx.android.synthetic.main.fragment_search.recyclerViewSearchHistory
import kotlinx.android.synthetic.main.fragment_search.tabLayoutSearch
import kotlinx.android.synthetic.main.fragment_search.viewPager
import java.util.Collections

class SearchFragment : Fragment(), OnClickListener, TextView.OnEditorActionListener,
    OnItemRecyclerViewClickListener<Search>, OnItemSearchClickListener {

    private lateinit var viewModel: SearchViewModel
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }
    private val photosFragment: PhotosFragment by lazy { PhotosFragment.instance(CommonUtils.ACTION_LOAD_SEARCH) }
    private val collectionsFragment: CollectionsFragment by lazy { CollectionsFragment.instance(CommonUtils.ACTION_LOAD_SEARCH) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        registerLiveData()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.imageBack) {
            removeFragment(SearchFragment::class.java.simpleName)
        }
    }

    override fun onItemClick(data: Search) {
        searchData(data.title!!)
    }

    override fun onItemSearchClick(search: Search) {
        viewModel.removeSearchHistory(search)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
            || actionId == EditorInfo.IME_ACTION_DONE
            || event?.action == KeyEvent.ACTION_DOWN
            && editTextSearch.text.toString().trim().isNotEmpty()
        ) {
            hideKeyboard()
            searchData(editTextSearch.text.toString())
            viewModel.addSearchHistory(Search(null, editTextSearch.text.toString()))
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
        recyclerViewSearchHistory.adapter = searchAdapter
        searchAdapter.setListener(this)
        searchAdapter.setOnItemClickListener(this)
        imageBack.setOnClickListener(this)
        editTextSearch.setOnEditorActionListener(this)
    }

    private fun initData() {
        val photoDataBase: PhotoDatabase = PhotoDatabase.instance(context!!.applicationContext)
        val photoRepository =
            PhotoRepository.instance(
                PhotoLocalDataSource.instance(photoDataBase.photoDAO()),
                PhotoRemoteDataSource.instance()
            )
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(photoRepository))
            .get(SearchViewModel::class.java)
        viewModel.getSearchHistory()
    }

    private fun registerLiveData() {
        viewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(view!!.context, it!!.message, Toast.LENGTH_SHORT).show()
        })
        viewModel.searHistoryLiveData.observe(this, Observer {
            Collections.reverse(it)
            searchAdapter.replaceItem(it!!)
        })
    }

    private fun searchData(query: String) {
        photosFragment.searchPhoto(query)
        collectionsFragment.searchCollection(query)
        groupSearchHistory.visibility = View.GONE
        viewPager.visibility = View.VISIBLE
    }

    companion object {
        fun instance() = SearchFragment()
    }
}
