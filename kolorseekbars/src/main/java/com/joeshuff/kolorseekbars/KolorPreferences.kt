package com.joeshuff.kolorseekbars

import android.content.Context
import android.content.SharedPreferences

class KolorPreferences(
    context: Context,
    private val huePreferenceKey: String? = null,
    private val saturationPreferenceKey: String? = null,
    private val brightnessPreferenceKey: String? = null
) {

    var preferences: SharedPreferences = context.getSharedPreferences("kolor-preferences", Context.MODE_PRIVATE)

    fun update(hue: Float, sat: Float, bri: Float) {
        huePreferenceKey?.let { preferences.edit().putFloat(it, hue).commit() }
        saturationPreferenceKey?.let { preferences.edit().putFloat(it, sat).commit() }
        brightnessPreferenceKey?.let { preferences.edit().putFloat(it, bri).commit() }
    }

    fun hue(): Float? = huePreferenceKey?.let { preferences.getFloat(it, KolorCreator.DEFAULT_HUE) }
    fun saturation(): Float? = saturationPreferenceKey?.let { preferences.getFloat(it, KolorCreator.DEFAULT_SATURATION) }
    fun brightness(): Float? = brightnessPreferenceKey?.let { preferences.getFloat(it, KolorCreator.DEFAULT_BRIGHTNESS) }
}