package com.sun_asterisk.myeditor03.data.source

import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.model.Photo
import io.reactivex.Observable

interface PhotoDataSource {
    interface PhotoLocalDataSource {
    }

    interface PhotoRemoteDataSource {
        fun getCollections(page: Int): Observable<List<Collection>>
        fun getPhotos(page: Int): Observable<List<Photo>>
        fun getPhotosByCollection(id: String, page: Int): Observable<List<Photo>>
        fun getPhotoDetail(id: String): Observable<Photo>
    }
}
