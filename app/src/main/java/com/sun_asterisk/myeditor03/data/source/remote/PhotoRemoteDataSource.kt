package com.sun_asterisk.myeditor03.data.source.remote

import com.sun_asterisk.myeditor03.data.source.PhotoDataSource
import com.sun_asterisk.myeditor03.data.source.remote.api.PhotoService
import com.sun_asterisk.myeditor03.data.source.remote.api.ServiceGenerator

class PhotoRemoteDataSource private constructor(private val photoService: PhotoService) :
    PhotoDataSource.PhotoRemoteDataSource {

    companion object {
        private var sInstance = PhotoRemoteDataSource(ServiceGenerator.create())

        fun instance(): PhotoRemoteDataSource {
            return sInstance
        }
    }
}
