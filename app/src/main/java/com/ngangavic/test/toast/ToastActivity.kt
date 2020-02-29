package com.ngangavic.test.toast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import com.ngangavic.test.R

class ToastActivity : AppCompatActivity() {

    lateinit var buttonGravity:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast)

        buttonGravity=findViewById(R.id.buttonGravity)
        buttonGravity.setOnClickListener {
            gravityToast()
        }
    }

    private fun gravityToast(){
        val toast=Toast.makeText(applicationContext,"Hello! Gravity is set to LEFT",Toast.LENGTH_LONG)
        toast.setGravity(Gravity.LEFT,0,0)
        toast.show()
    }

}
