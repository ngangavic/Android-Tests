package com.ngangavic.test.sharedprefs

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

        val sharedPref = applicationContext.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE)


        buttonSave.setOnClickListener {
            if (TextUtils.isEmpty(editTextName.text.toString())||TextUtils.isEmpty(editTextYear.text.toString())||TextUtils.isEmpty(editTextAge.text.toString())||TextUtils.isEmpty(editTextTown.text.toString())||TextUtils.isEmpty(editTextPhone.text.toString())){
                Toast.makeText(applicationContext,"Fill all the fields",Toast.LENGTH_SHORT).show()
            }else {
                with(sharedPref.edit()) {
                    putString(getString(R.string.name), editTextName.text.toString())
                    putString(getString(R.string.year), editTextYear.text.toString())
                    putString(getString(R.string.age), editTextAge.text.toString())
                    putString(getString(R.string.town), editTextTown.text.toString())
                    putString(getString(R.string.phone), editTextPhone.text.toString())
                    commit()
                    Toast.makeText(applicationContext,"Saved",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
