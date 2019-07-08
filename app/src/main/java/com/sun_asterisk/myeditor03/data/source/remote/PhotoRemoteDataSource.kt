package com.sun_asterisk.myeditor03.data.source.remote

import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.source.PhotoDataSource
import com.sun_asterisk.myeditor03.data.source.remote.api.PhotoService
import com.sun_asterisk.myeditor03.data.source.remote.api.ServiceGenerator
import io.reactivex.Observable

class PhotoRemoteDataSource private constructor(private val photoService: PhotoService) :
    PhotoDataSource.PhotoRemoteDataSource {

    override fun getCollections(page: Int): Observable<List<Collection>> {
        return photoService.getCollections(page)
    }

    companion object {
        private var sInstance = PhotoRemoteDataSource(ServiceGenerator.create())

        fun instance(): PhotoRemoteDataSource {
            return sInstance
        }
    }
}
