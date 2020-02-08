package com.ngangavic.test.service

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.ngangavic.test.R

class ServicesActivity : AppCompatActivity() {
    lateinit var buttonStart: Button
    lateinit var buttonStop: Button
    lateinit var serviceLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        buttonStart = findViewById(R.id.buttonStart)
        buttonStop = findViewById(R.id.buttonStop)
        serviceLayout = findViewById(R.id.serviceLayout)

        buttonStart.setOnClickListener {
            startService(Intent(this, MyService::class.java))
        }

        buttonStop.setOnClickListener {
            stopService(Intent(this, MyService::class.java))
        }

    }


}
