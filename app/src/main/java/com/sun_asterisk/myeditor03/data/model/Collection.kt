package com.sun_asterisk.myeditor03.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Collection(
    var id: String,
    var title: String,
    @SerializedName("published_at")
    var publishAt: String,
    @SerializedName("total_photos")
    var totalPhotos: Int,
    @SerializedName("cover_photo")
    var convertPhoto: ConvertPhoto,
    var user: User
) : Parcelable
