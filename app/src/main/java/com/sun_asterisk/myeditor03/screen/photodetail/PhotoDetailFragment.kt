package com.sun_asterisk.myeditor03.screen.photodetail

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
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.PhotoRepository
import com.sun_asterisk.myeditor03.data.source.local.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.remote.PhotoRemoteDataSource
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.MyViewModelFactory
import com.sun_asterisk.myeditor03.utils.loadImageUrl
import com.sun_asterisk.myeditor03.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_photo_detail.imagePhotoDetail
import kotlinx.android.synthetic.main.fragment_photo_detail.imageViewBack
import kotlinx.android.synthetic.main.fragment_photo_detail.textViewDownload
import kotlinx.android.synthetic.main.fragment_photo_detail.textViewLike
import kotlinx.android.synthetic.main.fragment_photo_detail.textViewLocation
import kotlinx.android.synthetic.main.fragment_photo_detail.textViewUserName
import kotlinx.android.synthetic.main.fragment_photo_detail.textViewView

class PhotoDetailFragment : Fragment(), OnClickListener {
    private lateinit var viewModel: PhotoDetailViewModel
    private var photo: Photo? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        registerLiveData()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imageViewBack -> removeFragment(PhotoDetailFragment::class.java.simpleName)
        }
    }

    private fun initView() {
        imageViewBack.setOnClickListener(this)
    }

    private fun initData() {
        val photoRepository =
            PhotoRepository.instance(PhotoLocalDataSource.instance(), PhotoRemoteDataSource.instance())
        viewModel = ViewModelProviders.of(this, MyViewModelFactory(photoRepository))
            .get(PhotoDetailViewModel::class.java)
        photo = arguments!!.getParcelable(CommonUtils.ACTION_TYPE)
        viewModel.getPhotoDetail(photo!!.id)
    }

    private fun registerLiveData() {
        viewModel.photoDetailLiveData.observe(this, Observer {
            bindView(it!!)
        })
        viewModel.errorLiveData.observe(this, Observer {
            Toast.makeText(view!!.context, it!!.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun bindView(photo: Photo) {
        textViewLike.text = photo.likeToString()
        textViewDownload.text = photo.downloadToString()
        textViewUserName.text = photo.user.name
        textViewView.text = photo.viewToSting()
        imagePhotoDetail.loadImageUrl(photo.urlImage.regular)
        textViewLocation.text = photo.location?.title
    }

    companion object {
        fun instance(photo: Photo) = PhotoDetailFragment().apply {
            val args = Bundle()
            args.putParcelable(CommonUtils.ACTION_TYPE, photo)
            arguments = args
        }
    }
}
