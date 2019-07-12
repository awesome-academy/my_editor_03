package com.sun_asterisk.myeditor03.screen.adjust

import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.SeekBar
import com.sun_asterisk.myeditor03.R
import com.sun_asterisk.myeditor03.utils.AdjustImageView
import com.sun_asterisk.myeditor03.utils.removeFragment
import kotlinx.android.synthetic.main.fragment_adjust.imageViewAdjust
import kotlinx.android.synthetic.main.fragment_adjust.seekBarBrightness
import kotlinx.android.synthetic.main.fragment_adjust.seekBarContrast
import kotlinx.android.synthetic.main.fragment_adjust.seekBarSaturation
import kotlinx.android.synthetic.main.fragment_adjust.textViewCancel
import kotlinx.android.synthetic.main.fragment_adjust.textViewSave

class AdjustFragment : Fragment(), SeekBar.OnSeekBarChangeListener, OnClickListener {
    var bitmap: Bitmap? = null
    val adjustLiveData = MutableLiveData<Bitmap>()
    private val BRIGHTNESS = 100f
    private val CONTRAST = 170f
    private val STATURATION = .10f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adjust, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        val brightness = seekBarBrightness.progress - BRIGHTNESS
        val contrast = seekBarContrast.progress / CONTRAST
        val saturation = seekBarSaturation.progress * STATURATION
        imageViewAdjust.setImageBitmap(
            AdjustImageView.changeBitmapImageView(
                bitmap!!,
                brightness,
                contrast,
                saturation
            )
        )
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textViewSave -> {
                adjustLiveData.value = ((imageViewAdjust.drawable as BitmapDrawable).bitmap)
                removeFragment(AdjustFragment::class.java.simpleName)
            }
            R.id.textViewCancel -> {
                adjustLiveData.value = bitmap
                removeFragment(AdjustFragment::class.java.simpleName)
            }
        }
    }

    private fun initView() {
        imageViewAdjust.setImageBitmap(bitmap)
        textViewSave.setOnClickListener(this)
        textViewCancel.setOnClickListener(this)
        seekBarBrightness.setOnSeekBarChangeListener(this)
        seekBarContrast.setOnSeekBarChangeListener(this)
        seekBarSaturation.setOnSeekBarChangeListener(this)
    }

    fun setCurrentBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
    }

    companion object {
        fun instance() = AdjustFragment()
    }
}
