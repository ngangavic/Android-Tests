package com.ngangavic.test.multipleimages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ngangavic.test.R
import droidninja.filepicker.FilePickerBuilder
import droidninja.filepicker.FilePickerConst


class MultipleImagesActivity : AppCompatActivity() {

    private lateinit var buttonSelect: Button
    private lateinit var buttonUpload: Button
    private lateinit var gridView: GridView
    lateinit var filePaths: ArrayList<Uri>
    lateinit var galleryAdapter: GalleryAdapter
    lateinit var buttonAlert: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_images)

        buttonSelect = findViewById(R.id.buttonSelect)
        buttonUpload = findViewById(R.id.buttonUpload)
        buttonAlert = findViewById(R.id.buttonAlert)
        gridView = findViewById(R.id.gridView)
        filePaths = ArrayList()
        buttonSelect.setOnClickListener {
            filePaths.clear()
            FilePickerBuilder.instance
                    .setMaxCount(5) //optional
                    .setSelectedFiles(filePaths) //optional
                    .setActivityTheme(R.style.LibAppTheme) //optional
                    .pickPhoto(this)
        }

        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            galleryAdapter.removeItem(position)
            Toast.makeText(applicationContext, "Removed item at : $position", Toast.LENGTH_LONG).show()
        }

        buttonAlert.setOnClickListener {
            val multipleImagesDialog = MultipleImagesDialog(filePaths).newInstance()
            multipleImagesDialog.isCancelable = false
            multipleImagesDialog.show(supportFragmentManager, "dialog grid view")
        }

        buttonUpload.setOnClickListener {  }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FilePickerConst.REQUEST_CODE_PHOTO -> {
                val dataList = data!!.getParcelableArrayListExtra<Uri>(FilePickerConst.KEY_SELECTED_MEDIA)

                if (resultCode == RESULT_OK && data != null) {
                    Log.d("DATALIST: ", data.toString())
                    for (path in dataList) {
                        Log.d("DATAPATH: ", listOf(path).toString())
                        filePaths.addAll(listOf(path))
                    }
                    galleryAdapter = GalleryAdapter(applicationContext, filePaths)
                    gridView.verticalSpacing = gridView.horizontalSpacing
                    val marginLayoutParams = gridView.layoutParams as ViewGroup.MarginLayoutParams
                    marginLayoutParams.setMargins(0, gridView.horizontalSpacing, 0, 0)
                    gridView.adapter = galleryAdapter
                }


            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
