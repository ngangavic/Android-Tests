package com.ngangavic.test.multipleimages.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.JobIntentService
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*


class UploadPhotoService : JobIntentService() {

    private lateinit var storageRef: StorageReference
    private val JOB_ID = 2

    override fun onCreate() {
        super.onCreate()
        storageRef = Firebase.storage.reference
        Log.e("UPLOAD", "Service started")
    }

    override fun onHandleWork(intent: Intent) {
        storageRef.child("android-test/multi-images/" + UUID.randomUUID().toString())
        val data = Uri.parse("file://" + intent.getStringExtra("photo"))
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)

//        val uploadTask = storageRef.putBytes(byteArrayOutputStream.toByteArray())
        val uploadTask = storageRef.putFile(data)
        uploadTask.addOnProgressListener { taskSnapshot ->
            Log.e("UPLOAD", taskSnapshot.bytesTransferred.toString() + "/" + taskSnapshot.totalByteCount.toString())
        }
                .addOnSuccessListener { Log.e("UPLOAD", "Upload success") }
                .addOnFailureListener { Log.e("UPLOAD", "Upload failed") }
    }

    fun enqueueWork(context: Context, intent: Intent) {
        enqueueWork(context, UploadPhotoService::class.java, JOB_ID, intent)
    }


}