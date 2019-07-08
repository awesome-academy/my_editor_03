package com.sun_asterisk.myeditor03.screen.collections.adapter

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.utils.BaseAdapter
import com.sun_asterisk.myeditor03.utils.BaseViewHolder
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.item_collections.view.imageCollections
import kotlinx.android.synthetic.main.item_collections.view.textViewTitle
import kotlinx.android.synthetic.main.item_collections.view.textViewTotalCount
import kotlinx.android.synthetic.main.item_collections.view.textViewUserName

class CollectionAdapter : BaseAdapter<Collection>() {
    override fun layout(row: Int): Int = R.layout.item_collections

    override fun viewHolder(view: View): BaseViewHolder<Collection> = CollectionAdapterViewHolder(view)

    companion object {
        class CollectionAdapterViewHolder(
            private val view: View
        ) : BaseViewHolder<Collection>(view) {

            override fun bindData(data: Collection, listener: OnItemRecyclerViewClickListener<Collection>) {
                view.textViewTitle.text = data.title
                view.textViewUserName.text = data.user.name
                view.textViewTotalCount.text = data.totalPhotoToString()
                Glide.with(view.context)
                    .load(data.convertPhoto.urlImage.regular)
                    .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(16)))
                    .into(view.imageCollections)
                view.setOnClickListener { listener.onItemClick(data) }
            }
        }
    }
}
