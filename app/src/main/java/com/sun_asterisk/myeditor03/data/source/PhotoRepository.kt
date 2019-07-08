package com.sun_asterisk.myeditor03.data.source

import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.source.PhotoDataSource.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.PhotoDataSource.PhotoRemoteDataSource
import io.reactivex.Observable

class PhotoRepository private constructor(
    private val local: PhotoLocalDataSource,
    private val remote: PhotoRemoteDataSource
) {

    fun getCollections(page: Int): Observable<List<Collection>> {
        return remote.getCollections(page)
    }

    companion object {
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
