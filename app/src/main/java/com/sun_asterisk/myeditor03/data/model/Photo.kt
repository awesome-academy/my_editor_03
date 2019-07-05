package com.sun_asterisk.myeditor03.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    var id: String,
    var description: String,
    @SerializedName("likes")
    var like: Int,
    @SerializedName("views")
    var view: Int,
    @SerializedName("urls")
    var urlImage: UrlImage,
    @SerializedName("location")
    var location: Location
) : Parcelable
