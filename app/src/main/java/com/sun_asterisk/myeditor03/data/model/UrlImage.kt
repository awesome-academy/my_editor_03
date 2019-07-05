package com.sun_asterisk.myeditor03.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UrlImage(
    var raw: String,
    var full: String,
    var regular: String,
    var small: String,
    var thumb: String
) : Parcelable
