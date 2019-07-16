package com.sun_asterisk.myeditor03.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.sun_asterisk.myeditor03.data.model.Search
import com.sun_asterisk.myeditor03.data.source.local.PhotoDatabase.Companion.DATABASE_VERSION

@Database(entities = [Search::class], version = DATABASE_VERSION)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDAO(): PhotoDAO

    companion object {
        private var sPhotoDatabase: PhotoDatabase? = null
        const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Room_database_search"

        fun instance(context: Context): PhotoDatabase {
            if (sPhotoDatabase == null) {
                sPhotoDatabase = Room.databaseBuilder(context, PhotoDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return sPhotoDatabase as PhotoDatabase
        }
    }
}
