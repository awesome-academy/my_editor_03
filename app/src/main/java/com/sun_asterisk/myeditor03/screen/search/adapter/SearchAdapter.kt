package com.sun_asterisk.myeditor03.screen.search.adapter

import android.view.View
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Search
import com.sun_asterisk.myeditor03.utils.BaseAdapter
import com.sun_asterisk.myeditor03.utils.BaseViewHolder
import com.sun_asterisk.myeditor03.utils.OnItemSearchClickListener
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import kotlinx.android.synthetic.main.item_search_history.view.imageViewRemoveHistory
import kotlinx.android.synthetic.main.item_search_history.view.textViewSearchQuery

class SearchAdapter : BaseAdapter<Search>() {
    private var onItemClickListener: OnItemSearchClickListener? = null

    override fun layout(row: Int): Int = R.layout.item_search_history

    override fun viewHolder(view: View): BaseViewHolder<Search> = SearchAdapterViewHolder(view, onItemClickListener!!)

    fun setListener(onItemClickListener: OnItemSearchClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    companion object {
        class SearchAdapterViewHolder(
            private val view: View, private var onItemClickListener: OnItemSearchClickListener
        ) : BaseViewHolder<Search>(view) {

            override fun bindData(data: Search, listener: OnItemRecyclerViewClickListener<Search>) {
                view.textViewSearchQuery.text = data.title
                view.setOnClickListener { listener.onItemClick(data) }
                view.imageViewRemoveHistory.setOnClickListener { onItemClickListener.onItemSearchClick(data) }
            }
        }
    }
}
