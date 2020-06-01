package com.ngangavic.test.multipleimages

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ngangavic.test.R


class MultipleImagesActivity : AppCompatActivity() {

    private lateinit var buttonSelect: Button
    private lateinit var gridView: GridView
    val PICK_MULTIPLE_IMAGE = 1
    lateinit var encodedImage: String
    lateinit var encodedImagesList: List<String>
    lateinit var galleryAdapter: GalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_images)

        buttonSelect = findViewById(R.id.buttonSelect)
        gridView = findViewById(R.id.gridView)

        buttonSelect.setOnClickListener {
            val intent = Intent()
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_MULTIPLE_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (requestCode == PICK_MULTIPLE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
                //get image from data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                encodedImagesList = ArrayList()
                if (data.data != null) {
                    val imageUri = data.data
//                    val cursor=contentResolver.query(imageUri,filePathColumn,null,null,null)
                    val cursor = imageUri?.let { contentResolver.query(it, filePathColumn, null, null, null) }
                    cursor?.moveToFirst()
                    val columnIndex = cursor!!.getColumnIndex(filePathColumn[0])
                    encodedImage = cursor.getString(columnIndex)
                    cursor.close()
                    val uriArray = ArrayList<Uri>()
                    uriArray.add(imageUri)
                    galleryAdapter = GalleryAdapter(applicationContext, uriArray)
                    gridView.adapter = galleryAdapter
                    gridView.verticalSpacing = gridView.horizontalSpacing
                    val marginLayoutParams = gridView.layoutParams as ViewGroup.MarginLayoutParams
                    marginLayoutParams.setMargins(0, gridView.horizontalSpacing, 0, 0)
                } else {
                    if (data.clipData != null) {
                        val clipData = data.clipData
                        val uriArray = ArrayList<Uri>()

                        for (i in 0 until clipData!!.itemCount) {
                            val item = clipData.getItemAt(i)
                            val uri = item.uri
                            uriArray.add(uri)
                            val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
                            cursor!!.moveToPosition(i)
                            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                            encodedImage = cursor.getString(columnIndex)
                            (encodedImagesList as ArrayList<String>).add(encodedImage)
                            cursor.close()
                            galleryAdapter = GalleryAdapter(applicationContext, uriArray)
                            gridView.adapter = galleryAdapter
                            gridView.verticalSpacing = gridView.horizontalSpacing
                            val marginLayoutParams = gridView.layoutParams as ViewGroup.MarginLayoutParams
                            marginLayoutParams.setMargins(0, gridView.horizontalSpacing, 0, 0)
                        }
                    }
                }

            } else {
                Toast.makeText(applicationContext, "You haven't picked an image ", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}
