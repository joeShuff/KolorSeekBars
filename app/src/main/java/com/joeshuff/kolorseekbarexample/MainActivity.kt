package com.joeshuff.kolorseekbarexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joeshuff.kolorseekbars.KolorCreator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        KolorCreator(hue_seek, sat_seek, br_seek) {
            color_preview.setBackgroundColor(it)
        }
    }
}
