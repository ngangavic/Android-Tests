package com.ngangavic.test.excelparse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.ngangavic.test.R

class ExcelActivity : AppCompatActivity() {

    private lateinit var textViewData:TextView

    private lateinit var buttonChooseFile:Button
    private lateinit var buttonParse:Button
    private lateinit var buttonUpload:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excel)

        textViewData=findViewById(R.id.textViewData)

        buttonChooseFile=findViewById(R.id.buttonChooseFile)
        buttonParse=findViewById(R.id.buttonParse)
        buttonUpload=findViewById(R.id.buttonUpload)
    }
}