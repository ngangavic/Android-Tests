package com.ngangavic.test.excelparse

import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ngangavic.test.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class ExcelActivity : AppCompatActivity() {

    private lateinit var textViewData: TextView

    private lateinit var buttonChooseFile: Button
    private lateinit var buttonParse: Button
    private lateinit var buttonUpload: Button

    private lateinit var input: InputStream

    private lateinit var assetManager: AssetManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_excel)

        textViewData = findViewById(R.id.textViewData)

        buttonChooseFile = findViewById(R.id.buttonChooseFile)
        buttonParse = findViewById(R.id.buttonParse)
        buttonUpload = findViewById(R.id.buttonUpload)

        buttonParse.visibility=View.GONE
        buttonUpload.visibility=View.GONE

        buttonChooseFile.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.setType("*/*")
//            startActivityForResult(i, 200)
            startActivityForResult(Intent.createChooser(i, "Select File"), 200)
        }

        buttonParse.setOnClickListener {
//            parseFile()
            parseCSV()
        }

    }

    private fun parseCSV(){
        val resultList: MutableList<Any> = ArrayList()
        val reader = BufferedReader(InputStreamReader(input))
        try {
            textViewData.append("\n")

            val iterator = reader.lineSequence().iterator()
            while(iterator.hasNext()) {
                val line = iterator.next()
                Log.e("LINE", line.split(",").toString())
                resultList.add(line.split(","))

            }
            reader.close()

            val itr=resultList.iterator()
            if (itr.hasNext()){

            itr.forEach {
                val data=it.toString().replace("[","").replace("]","")

                val adm="ADM: "+data.substringBefore(",")+" "
                val name="NAME: "+data.substringAfter(",").substringBefore(",")+" "
                val classs="CLASS: "+data.substringAfter(",").substringAfter(",")+" "

                Log.e("ADM:",it.toString().substringBefore(","))
                Log.e("NAME:",it.toString().substringAfter(",").substringBefore(","))
                Log.e("CLASS",it.toString().substringAfter(",").substringAfter(","))

                textViewData.append(adm+name+classs+"\n")

            }

            }

        } catch (ex: IOException) {
            throw RuntimeException("Error in reading CSV file: $ex")
        } finally {
            try {
                input.close()
            } catch (e: IOException) {
                throw RuntimeException("Error while closing input stream: $e")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 200 && resultCode == RESULT_OK) {
            buttonParse.visibility=View.VISIBLE
            buttonUpload.visibility=View.VISIBLE

            val uri = data?.data
            input = uri?.let { contentResolver.openInputStream(it) }!!
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}