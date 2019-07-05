package com.sun_asterisk.myeditor03.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConvertPhoto(
    var id: String,
    var description: String,
    @SerializedName("urls")
    var urlImage: UrlImage
) : Parcelable
