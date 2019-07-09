package com.sun_asterisk.myeditor03.data.source.remote.api

import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.model.Photo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {

    @GET("collections")
    fun getCollections(@Query("page") page: Int): Observable<List<Collection>>

    @GET("photos")
    fun getPhotos(@Query("page") page: Int): Observable<List<Photo>>
}
