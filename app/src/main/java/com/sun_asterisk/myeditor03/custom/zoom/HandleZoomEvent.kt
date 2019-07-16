package com.sun_asterisk.myeditor03.custom.zoom

import android.graphics.Matrix
import android.graphics.RectF
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

class HandleZoomEvent(private val imageView: ImageView) : View.OnTouchListener, OnAnimationChangeListener,
    GestureDetector.OnDoubleTapListener {

    private val matrixValues = FloatArray(9)
    private val matrix = Matrix()
    private val displayRectF = RectF()
    private val gestureDetector: GestureDetector = GestureDetector(imageView.context, SimpleOnGestureListener())
    private val scaleDetector: DragScaleDetector = DragScaleDetector(imageView.context, this)

    private val displayRect: RectF?
        get() {
            val d = imageView.drawable
            if (d != null) {
                displayRectF.set(
                    DEFAULT_ZERO_VALUE.toFloat(), DEFAULT_ZERO_VALUE.toFloat(), d.intrinsicWidth.toFloat(),
                    d.intrinsicHeight.toFloat()
                )
                matrix.mapRect(displayRectF)
                return displayRectF
            }
            return null
        }

    init {
        imageView.setOnTouchListener(this)
        gestureDetector.setOnDoubleTapListener(this)
    }

    override fun getScale(): Float {
        matrix.getValues(matrixValues)
        return matrixValues[Matrix.MSCALE_X]
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        imageView.callOnClick()
        return false
    }

    override fun onDoubleTap(ev: MotionEvent): Boolean {
        val scale = scale
        val x = ev.x
        val y = ev.y
        if (scale < MAX_SCALE_VALUE) setScale(MAX_SCALE_VALUE, x, y)
        else setScale(DEFAULT_SCALE_VALUE, x, y)
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return false
    }

    override fun onScale(scaleFactor: Float, focusX: Float, focusY: Float) {
        if (scale < MAX_SCALE_VALUE && scaleFactor > DEFAULT_SCALE_FACTOR
            || scale > MIN_SCALE_VALUE && scaleFactor < DEFAULT_SCALE_FACTOR
        ) {
            matrix.postScale(scaleFactor, scaleFactor, focusX, focusY)
            checkAndDisplayMatrix()
        }
    }

    override fun onDrag(dx: Float, dy: Float) {
        matrix.postTranslate(dx, dy)
        checkAndDisplayMatrix()
    }

    override fun onTouch(v: View, ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_UP -> if (scale < DEFAULT_SCALE_VALUE) {
                val rect = displayRect
                if (rect != null) {
                    v.post(
                        ZoomAnimation(
                            imageView, scale, DEFAULT_SCALE_VALUE,
                            rect.centerX(), rect.centerY(), this
                        )
                    )
                }
            }
        }
        imageView.scaleType = ImageView.ScaleType.MATRIX
        gestureDetector.onTouchEvent(ev)
        scaleDetector.onTouchEvent(ev)
        return true
    }

    private fun setScale(scale: Float, focalX: Float, focalY: Float) {
        imageView.post(ZoomAnimation(imageView, scale, scale, focalX, focalY, this))
    }

    private fun checkAndDisplayMatrix() {
        if (checkMatrixBounds()) {
            imageView.imageMatrix = matrix
        }
    }

    private fun checkMatrixBounds(): Boolean {
        val rect = displayRect ?: return false
        val rectHeight = rect.height()
        val rectWidth = rect.width()
        var deltaX = DEFAULT_ZERO_VALUE.toFloat()
        var deltaY = DEFAULT_ZERO_VALUE.toFloat()
        val viewHeight = imageView.height
        when {
            rectHeight <= viewHeight -> deltaY = (viewHeight - rectHeight) / DEFAULT_DIVIDE_VALUE - rect.top
            rect.top > DEFAULT_ZERO_VALUE -> deltaY = -rect.top
            rect.bottom < viewHeight -> deltaY = viewHeight - rect.bottom
        }
        val viewWidth = imageView.width
        when {
            rectWidth <= viewWidth -> deltaX = (viewWidth - rectWidth) / DEFAULT_DIVIDE_VALUE - rect.left
            rect.left > DEFAULT_ZERO_VALUE -> deltaX = -rect.left
            rect.right < viewWidth -> deltaX = viewWidth - rect.right
        }
        matrix.postTranslate(deltaX, deltaY)
        return true
    }

    companion object {
        private val MIN_SCALE_VALUE = 0.5f
        private val DEFAULT_SCALE_VALUE = 1.0f
        private val MAX_SCALE_VALUE = 3.0f
        private val DEFAULT_SCALE_FACTOR = 1.0f
        private val DEFAULT_ZERO_VALUE = 0
        private val DEFAULT_DIVIDE_VALUE = 2
    }
}
