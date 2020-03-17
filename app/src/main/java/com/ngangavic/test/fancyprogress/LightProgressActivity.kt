package com.ngangavic.test.fancyprogress

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bitvale.lightprogress.LightProgress
import com.ngangavic.test.R

class LightProgressActivity : AppCompatActivity() {
    lateinit var light: LightProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_progress)
        light = findViewById(R.id.light)
        light.on()
    }
}
