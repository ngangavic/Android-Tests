package com.ngangavic.test.sharedprefs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.ngangavic.test.R

class SharedPrefsActivity : AppCompatActivity() {
    lateinit var editTextName:EditText
    lateinit var editTextYear:EditText
    lateinit var editTextAge:EditText
    lateinit var editTextTown:EditText
    lateinit var editTextPhone:EditText
    lateinit var buttonSave:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_prefs)
        editTextName=findViewById(R.id.editTextName)
        editTextYear=findViewById(R.id.editTextYear)
        editTextAge=findViewById(R.id.editTextAge)
        editTextTown=findViewById(R.id.editTextTown)
        editTextPhone=findViewById(R.id.editTextPhone)
        buttonSave=findViewById(R.id.buttonSave)
    }
}
