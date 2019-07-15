package com.sun_asterisk.myeditor03.data.source.remote.responce

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sun_asterisk.myeditor03.data.model.Collection
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchCollectionResponse(
    @SerializedName("results")
    var collections: List<Collection>? = null
) : Parcelable
