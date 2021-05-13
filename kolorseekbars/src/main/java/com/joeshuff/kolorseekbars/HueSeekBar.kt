package com.joeshuff.kolorseekbars

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import java.util.*
import kotlin.math.max

class HueSeekBar(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatSeekBar(context, attrs) {

    var backgroundDependsOnOtherSliders = false
    var defaultHue = 0f
    var progressBarHeight: Int? = null

    init {
        max = 360

        context.theme.obtainStyledAttributes(attrs, R.styleable.HueSeekBar, 0, 0).apply {
            backgroundDependsOnOtherSliders = getBoolean(R.styleable.HueSeekBar_updateHueBackgroundFromOthers, false)
            defaultHue = getFloat(R.styleable.HueSeekBar_defaultHue, KolorCreator.DEFAULT_HUE)
            val loadedHeight = getDimensionPixelSize(R.styleable.HueSeekBar_hueProgressHeight, -1)
            progressBarHeight = if (loadedHeight < 0) null else loadedHeight

            if (defaultHue < 0f || defaultHue > 360f) {
                throw InputMismatchException("defaultHue needs to be >= 0f and <= 360f. You input $defaultHue")
            }
        }

        update(defaultHue, KolorCreator.DEFAULT_SATURATION, KolorCreator.DEFAULT_BRIGHTNESS)
    }

    fun update(color: Int) {
        var hsb = arrayOf(0f, 0f, 0f).toFloatArray()
        Color.colorToHSV(color, hsb)

        update(hsb[0], hsb[1], hsb[2])
    }

    fun update(hue: Float, saturation: Float, brightness: Float) {
        progress = hue.toInt()
        progressDrawable = BitmapDrawable(context.resources,
            if (backgroundDependsOnOtherSliders) createHueDrawable(saturation, brightness)
            else createHueDrawable(KolorCreator.DEFAULT_SATURATION, KolorCreator.DEFAULT_BRIGHTNESS)
        )
    }

    private fun createHueDrawable(saturation: Float, brightness: Float): Bitmap {
        val bitmap = Bitmap.createBitmap(360, progressBarHeight?: max(1, height), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        (0..360).toList().forEach {
            val paint = Paint()
            paint.color = Color.HSVToColor(arrayOf(it.toFloat(), saturation, brightness).toFloatArray())
            canvas.drawRect(it.toFloat(), 0f, (it + 1).toFloat(), (progressBarHeight?: max(1, height)).toFloat(), paint)
        }

//        return bitmap.getRoundedCornerBitmap() //Doesn't really work well yet
        return bitmap
    }
}