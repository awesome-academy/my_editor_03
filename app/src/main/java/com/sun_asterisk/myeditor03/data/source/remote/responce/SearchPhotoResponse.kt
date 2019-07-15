package com.sun_asterisk.myeditor03.data.source.remote.responce

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun_asterisk.myeditor03.data.model.Photo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchPhotoResponse(
    @SerializedName("results")
    var photos: List<Photo>? = null
) : Parcelable
