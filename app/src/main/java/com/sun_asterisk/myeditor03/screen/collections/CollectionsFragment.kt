package com.sun_asterisk.myeditor03.screen.collections

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.data.source.local.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.remote.PhotoRemoteDataSource
import com.sun_asterisk.myeditor03.screen.collections.adapter.CollectionAdapter
import com.sun_asterisk.myeditor03.utils.EndlessScrollListener
import com.sun_asterisk.myeditor03.utils.MyViewModelFactory
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_collections.recyclerViewCollections

class CollectionsFragment : Fragment(), OnItemRecyclerViewClickListener<Collection> {
    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var photoRepository: PhotoRepository
    private lateinit var viewModel: CollectionViewModel
    private var page: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collections, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        registerLiveData()
    }

    override fun onItemClick(data: Collection) {
    }

    private fun initView() {
        collectionAdapter = CollectionAdapter()
        collectionAdapter.setOnItemClickListener(this)
        recyclerViewCollections.adapter = collectionAdapter
        val local: PhotoLocalDataSource = PhotoLocalDataSource.instance()
        val remote: PhotoRemoteDataSource = PhotoRemoteDataSource.instance()
        photoRepository = PhotoRepository.instance(local, remote)
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(photoRepository))
            .get(CollectionViewModel::class.java)
        viewModel.getCollections(page)
        recyclerViewCollections.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                viewModel.getCollections(page)
            }
        })
    }

    private fun registerLiveData() {
        viewModel.collectionsLiveData.observe(this, Observer {
            collectionAdapter.addItems(it)
        })
    }

    companion object {
        fun instance(): CollectionsFragment {
            return CollectionsFragment()
        }
    }
}
