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

class SaturationSeekBar(context: Context, attrs: AttributeSet): AppCompatSeekBar(context, attrs) {

    var backgroundDependsOnOtherSliders = false
    var defaultSaturation = 1f

    init {
        min = 0
        max = 1000

        context.theme.obtainStyledAttributes(attrs, R.styleable.SaturationSeekBar, 0, 0).apply {
            backgroundDependsOnOtherSliders = getBoolean(R.styleable.SaturationSeekBar_updateSaturationBackgroundFromOthers, false)
            defaultSaturation = getFloat(R.styleable.SaturationSeekBar_defaultSaturation, KolorCreator.DEFAULT_SATURATION)

            if (defaultSaturation < 0f || defaultSaturation > 1f) {
                throw InputMismatchException("defaultSaturation needs to be >= 0f and <= 1f. You input $defaultSaturation")
            }
        }

        update(KolorCreator.DEFAULT_HUE, defaultSaturation, KolorCreator.DEFAULT_BRIGHTNESS)
    }

    fun update(color: Int) {
        var hsb = arrayOf(0f, 0f, 0f).toFloatArray()
        Color.colorToHSV(color, hsb)

        update(hsb[0], hsb[1], hsb[2])
    }

    fun update(hue: Float, saturation: Float, brightness: Float) {
        progress = (saturation * 1000).toInt()
        progressDrawable = BitmapDrawable(context.resources,
            if (backgroundDependsOnOtherSliders) createSaturationDrawable(hue, brightness)
            else createSaturationDrawable(KolorCreator.DEFAULT_HUE, KolorCreator.DEFAULT_BRIGHTNESS)
        )
    }

    private fun createSaturationDrawable(hue: Float, brightness: Float): Bitmap {
        val bitmap = Bitmap.createBitmap(100, 1, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        (0..100).toList().forEach {
            val paint = Paint()
            paint.color = Color.HSVToColor(arrayOf(hue, it / 100f, brightness).toFloatArray())
            canvas.drawRect(it.toFloat(), 0f, (it + 1).toFloat(), 1f, paint)
        }

        return bitmap
    }
}