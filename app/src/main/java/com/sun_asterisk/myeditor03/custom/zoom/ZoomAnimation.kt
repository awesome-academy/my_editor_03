package com.sun_asterisk.myeditor03.custom.zoom

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator

class ZoomAnimation(
    private val view: View,
    private val zoomStart: Float,
    private val zoomEnd: Float,
    private val focalX: Float,
    private val focalY: Float,
    private val changeListener: OnAnimationChangeListener
) : Runnable {

    private val interpolator: Interpolator by lazy { AccelerateDecelerateInterpolator() }
    private val startTime: Long by lazy { System.currentTimeMillis() }

    override fun run() {
        val animationRatio = interpolate()
        val scale = zoomStart + animationRatio * (zoomEnd - zoomStart)
        val deltaScale = scale / changeListener.scale
        changeListener.onScale(deltaScale, focalX, focalY)
        if (animationRatio < FLOAT_DEFAULT_VALUE) {
            view.postOnAnimation(this)
        }
    }

    private fun interpolate(): Float {
        var timeRatio = FLOAT_DEFAULT_VALUE * (System.currentTimeMillis() - startTime) / ZOOM_DURATION
        timeRatio = Math.min(FLOAT_DEFAULT_VALUE, timeRatio)
        return interpolator.getInterpolation(timeRatio)
    }

    companion object {
        private val ZOOM_DURATION = 200
        private val FLOAT_DEFAULT_VALUE = 1.0f
    }
}
