package com.ngangavic.test.firebasestorage

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ngangavic.test.R
import java.io.IOException


class StorageActivity : AppCompatActivity() {

    lateinit var storage: FirebaseStorage //android-test
    lateinit var imageView:ImageView
    lateinit var buttonUpload:Button
    lateinit var buttonViewImages:Button
    lateinit var buttonPickImage:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        imageView=findViewById(R.id.imageView)
        buttonUpload=findViewById(R.id.buttonUpload)
        buttonViewImages=findViewById(R.id.buttonViewImages)
        buttonPickImage=findViewById(R.id.buttonPickImage)
        storage = Firebase.storage

        buttonPickImage.setOnClickListener { chooseImage() }
    }

    private fun chooseImage(){
        val intent=Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try{
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                imageView.setImageBitmap(bitmap);
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
        }
}
