package com.sun_asterisk.myeditor03.data.source.local

import com.sun_asterisk.myeditor03.data.source.PhotoDataSource

class PhotoLocalDataSource: PhotoDataSource.PhotoLocalDataSource {

    companion object {
        @Volatile  // @Volatile - Writes to this property are immediately visible to other threads
        private var sInstance: PhotoLocalDataSource? = null

        @JvmStatic
        fun instance(): PhotoLocalDataSource {
            synchronized(this) {
                if (sInstance == null) {
                    sInstance = PhotoLocalDataSource()
                }
            }
            return sInstance!!
        }
    }
}
