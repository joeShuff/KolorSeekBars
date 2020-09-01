package com.joeshuff.kolorseekbars

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSeekBar
import java.util.*

class BrightnessSeekBar(context: Context, attrs: AttributeSet): AppCompatSeekBar(context, attrs) {

    var backgroundDependsOnOtherSliders = false
    var defaultBrightness = 1f

    init {
        min = 0
        max = 1000

        context.theme.obtainStyledAttributes(attrs, R.styleable.BrightnessSeekBar, 0, 0).apply {
            backgroundDependsOnOtherSliders = getBoolean(R.styleable.BrightnessSeekBar_updateBrightnessBackgroundFromOthers, false)
            defaultBrightness = getFloat(R.styleable.BrightnessSeekBar_defaultBrightness, KolorCreator.DEFAULT_BRIGHTNESS)

            if (defaultBrightness < 0f || defaultBrightness > 1f) {
                throw InputMismatchException("defaultBrightness needs to be >= 0f and <= 1f. You input $defaultBrightness")
            }
        }

        update(KolorCreator.DEFAULT_HUE, KolorCreator.DEFAULT_SATURATION, defaultBrightness)
    }

    fun update(color: Int) {
        var hsb = arrayOf(0f, 0f, 0f).toFloatArray()
        Color.colorToHSV(color, hsb)

        update(hsb[0], hsb[1], hsb[2])
    }

    fun update(hue: Float, saturation: Float, brightness: Float) {
        progress = (brightness * 1000).toInt()
        progressDrawable = BitmapDrawable(context.resources,
            if (backgroundDependsOnOtherSliders) createBrightnessDrawable(hue, saturation)
            else createBrightnessDrawable(KolorCreator.DEFAULT_HUE, KolorCreator.DEFAULT_SATURATION)
        )
    }

    private fun createBrightnessDrawable(hue: Float, saturation: Float): Bitmap {
        val imageSize = 100
        val bitmap = Bitmap.createBitmap(imageSize, 1, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        (0..imageSize).toList().forEach {
            val paint = Paint()
            paint.color = Color.HSVToColor(arrayOf(hue, saturation, it / 100f).toFloatArray())
            canvas.drawRect(it.toFloat(), 0f, (it + 1).toFloat(), 1f, paint)
        }

        return bitmap
    }

}