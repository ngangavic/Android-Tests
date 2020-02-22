package com.ngangavic.test.download

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.ngangavic.test.R

class PDFDownloadActivity : AppCompatActivity() {

    lateinit var editTextUrl:EditText
    lateinit var buttonDownload:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfdownload)

        editTextUrl=findViewById(R.id.editTextUrl)
        buttonDownload=findViewById(R.id.buttonDownload)
    }
}
