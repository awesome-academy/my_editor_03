package com.sun_asterisk.myeditor03.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "search")
data class Search(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null
) : Parcelable
