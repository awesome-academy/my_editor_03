package com.sun_asterisk.myeditor03.data.source

import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.model.Search
import com.sun_asterisk.myeditor03.data.source.remote.responce.SearchCollectionResponse
import com.sun_asterisk.myeditor03.data.source.remote.responce.SearchPhotoResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface PhotoDataSource {
    interface PhotoLocalDataSource {
        fun getAllHistory(): Flowable<List<Search>>
        fun deleteHistory(search: Search)
        fun insertHistory(search: Search)
    }

    interface PhotoRemoteDataSource {
        fun getCollections(page: Int): Observable<List<Collection>>
        fun getPhotos(page: Int): Observable<List<Photo>>
        fun getPhotosByCollection(id: String, page: Int): Observable<List<Photo>>
        fun getPhotoDetail(id: String): Observable<Photo>
        fun getSearchPhoto(query: String, page: Int): Observable<SearchPhotoResponse>
        fun getSearchCollection(query: String, page: Int): Observable<SearchCollectionResponse>
    }
}
