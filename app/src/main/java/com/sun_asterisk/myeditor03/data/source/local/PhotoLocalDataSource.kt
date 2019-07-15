package com.sun_asterisk.myeditor03.data.source.local

import com.sun_asterisk.myeditor03.data.model.Search
import com.sun_asterisk.myeditor03.data.source.PhotoDataSource
import io.reactivex.Flowable

class PhotoLocalDataSource private constructor(private val photoDao: PhotoDAO) :
    PhotoDataSource.PhotoLocalDataSource {

    override fun getAllHistory(): Flowable<List<Search>> {
        return photoDao.getAllHistory()
    }

    override fun deleteHistory(search: Search) {
        return photoDao.deleteHistory(search)
    }

    override fun insertHistory(search: Search) {
        return photoDao.insertHistory(search)
    }

    companion object {
        private var sInstance: PhotoLocalDataSource? = null

        @JvmStatic
        fun instance(photoDao: PhotoDAO): PhotoLocalDataSource {
            synchronized(this) {
                if (sInstance == null) {
                    sInstance = PhotoLocalDataSource(photoDao)
                }
            }
            return sInstance!!
        }
    }
}
