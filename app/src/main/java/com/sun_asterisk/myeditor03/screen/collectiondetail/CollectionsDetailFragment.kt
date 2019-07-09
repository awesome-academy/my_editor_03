package com.sun_asterisk.myeditor03.screen.collectiondetail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.data.source.local.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.remote.PhotoRemoteDataSource
import com.sun_asterisk.myeditor03.screen.collectiondetail.adapter.CollectionDetailAdapter
import com.sun_asterisk.myeditor03.screen.collections.CollectionsFragment
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.EndlessScrollListener
import com.sun_asterisk.myeditor03.utils.MyViewModelFactory
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.myeditor03.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_collection_detail.imageBack
import kotlinx.android.synthetic.main.fragment_collection_detail.imageViewPoster
import kotlinx.android.synthetic.main.fragment_collection_detail.recyclerPhotoByCollection
import kotlinx.android.synthetic.main.fragment_collection_detail.textViewTitle
import kotlinx.android.synthetic.main.fragment_collection_detail.textViewTotalCount

class CollectionsDetailFragment : Fragment(), OnItemRecyclerViewClickListener<Photo>, OnClickListener {
    private val TARGET = "h=128&w=128"
    private val REPLACEMENT = "h=256&w=256"
    private lateinit var collectionDetailAdapter: CollectionDetailAdapter
    private lateinit var photoRepository: PhotoRepository
    private lateinit var viewModel: CollectionDetailViewModel
    private var page: Int = 1
    private lateinit var collection: Collection

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_collection_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        registerLiveData()
    }

    override fun onItemClick(data: Photo) {
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imageBack -> removeFragment(CollectionsDetailFragment::class.java.simpleName)
        }
    }

    private fun initView() {
        collectionDetailAdapter = CollectionDetailAdapter()
        collectionDetailAdapter.setOnItemClickListener(this)
        imageBack.setOnClickListener(this)
        recyclerPhotoByCollection.adapter = collectionDetailAdapter
        val local: PhotoLocalDataSource = PhotoLocalDataSource.instance()
        val remote: PhotoRemoteDataSource = PhotoRemoteDataSource.instance()
        photoRepository = PhotoRepository.instance(local, remote)
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(photoRepository))
            .get(CollectionDetailViewModel::class.java)
        collection = arguments!!.getParcelable(CommonUtils.ACTION_TYPE)
        textViewTitle.text = collection.title
        textViewTotalCount.text = collection.totalPhotoToString()
        Glide.with(view!!.context)
            .load(collection.user.profileImage.LargeImage.replace(TARGET, REPLACEMENT))
            .apply(RequestOptions.circleCropTransform())
            .into(imageViewPoster)
        viewModel.getPhotosByCollection(collection.id, page)
        recyclerPhotoByCollection.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                viewModel.getPhotosByCollection(collection.id, page)
            }
        })
    }

    private fun registerLiveData() {
        viewModel.photosByCollectionLiveData.observe(this, Observer {
            collectionDetailAdapter.addItems(it!!)
        })
    }

    companion object {
        fun instance(collection: Collection): CollectionsDetailFragment {
            val collectionDetailFragment = CollectionsDetailFragment()
            val args = Bundle()
            args.putParcelable(CommonUtils.ACTION_TYPE, collection)
            collectionDetailFragment.arguments = args
            return collectionDetailFragment
        }
    }
}
