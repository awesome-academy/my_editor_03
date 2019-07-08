package com.sun_asterisk.myeditor03.screen.photos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.data.source.local.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.remote.PhotoRemoteDataSource
import com.sun_asterisk.myeditor03.screen.photos.adapter.PhotosAdapter
import com.sun_asterisk.myeditor03.utils.EndlessScrollListener
import com.sun_asterisk.myeditor03.utils.MyViewModelFactory
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_photos.recyclerViewPhoto

class PhotosFragment : Fragment(), OnItemRecyclerViewClickListener<Photo> {
    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var photoRepository: PhotoRepository
    private lateinit var viewModel: PhotosViewModel
    private var page: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        registerLiveData()
    }

    override fun onItemClick(data: Photo) {
    }

    private fun initView() {
        photosAdapter = PhotosAdapter()
        photosAdapter.setOnItemClickListener(this)
        recyclerViewPhoto.adapter = photosAdapter
        val local: PhotoLocalDataSource = PhotoLocalDataSource.instance()
        val remote: PhotoRemoteDataSource = PhotoRemoteDataSource.instance()
        photoRepository = PhotoRepository.instance(local, remote)
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(photoRepository))
            .get(PhotosViewModel::class.java)
        viewModel.getPhotos(page)
        recyclerViewPhoto.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                viewModel.getPhotos(page)
            }
        })
    }

    private fun registerLiveData() {
        viewModel.photoLiveData.observe(this, Observer {
            photosAdapter.addItems(it)
        })
    }

    companion object {
        fun instance(): PhotosFragment {
            return PhotosFragment()
        }
    }
}
