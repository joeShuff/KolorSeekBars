package com.joeshuff.kolorseekbars

import android.graphics.Color
import android.util.Log
import android.widget.SeekBar
import java.util.*

class KolorCreator(
    private var hueSeekBar: HueSeekBar? = null,
    private var saturationSeekBar: SaturationSeekBar? = null,
    private var brightnessSeekBar: BrightnessSeekBar? = null,
    val onColorChange: (newColor: Int) -> Unit) {

    companion object {
        const val DEFAULT_HUE = 0f
        const val DEFAULT_SATURATION = 1f
        const val DEFAULT_BRIGHTNESS = 1f
    }

    var hue: Float = DEFAULT_HUE
        private set

    var saturation: Float = DEFAULT_SATURATION
        private set

    var brightness: Float = DEFAULT_BRIGHTNESS
        private set

    init {
        if (hueSeekBar == null && saturationSeekBar == null && brightnessSeekBar == null) {
            Log.w("NO SLIDERS", "No sliders have been found on initialise. Please use setters to add sliders")
        }

        hueSeekBar?.let { setHueSeekBar(it); hue = it.defaultHue }
        saturationSeekBar?.let { setSaturationSeekBar(it); saturation = it.defaultSaturation }
        brightnessSeekBar?.let { setBrightnessSeekBar(it); brightness = it.defaultBrightness }

        update()
    }

    private fun update() {
        val newColor = Color.HSVToColor(arrayOf(hue, saturation, brightness).toFloatArray())
        onColorChange.invoke(newColor)

        Log.i("KolorSlider", "Update received")

        hueSeekBar?.update(hue, saturation, brightness)
        saturationSeekBar?.update(hue, saturation, brightness)
        brightnessSeekBar?.update(hue, saturation, brightness)
    }

    fun setHueSeekBar(seekBar: HueSeekBar) {
        hueSeekBar = seekBar

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) { setHue(p1.toFloat()) }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    fun setSaturationSeekBar(seekBar: SaturationSeekBar) {
        saturationSeekBar = seekBar

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) { setSaturation(p1.toFloat() / 1000f) }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    fun setBrightnessSeekBar(seekBar: BrightnessSeekBar) {
        brightnessSeekBar = seekBar

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) { setBrightness(p1.toFloat() / 1000f) }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    fun setColor(color: Int) {
        var results = arrayOf(0f, 0f, 0f).toFloatArray()
        Color.colorToHSV(color, results)

        hue = results[0]
        saturation = results[1]
        brightness = results[2]

        update()
    }

    fun setHue(hue: Float) {
        if (hue < 0f || hue > 360f) {
            throw InputMismatchException("Hue value must be >= 0 and <= 360. You sent $hue")
        }

        this.hue = hue
        update()
    }

    fun setSaturation(saturation: Float) {
        if (saturation < 0f || saturation > 1f) {
            throw InputMismatchException("Saturation value must be >= 0 and <= 1. You sent $saturation")
        }

        this.saturation = saturation
        update()
    }

    fun setBrightness(brightness: Float) {
        if (brightness < 0f || brightness > 1f) {
            throw InputMismatchException("Brightness value must be >= 0 and <= 1. You sent $brightness")
        }

        this.brightness = brightness
        update()
    }
}