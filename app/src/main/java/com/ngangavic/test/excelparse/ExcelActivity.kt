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
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.openxml4j.opc.OPCPackage
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.util.PackageHelper.open
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.InputStream


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
            parseFile()
        }

    }


    private fun parseFile() {
        try {
            val pkg = OPCPackage.open(input)
        val myFileSystem = POIFSFileSystem(input)
        val myWorkBook = XSSFWorkbook(pkg)
        val mySheet = myWorkBook.getSheetAt(0)

        val rowIterator=mySheet.rowIterator()
        var rowNo=0
        textViewData.append("\n")
        while (rowIterator.hasNext()){
           val myRow=rowIterator.next()
            if (rowNo!=0){
                val cellIterator=myRow.cellIterator()
                var colNo=0
                var adm="";var name="";var classs=""
                while (cellIterator.hasNext()){
                    val myCell=cellIterator.next()
                    if (colNo==0){
                        adm=myCell.toString()
                    }else if (colNo==1){
                        name=myCell.toString()
                    }else if (colNo==2) {
                    adm=myCell.toString()
                    }
                    colNo++

                }
                textViewData.append("ADM: "+adm+" NAME: "+name+" CLASS "+classs)
            }
            rowNo++
        }
        }catch (e:Exception){
            Log.e("ERROR: ",e.message.toString())
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