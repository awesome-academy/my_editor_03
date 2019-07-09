package com.sun_asterisk.myeditor03.screen.collectiondetail.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.utils.BaseAdapter
import com.sun_asterisk.myeditor03.utils.BaseViewHolder
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.item_photo_by_collection.view.imageViewDetailPoster

class CollectionDetailAdapter : BaseAdapter<Photo>() {
    override fun layout(row: Int): Int = R.layout.item_photo_by_collection

    override fun viewHolder(view: View): BaseViewHolder<Photo> = PhotoAdapterViewHolder(view)

    companion object {
        class PhotoAdapterViewHolder(
            private val view: View
        ) : BaseViewHolder<Photo>(view) {

            override fun bindData(data: Photo, listener: OnItemRecyclerViewClickListener<Photo>) {
                Glide.with(view.context)
                    .load(data.urlImage.regular)
                    .into(view.imageViewDetailPoster)
                view.setOnClickListener { listener.onItemClick(data) }
            }
        }
    }
}
