package com.joeshuff.kolorseekbars

import android.graphics.*


fun Bitmap.getRoundedCornerBitmap(): Bitmap {
    val output = Bitmap.createBitmap(
        width,
        height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(output)
    val color = Color.parseColor("#ff424242")
    val paint = Paint()
    val rect = Rect(0, 0, width, height)
    val rectF = RectF(rect)
    val roundPx = 100f

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)

    return output
}