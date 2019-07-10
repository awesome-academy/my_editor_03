package com.sun_asterisk.myeditor03.screen.collectiondetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.data.source.local.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.remote.PhotoRemoteDataSource
import com.sun_asterisk.myeditor03.screen.collectiondetail.adapter.CollectionDetailAdapter
import com.sun_asterisk.myeditor03.screen.photodetail.PhotoDetailFragment
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.EndlessScrollListener
import com.sun_asterisk.myeditor03.utils.MyViewModelFactory
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.myeditor03.utils.addFragmentToFragment
import com.sun_asterisk.myeditor03.utils.removeFragment
import com.sun_asterisk.myeditor03.utils.loadCircleImageUrl
import kotlinx.android.synthetic.main.fragment_collection_detail.imageBack
import kotlinx.android.synthetic.main.fragment_collection_detail.imageViewPoster
import kotlinx.android.synthetic.main.fragment_collection_detail.recyclerPhotoByCollection
import kotlinx.android.synthetic.main.fragment_collection_detail.textViewTitle
import kotlinx.android.synthetic.main.fragment_collection_detail.textViewTotalCount

class CollectionsDetailFragment : Fragment(), OnItemRecyclerViewClickListener<Photo>, OnClickListener {
    private val TARGET = "h=128&w=128"
    private val REPLACEMENT = "h=256&w=256"
    private val collectionDetailAdapter: CollectionDetailAdapter by lazy { CollectionDetailAdapter() }
    private lateinit var viewModel: CollectionDetailViewModel
    private var page: Int = 1
    private var collection: Collection? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collection_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        bindView()
        registerLiveData()
    }

    override fun onItemClick(data: Photo) {
        addFragmentToFragment(R.id.layoutContainer, PhotoDetailFragment.instance(data), true)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imageBack -> removeFragment(CollectionsDetailFragment::class.java.simpleName)
        }
    }

    private fun initView() {
        recyclerPhotoByCollection.adapter = collectionDetailAdapter
        collectionDetailAdapter.setOnItemClickListener(this)
        imageBack.setOnClickListener(this)
    }

    private fun initData() {
        val photoRepository =
            PhotoRepository.instance(PhotoLocalDataSource.instance(), PhotoRemoteDataSource.instance())
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(photoRepository))
            .get(CollectionDetailViewModel::class.java)
        collection = arguments!!.getParcelable(CommonUtils.ACTION_TYPE)
        viewModel.getPhotosByCollection(collection!!.id, page)
        recyclerPhotoByCollection.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                viewModel.getPhotosByCollection(collection!!.id, page)
            }
        })
    }

    private fun bindView() {
        textViewTitle.text = collection!!.title
        textViewTotalCount.text = collection!!.totalPhotoToString()
        imageViewPoster.loadCircleImageUrl(collection!!.user.profileImage.LargeImage.replace(TARGET, REPLACEMENT))
    }

    private fun registerLiveData() {
        viewModel.photosByCollectionLiveData.observe(this, Observer {
            collectionDetailAdapter.addItems(it!!)
        })
        viewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(view!!.context, it!!.message, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        fun instance(collection: Collection) = CollectionsDetailFragment().apply {
            val args = Bundle()
            args.putParcelable(CommonUtils.ACTION_TYPE, collection)
            arguments = args
        }
    }
}
