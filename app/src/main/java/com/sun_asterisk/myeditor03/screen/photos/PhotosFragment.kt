package com.sun_asterisk.myeditor03.screen.photos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun_asterisk.myeditor03.R

class PhotosFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)
        return view
    }

    companion object {
        fun getInstance(): PhotosFragment {
            return PhotosFragment()
        }
    }
}
