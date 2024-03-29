package com.sun_asterisk.myeditor03.data.source

import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.model.Search
import com.sun_asterisk.myeditor03.data.source.PhotoDataSource.PhotoLocalDataSource
import com.sun_asterisk.myeditor03.data.source.PhotoDataSource.PhotoRemoteDataSource
import com.sun_asterisk.myeditor03.data.source.remote.responce.SearchCollectionResponse
import com.sun_asterisk.myeditor03.data.source.remote.responce.SearchPhotoResponse
import io.reactivex.Flowable
import io.reactivex.Observable

class PhotoRepository private constructor(
    private val local: PhotoLocalDataSource,
    private val remote: PhotoRemoteDataSource
) {

    fun getCollections(page: Int): Observable<List<Collection>> {
        return remote.getCollections(page)
    }

    fun getPhotos(page: Int): Observable<List<Photo>> {
        return remote.getPhotos(page)
    }

    fun getPhotosByCollection(id: String, page: Int): Observable<List<Photo>> {
        return remote.getPhotosByCollection(id, page)
    }

    fun getPhotoDetail(id: String): Observable<Photo> {
        return remote.getPhotoDetail(id)
    }

    fun getSearchPhoto(query: String, page: Int): Observable<SearchPhotoResponse> {
        return remote.getSearchPhoto(query, page)
    }

    fun getSearchCollection(query: String, page: Int): Observable<SearchCollectionResponse> {
        return remote.getSearchCollection(query, page)
    }

    fun getAllHistory(): Flowable<List<Search>> {
        return local.getAllHistory()
    }

    fun deleteHistory(search: Search) {
        return local.deleteHistory(search)
    }

    fun insertHistory(search: Search) {
        return local.insertHistory(search)
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
