package com.ngangavic.test.firebasestorage

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ngangavic.test.R
import com.ngangavic.test.firebasestorage.Credetials.Companion.email
import com.ngangavic.test.firebasestorage.Credetials.Companion.password
import java.io.ByteArrayOutputStream
import java.io.IOException


class StorageActivity : AppCompatActivity() {

    lateinit var storage: FirebaseStorage //android-test
    lateinit var imageView: ImageView
    lateinit var buttonUpload: Button
    lateinit var buttonViewImages: Button
    lateinit var buttonPickImage: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        imageView = findViewById(R.id.imageView)
        buttonUpload = findViewById(R.id.buttonUpload)
        buttonViewImages = findViewById(R.id.buttonViewImages)
        buttonPickImage = findViewById(R.id.buttonPickImage)
        storage = Firebase.storage
        auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, "SignIn success", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, "SignIn failed", Toast.LENGTH_LONG).show()
                    }
                }

        buttonPickImage.setOnClickListener { chooseImage() }

        buttonUpload.setOnClickListener { uploadImage() }
    }

    private fun chooseImage() {
        val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                imageView.setImageBitmap(bitmap);
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        val storageRef = storage.reference.child("android-test/vic")
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        val data = baos.toByteArray()

        var uploadTask = storageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
        }.addOnSuccessListener {
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_LONG).show()
        }
    }
}
