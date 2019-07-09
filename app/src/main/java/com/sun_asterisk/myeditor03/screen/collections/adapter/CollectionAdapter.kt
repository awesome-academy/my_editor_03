package com.sun_asterisk.myeditor03.screen.collections.adapter

import android.view.View
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Collection
import com.sun_asterisk.myeditor03.utils.BaseAdapter
import com.sun_asterisk.myeditor03.utils.BaseViewHolder
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.myeditor03.utils.setRadiusImage
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
                view.imageCollections.setRadiusImage(data.convertPhoto.urlImage.regular, 16)
                view.setOnClickListener { listener.onItemClick(data) }
            }
        }
    }
}
