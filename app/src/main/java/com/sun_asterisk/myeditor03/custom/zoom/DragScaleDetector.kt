package com.sun_asterisk.myeditor03.custom.zoom

import android.content.Context
import android.view.MotionEvent
import android.view.ScaleGestureDetector

class DragScaleDetector constructor(
    context: Context,
    private val onAnimationChangeListener: OnAnimationChangeListener
) : ScaleGestureDetector.OnScaleGestureListener {

    private var activePointerId: Int = 0
    private var activePointerIndex = 0
    private var lastTouchX: Float = 0.toFloat()
    private var lastTouchY: Float = 0.toFloat()
    private val scaleGestureDetector: ScaleGestureDetector = ScaleGestureDetector(context, this)

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        onAnimationChangeListener.onScale(detector.scaleFactor, detector.focusX, detector.focusY)
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {}

    fun onTouchEvent(ev: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(ev)
        return processTouchEvent(ev)
    }

    private fun processTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                activePointerId = ev.getPointerId(FIRST_POINTER_INDEX)
                lastTouchX = getActiveX(ev)
                lastTouchY = getActiveY(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                val x = getActiveX(ev)
                val y = getActiveY(ev)
                val dx = x - lastTouchX
                val dy = y - lastTouchY
                onAnimationChangeListener.onDrag(dx, dy)
                lastTouchX = x
                lastTouchY = y
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = ev.actionIndex
                val pointerId = ev.getPointerId(pointerIndex)
                if (pointerId == activePointerId) {
                    val newPointerIndex = if (pointerIndex == FIRST_POINTER_INDEX) SECOND_POINTER_INDEX
                    else FIRST_POINTER_INDEX
                    activePointerId = ev.getPointerId(newPointerIndex)
                    lastTouchX = ev.getX(newPointerIndex)
                    lastTouchY = ev.getY(newPointerIndex)
                }
            }
        }
        activePointerIndex = ev.findPointerIndex(activePointerId)
        return true
    }

    private fun getActiveX(ev: MotionEvent): Float {
        return if (activePointerIndex < ev.pointerCount) {
            ev.getX(activePointerIndex)
        } else ev.x
    }

    private fun getActiveY(ev: MotionEvent): Float {
        return if (activePointerIndex < ev.pointerCount) {
            ev.getY(activePointerIndex)
        } else ev.y
    }

    companion object {
        private val FIRST_POINTER_INDEX = 0
        private val SECOND_POINTER_INDEX = 1
    }
}
