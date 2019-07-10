package com.sun_asterisk.myeditor03.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun_asterisk.myeditor03.utils.CommonUtils
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
    var location: Location?,
    @SerializedName("user")
    var user: User,
    @SerializedName("downloads")
    var download: Int? = null
) : Parcelable {

    fun likeToString() = like.toString() + CommonUtils.LIKE
    fun viewToSting() = view.toString() + CommonUtils.VIEW
    fun downloadToString() = download.toString() + CommonUtils.DOWNLOAD
}
