package com.sun_asterisk.myeditor03.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class ProfileImage(
    @SerializedName("small")
    var smallImage: String,
    @SerializedName("medium")
    var mediumImage: String,
    @SerializedName("large")
    var LargeImage: String
) : Parcelable
