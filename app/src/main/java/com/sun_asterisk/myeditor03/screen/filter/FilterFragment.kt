package com.sun_asterisk.myeditor03.screen.filter

import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.utils.OnItemRecyclerViewClickListener
import com.sun_asterisk.myeditor03.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_filter.imageViewFilter
import kotlinx.android.synthetic.main.fragment_filter.recyclerViewFilter
import kotlinx.android.synthetic.main.fragment_filter.textViewCancel
import kotlinx.android.synthetic.main.fragment_filter.textViewSave
import net.alhazmy13.imagefilter.ImageFilter
import net.alhazmy13.imagefilter.ImageFilter.Filter

class FilterFragment : Fragment(), OnItemRecyclerViewClickListener<ImageFilter.Filter>, OnClickListener {
    val bitmapLiveData = MutableLiveData<Bitmap>()
    private var bitmap: Bitmap? = null
    private val filterViewModel: FilterViewModel by lazy { FilterViewModel() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }

    override fun onItemClick(data: Filter) {
        val bitmap = ImageFilter.applyFilter(this.bitmap?.copy(Bitmap.Config.RGB_565, true), data)
        imageViewFilter.setImageBitmap(bitmap)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewSave -> {
                bitmapLiveData.value = ((imageViewFilter.drawable as BitmapDrawable).bitmap)
                removeFragment(FilterFragment::class.java.simpleName)
            }
            R.id.textViewCancel -> {
                bitmapLiveData.value = bitmap
                removeFragment(FilterFragment::class.java.simpleName)
            }
        }
    }

    fun setCurrentBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    private fun setUp() {
        filterViewModel.initData()
        recyclerViewFilter.adapter = filterViewModel.adapter
        imageViewFilter.setImageBitmap(bitmap)
        filterViewModel.adapter.setOnItemClickListener(this)
        textViewSave.setOnClickListener(this)
        textViewCancel.setOnClickListener(this)
    }

    companion object {
        fun instance() = FilterFragment()
    }
}
