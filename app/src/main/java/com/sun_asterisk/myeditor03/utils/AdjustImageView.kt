package com.sun_asterisk.myeditor03.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint

class AdjustImageView {

    companion object {
        private val DEFAULT_VALUE = 0f
        private val ONE_VALUE = 1f

        fun changeBitmapImageView(
            bitmap: Bitmap,
            brightness: Float,
            contrast: Float,
            saturation: Float
        ): Bitmap {
            //Matrix: 4x5
            val colorMatrix = ColorMatrix(
                floatArrayOf(
                    contrast, saturation, saturation, DEFAULT_VALUE, brightness,
                    DEFAULT_VALUE, contrast, DEFAULT_VALUE, DEFAULT_VALUE, brightness,
                    DEFAULT_VALUE, DEFAULT_VALUE, contrast, DEFAULT_VALUE, brightness,
                    DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, ONE_VALUE, DEFAULT_VALUE
                )
            )
            val btp = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
            val canvas = Canvas(btp)
            val paint = Paint()
            paint.colorFilter = ColorMatrixColorFilter(colorMatrix)
            canvas.drawBitmap(bitmap, DEFAULT_VALUE, DEFAULT_VALUE, paint)
            return btp
        }
    }
}
