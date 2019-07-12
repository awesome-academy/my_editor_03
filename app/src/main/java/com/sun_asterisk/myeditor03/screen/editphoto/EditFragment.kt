package com.sun_asterisk.myeditor03.screen.editphoto

import android.arch.lifecycle.Observer
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.data.model.Photo
import com.sun_asterisk.myeditor03.screen.adjust.AdjustFragment
import com.sun_asterisk.myeditor03.screen.filter.FilterFragment
import com.sun_asterisk.myeditor03.utils.CommonUtils
import com.sun_asterisk.myeditor03.utils.addFragmentToFragment
import com.sun_asterisk.myeditor03.utils.loadImageUrl
import com.sun_asterisk.myeditor03.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_edit.imageViewBack
import kotlinx.android.synthetic.main.fragment_edit.imageViewEdit
import kotlinx.android.synthetic.main.fragment_edit.textViewAdjust
import kotlinx.android.synthetic.main.fragment_edit.textViewFilter

class EditFragment : Fragment(), OnClickListener {
    private var photo: Photo? = null
    private val filterFragment: FilterFragment by lazy { FilterFragment() }
    private val adjustFragment: AdjustFragment by lazy { AdjustFragment() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        initView()
        registerLiveData()
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.textViewFilter -> {
                filterFragment.setCurrentBitmap((imageViewEdit.drawable as BitmapDrawable).bitmap)
                addFragmentToFragment(
                    R.id.layoutContainer,
                    filterFragment,
                    true
                )
            }
            R.id.imageViewBack -> removeFragment(EditFragment::class.java.simpleName)
            R.id.textViewAdjust -> {
                adjustFragment.setCurrentBitmap((imageViewEdit.drawable as BitmapDrawable).bitmap)
                addFragmentToFragment(
                    R.id.layoutContainer,
                    adjustFragment,
                    true
                )
            }
        }
    }

    private fun initView() {
        textViewFilter.setOnClickListener(this)
        imageViewBack.setOnClickListener(this)
        textViewAdjust.setOnClickListener(this)
    }

    private fun registerLiveData() {
        filterFragment.bitmapLiveData.observe(this, Observer {
            imageViewEdit.setImageBitmap(it)
        })
        adjustFragment.adjustLiveData.observe(this, Observer {
            imageViewEdit.setImageBitmap(it)
        })
    }

    private fun bindView() {
        photo = arguments?.getParcelable(CommonUtils.ACTION_TYPE)
        imageViewEdit.loadImageUrl(photo!!.urlImage.regular)
    }

    companion object {
        fun instance(photo: Photo) = EditFragment().apply {
            val args = Bundle()
            args.putParcelable(CommonUtils.ACTION_TYPE, photo)
            arguments = args
        }
    }
}
