package com.sun_asterisk.myeditor03.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: String,
    var name: String,
    @SerializedName("profile_image")
    var profileImage: ProfileImage
) : Parcelable
