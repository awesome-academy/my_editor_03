package com.sun_asterisk.myeditor03.screen.photodetail

import android.Manifest
import android.app.DownloadManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
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
import com.sun_asterisk.myeditor03.screen.editphoto.EditFragment
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.MyViewModelFactory
import com.sun_asterisk.myeditor03.utils.addFragmentToFragment
import com.sun_asterisk.myeditor03.utils.loadImageUrl
import com.sun_asterisk.myeditor03.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_filter.imageViewFilter
import kotlinx.android.synthetic.main.fragment_photo_detail.buttonDownload
import kotlinx.android.synthetic.main.fragment_photo_detail.buttonEdit
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
    private val DOWNLOAD_REQUEST_CODE = 6969
    private val PHOTO_EXTENSION = ".png"

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
            R.id.buttonDownload -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (activity!!.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            DOWNLOAD_REQUEST_CODE
                        )
                    } else downloadPhoto()
                } else downloadPhoto()
            }
            R.id.buttonEdit -> {
                addFragmentToFragment(
                    R.id.layoutContainer,
                    EditFragment.instance(photo!!),
                    true
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == DOWNLOAD_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadPhoto()
        }
    }

    private fun initView() {
        imageViewBack.setOnClickListener(this)
        buttonDownload.setOnClickListener(this)
        buttonEdit.setOnClickListener(this)
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

    private fun downloadPhoto() {
        val uri = Uri.parse(photo!!.urlImage.regular)
        val manager = activity!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(
            DownloadManager.Request(uri).setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            )
                .setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE
                )
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    photo!!.id + PHOTO_EXTENSION
                )
                .setDescription(photo!!.description)
        )
    }

    companion object {
        fun instance(photo: Photo) = PhotoDetailFragment().apply {
            val args = Bundle()
            args.putParcelable(CommonUtils.ACTION_TYPE, photo)
            arguments = args
        }
    }
}
