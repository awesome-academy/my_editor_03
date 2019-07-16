package com.sun_asterisk.myeditor03.screen.photos

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
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.data.source.local.PhotoDatabase
import com.sun_asterisk.myeditor03.data.source.local.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.remote.PhotoRemoteDataSource
import com.sun_asterisk.myeditor03.screen.photodetail.PhotoDetailFragment
import com.sun_asterisk.myeditor03.screen.photos.adapter.PhotosAdapter
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.EndlessScrollListener
import com.sun_asterisk.myeditor03.utils.MyViewModelFactory
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.myeditor03.utils.addFragmentToActivity
import kotlinx.android.synthetic.main.fragment_photos.recyclerViewPhoto

class PhotosFragment : Fragment(), OnItemRecyclerViewClickListener<Photo> {
    private val photosAdapter: PhotosAdapter by lazy { PhotosAdapter() }
    private var page: Int = 1
    private var actionType: Int = 1
    private lateinit var viewModel: PhotosViewModel
    private var query: String = ""

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
        (activity as AppCompatActivity).addFragmentToActivity(
            R.id.layoutContainer,
            PhotoDetailFragment.instance(data),
            true
        )
    }

    private fun initView() {
        actionType = arguments!!.getInt(CommonUtils.ACTION_TYPE)
        recyclerViewPhoto.adapter = photosAdapter
        photosAdapter.setOnItemClickListener(this)
    }

    private fun initData() {
        val photoDataBase: PhotoDatabase = PhotoDatabase.instance(context!!.applicationContext)
        val photoRepository =
            PhotoRepository.instance(
                PhotoLocalDataSource.instance(photoDataBase.photoDAO()),
                PhotoRemoteDataSource.instance()
            )
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(photoRepository))
            .get(PhotosViewModel::class.java)
        if (actionType == CommonUtils.ACTION_LOAD_PHOTO) {
            viewModel.getPhotos(page)
        }
        recyclerViewPhoto.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                page++
                if (actionType == CommonUtils.ACTION_LOAD_PHOTO) viewModel.getPhotos(page)
                else viewModel.getSearchPhotos(query, page)
            }
        })
    }

    private fun registerLiveData() {
        viewModel.photoLiveData.observe(this, Observer {
            photosAdapter.addItems(it!!)
        })
        viewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(view!!.context, it!!.message, Toast.LENGTH_SHORT).show()
        })
    }

    fun searchPhoto(query: String) {
        this.query = query
        page = CommonUtils.ACTION_LOAD_PHOTO
        photosAdapter.clearList()
        viewModel.getSearchPhotos(query, page)
    }

    companion object {
        fun instance(action: Int) = PhotosFragment().apply {
            val args = Bundle()
            args.putInt(CommonUtils.ACTION_TYPE, action)
            arguments = args
        }
    }
}
