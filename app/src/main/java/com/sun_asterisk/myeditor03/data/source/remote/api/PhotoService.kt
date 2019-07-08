package com.sun_asterisk.myeditor03.data.source.remote.api

import com.sun_asterisk.myeditor03.data.model.Collection
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {

    @GET("collections")
    fun getCollections(@Query("page") page: Int): Observable<List<Collection>>
}
