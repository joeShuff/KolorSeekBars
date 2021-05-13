package com.joeshuff.kolorseekbars

import android.content.Context
import android.content.SharedPreferences

class KolorPreferences(
    context: Context,
    private val huePreferenceKey: String = "hue-val",
    private val saturationPreferenceKey: String = "sat-val",
    private val brightnessPreferenceKey: String = "bri-val"
) {

    var preferences: SharedPreferences = context.getSharedPreferences("kolor-preferences", Context.MODE_PRIVATE)

    fun update(hue: Float, sat: Float, bri: Float) {
        huePreferenceKey?.let { preferences.edit().putFloat(it, hue).commit() }
        saturationPreferenceKey?.let { preferences.edit().putFloat(it, sat).commit() }
        brightnessPreferenceKey?.let { preferences.edit().putFloat(it, bri).commit() }
    }

    fun clearStorage() {
        preferences.edit().clear().commit()
    }

    fun hue(): Float? = huePreferenceKey?.let {
        val loaded = preferences.getFloat(it, -1f)
        if (loaded == -1f) return null else loaded
    }

    fun saturation(): Float? = saturationPreferenceKey?.let {
        val loaded = preferences.getFloat(it, -1f)
        if (loaded == -1f) return null else loaded
    }

    fun brightness(): Float? = brightnessPreferenceKey?.let {
        val loaded = preferences.getFloat(it, -1f)
        if (loaded == -1f) return null else loaded
    }
}