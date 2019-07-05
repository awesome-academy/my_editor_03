package com.sun_asterisk.myeditor03.data.source

import com.sun_asterisk.myeditor03.data.source.PhotoDataSource.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.PhotoDataSource.PhotoRemoteDataSource

class PhotoRepository private constructor(
    private val local: PhotoLocalDataSource,
    private val remote: PhotoRemoteDataSource
) {

    companion object {
        @Volatile
        private var sInstance: PhotoRepository? = null

        @JvmStatic
        fun instance(
            local: PhotoLocalDataSource,
            remote: PhotoRemoteDataSource
        ): PhotoRepository {
            if (sInstance == null) {
                synchronized(this) {
                    sInstance = PhotoRepository(local, remote)
                }
            }
            return sInstance!!
        }
    }
}
