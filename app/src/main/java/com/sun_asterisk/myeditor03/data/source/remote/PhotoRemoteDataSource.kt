package com.sun_asterisk.myeditor03.data.source.remote

import com.sun_asterisk.myeditor03.data.source.PhotoDataSource
import com.sun_asterisk.myeditor03.data.source.remote.api.MovieService
import com.sun_asterisk.myeditor03.data.source.remote.api.ServiceGenerator

class PhotoRemoteDataSource private constructor(private val movieService: MovieService) :
    PhotoDataSource.PhotoRemoteDataSource {

    companion object {
        @Volatile
        private var sInstance = PhotoRemoteDataSource(ServiceGenerator.create())

        fun instance(): PhotoRemoteDataSource {
            return sInstance
        }
    }
}
