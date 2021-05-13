package com.joeshuff.kolorseekbarexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joeshuff.kolorseekbars.KolorCreator
import com.joeshuff.kolorseekbars.KolorPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = KolorPreferences(applicationContext, "hue_val", "sat_val", "bri_val" )

        KolorCreator(hue_seek, sat_seek, br_seek, preferences) {
            color_preview.setBackgroundColor(it)
        }

        clearStorageButton.setOnClickListener { KolorPreferences(applicationContext).clearStorage() }
    }
}
