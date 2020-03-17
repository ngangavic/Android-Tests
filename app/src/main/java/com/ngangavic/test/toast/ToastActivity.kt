package com.ngangavic.test.toast

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ngangavic.test.R

class ToastActivity : AppCompatActivity() {

    lateinit var buttonGravity: Button
    lateinit var buttonCustomToast: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast)

        buttonGravity = findViewById(R.id.buttonGravity)
        buttonCustomToast = findViewById(R.id.buttonCustomToast)
        buttonGravity.setOnClickListener {
            gravityToast()
        }
        buttonCustomToast.setOnClickListener {
            customToast()
        }
    }

    private fun customToast() {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
        toast.view = layout
        toast.show()
    }

    private fun gravityToast() {
        val toast = Toast.makeText(applicationContext, "Hello! Gravity is set to LEFT", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.LEFT, 0, 0)
        toast.show()
    }

}
