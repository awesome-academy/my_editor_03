package com.sun_asterisk.myeditor03.screen.collections

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.data.source.local.PhotoDatabase
import com.sun_asterisk.myeditor03.data.source.local.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.remote.PhotoRemoteDataSource
import com.sun_asterisk.myeditor03.screen.collectiondetail.CollectionsDetailFragment
import com.sun_asterisk.myeditor03.screen.collections.adapter.CollectionAdapter
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.EndlessScrollListener
import com.sun_asterisk.myeditor03.utils.MyViewModelFactory
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.myeditor03.utils.addFragmentToActivity
import kotlinx.android.synthetic.main.fragment_collections.recyclerViewCollections

class CollectionsFragment : Fragment(), OnItemRecyclerViewClickListener<Collection> {
    private val collectionAdapter: CollectionAdapter by lazy { CollectionAdapter() }
    private var page: Int = 1
    private var actionType: Int = 1
    private var query: String = ""
    private lateinit var viewModel: CollectionViewModel

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
        (activity as AppCompatActivity).addFragmentToActivity(
            R.id.layoutContainer,
            CollectionsDetailFragment.instance(data),
            true
        )
    }

    private fun initView() {
        actionType = arguments!!.getInt(CommonUtils.ACTION_TYPE)
        recyclerViewCollections.adapter = collectionAdapter
        collectionAdapter.setOnItemClickListener(this)
    }

    private fun initData() {
        val photoDataBase: PhotoDatabase = PhotoDatabase.instance(context!!.applicationContext)
        val photoRepository =
            PhotoRepository.instance(PhotoLocalDataSource.instance(photoDataBase.photoDAO()), PhotoRemoteDataSource.instance())
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(photoRepository))
            .get(CollectionViewModel::class.java)
        if (actionType == CommonUtils.ACTION_LOAD_PHOTO) {
            viewModel.getCollections(page)
        }
        recyclerViewCollections.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                if (actionType == 1) viewModel.getCollections(page)
                else viewModel.getSearchCollections(query, page)
            }
        })
    }

    private fun registerLiveData() {
        viewModel.collectionsLiveData.observe(this, Observer {
            collectionAdapter.addItems(it!!)
        })
        viewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(view!!.context, it!!.message, Toast.LENGTH_SHORT).show()
        })
    }

    fun searchCollection(query: String) {
        this.query = query
        page = CommonUtils.ACTION_LOAD_PHOTO
        collectionAdapter.clearList()
        viewModel.getSearchCollections(query, page)
    }

    companion object {
        fun instance(action: Int) = CollectionsFragment().apply {
            val args = Bundle()
            args.putInt(CommonUtils.ACTION_TYPE, action)
            arguments = args
        }
    }
}
