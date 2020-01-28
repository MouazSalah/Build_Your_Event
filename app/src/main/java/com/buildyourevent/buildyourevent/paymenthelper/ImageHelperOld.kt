package com.awesomeurch.paymentlayout.helper

import android.graphics.*
import android.graphics.Bitmap.Config
import android.graphics.PorterDuff.Mode

object ImageHelperOld {
    fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap
                .height, Config.ARGB_8888
        )
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }
}