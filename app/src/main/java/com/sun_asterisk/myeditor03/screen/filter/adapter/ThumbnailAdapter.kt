package com.sun_asterisk.myeditor03.screen.filter.adapter

import android.view.View
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.utils.BaseAdapter
import com.sun_asterisk.myeditor03.utils.BaseViewHolder
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.thumbnail_item.view.filterName
import net.alhazmy13.imagefilter.ImageFilter
import net.alhazmy13.imagefilter.ImageFilter.Filter

class ThumbnailAdapter: BaseAdapter<Filter>() {

    override fun viewHolder(view: View): BaseViewHolder<ImageFilter.Filter> = MyViewHolder(view)

    override fun layout(row: Int): Int = R.layout.thumbnail_item

    companion object {
        class MyViewHolder(private val view: View) : BaseViewHolder<ImageFilter.Filter>(view) {
            override fun bindData(
                data: ImageFilter.Filter,
                listener: OnItemRecyclerViewClickListener<Filter>
            ) {
                view.filterName.text = data.name
                view.setOnClickListener { listener.onItemClick(data) }
            }
        }
    }
}
