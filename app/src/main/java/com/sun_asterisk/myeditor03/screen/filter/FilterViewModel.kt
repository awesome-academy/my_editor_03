package com.sun_asterisk.myeditor03.screen.filter

import android.arch.lifecycle.ViewModel
import com.sun_asterisk.myeditor03.screen.filter.adapter.ThumbnailAdapter
import net.alhazmy13.imagefilter.ImageFilter
import java.util.Arrays

class FilterViewModel : ViewModel() {
    val adapter: ThumbnailAdapter = ThumbnailAdapter()

    fun initData() {
        val filters = arrayOf(
            ImageFilter.Filter.GRAY,
            ImageFilter.Filter.NEON,
            ImageFilter.Filter.PIXELATE,
            ImageFilter.Filter.TV,
            ImageFilter.Filter.BLOCK,
            ImageFilter.Filter.OLD,
            ImageFilter.Filter.SHARPEN,
            ImageFilter.Filter.LIGHT,
            ImageFilter.Filter.LOMO,
            ImageFilter.Filter.SKETCH,
            ImageFilter.Filter.GOTHAM
        )
        val filterList = Arrays.asList(*filters)
        adapter.replaceItem(filterList)
    }
}
