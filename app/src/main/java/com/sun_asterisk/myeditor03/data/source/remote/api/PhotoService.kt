package com.sun_asterisk.myeditor03.data.source.remote.api

import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.data.source.remote.responce.SearchPhotoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {

    @GET("collections")
    fun getCollections(@Query("page") page: Int): Observable<List<Collection>>

    @GET("photos")
    fun getPhotos(@Query("page") page: Int): Observable<List<Photo>>

    @GET("collections/{id}/photos")
    fun getPhotosByCollection(@Path("id") id: String, @Query("page") page: Int): Observable<List<Photo>>

    @GET("photos/{id}")
    fun getPhotoDetail(@Path("id") id: String): Observable<Photo>

    @GET("search/photos")
    fun getSearchPhoto(@Query("query") query: String, @Query("page") page: Int): Observable<SearchPhotoResponse>
}
