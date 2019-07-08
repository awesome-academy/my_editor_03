package com.sun_asterisk.myeditor03.data.source

import com.sun_asterisk.myeditor03.data.model.Collection
import io.reactivex.Observable

interface PhotoDataSource {
    interface PhotoLocalDataSource {
    }

    interface PhotoRemoteDataSource {
        fun getCollections(page: Int): Observable<List<Collection>>
    }
}
