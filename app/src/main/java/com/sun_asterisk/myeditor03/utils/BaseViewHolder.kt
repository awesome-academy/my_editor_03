package com.sun_asterisk.myeditor03.utils

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mViews = SparseArrayCompat<View>()

    abstract fun bindData(data: T, listener: OnItemRecyclerViewClickListener<T>)

    fun view() = itemView
}
