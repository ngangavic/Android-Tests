package com.ngangavic.test.firebasestorage

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ngangavic.test.R

class ViewActivity : AppCompatActivity() {

    lateinit var imageView:ImageView
    lateinit var buttonUpload:Button
    lateinit var progressBar:ProgressBar
    lateinit var storage: FirebaseStorage //android-test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        imageView = findViewById(R.id.imageView)
        buttonUpload = findViewById(R.id.buttonUpload)
        progressBar = findViewById(R.id.progressBar)
        storage = Firebase.storage

        imageView.visibility = View.GONE
        buttonUpload.visibility = View.GONE

        buttonUpload.setOnClickListener { startActivity(Intent(applicationContext, StorageActivity::class.java)) }
//
////        val url = "https://firebasestorage.googleapis.com/v0/b/appfirebase-e9697.appspot.com/o/android-test%2Fvic?alt=media&token=7bc37514-cc82-48b2-a89a-83e0d239cf45"
//
//        val storageRef = storage.reference.child("android-test/vic.jpg").downloadUrl.addOnSuccessListener(object : On)
//
//        Glide.with(applicationContext).load(url)
//                .listener(object : RequestListener<Drawable> {
//
//                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                        progressBar.visibility = View.GONE
//                        imageView.visibility = View.VISIBLE
//                        buttonUpload.visibility = View.VISIBLE
//                        return false
//                    }
//
//                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                        progressBar.visibility = View.GONE
//                        imageView.visibility = View.VISIBLE
//                        buttonUpload.visibility = View.VISIBLE
//                        return false
//                    }
//                })
//                .into(imageView)
    }
}
