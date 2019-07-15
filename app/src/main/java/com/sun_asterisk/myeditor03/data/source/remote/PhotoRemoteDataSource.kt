package com.sun_asterisk.myeditor03.data.source.remote

import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.PhotoDataSource
import com.sun_asterisk.myeditor03.data.source.remote.api.PhotoService
import com.sun_asterisk.myeditor03.data.source.remote.api.ServiceGenerator
import com.sun_asterisk.myeditor03.data.source.remote.responce.SearchPhotoResponse
import io.reactivex.Observable

class PhotoRemoteDataSource private constructor(private val photoService: PhotoService) :
    PhotoDataSource.PhotoRemoteDataSource {

    override fun getSearchPhoto(query: String, page: Int): Observable<SearchPhotoResponse> {
        return photoService.getSearchPhoto(query, page)
    }

    override fun getPhotoDetail(id: String): Observable<Photo> {
        return photoService.getPhotoDetail(id)
    }

    override fun getPhotosByCollection(id: String, page: Int): Observable<List<Photo>> {
        return photoService.getPhotosByCollection(id, page)
    }

    override fun getPhotos(page: Int): Observable<List<Photo>> {
        return photoService.getPhotos(page)
    }

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
