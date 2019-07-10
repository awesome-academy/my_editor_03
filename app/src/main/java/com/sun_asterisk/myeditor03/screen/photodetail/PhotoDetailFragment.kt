package com.sun_asterisk.myeditor03.screen.photodetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.utils.CommonUtils

class PhotoDetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun instance(photo: Photo) = PhotoDetailFragment().apply {
            val args = Bundle()
            args.putParcelable(CommonUtils.ACTION_TYPE, photo)
            arguments = args
        }
    }
}
