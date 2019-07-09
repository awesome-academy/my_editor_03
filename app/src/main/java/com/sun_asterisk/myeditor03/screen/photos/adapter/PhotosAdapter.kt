package com.sun_asterisk.myeditor03.screen.photos.adapter

import android.view.View
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.utils.BaseAdapter
import com.sun_asterisk.myeditor03.utils.BaseViewHolder
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.myeditor03.utils.loadImageUrl
import kotlinx.android.synthetic.main.item_photo.view.imagePhoto

class PhotosAdapter : BaseAdapter<Photo>() {
    override fun layout(row: Int): Int = R.layout.item_photo

    override fun viewHolder(view: View): BaseViewHolder<Photo> = PhotoAdapterViewHolder(view)

    companion object {
        class PhotoAdapterViewHolder(
            private val view: View
        ) : BaseViewHolder<Photo>(view) {

            override fun bindData(data: Photo, listener: OnItemRecyclerViewClickListener<Photo>) {
                view.imagePhoto.loadImageUrl(data.urlImage.regular)
                view.setOnClickListener { listener.onItemClick(data) }
            }
        }
    }
}
