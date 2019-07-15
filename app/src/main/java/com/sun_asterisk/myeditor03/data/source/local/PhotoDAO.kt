package com.sun_asterisk.myeditor03.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sun_asterisk.myeditor03.data.model.Search
import io.reactivex.Flowable

@Dao
interface PhotoDAO {

    @Query("SELECT * FROM search")
    fun getAllHistory(): Flowable<List<Search>>

    @Delete
    fun deleteHistory(search: Search)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHistory(search: Search)
}
