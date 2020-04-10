package com.ngangavic.test.firebasestorage

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ngangavic.test.R

class ViewImageActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.enterTransition= Slide()
        window.exitTransition=Explode()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)

        imageView = findViewById(R.id.imageView)
        progressBar = findViewById(R.id.progressBar)
//        imageView.visibility = View.GONE

        val url = intent.getStringExtra("image_url")

        Log.d("SENT_URL",url)

        Glide.with(applicationContext).load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        imageView.setImageResource(R.drawable.ic_image)
                        progressBar.visibility = View.GONE
//                        imageView.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
//                        imageView.visibility = View.VISIBLE
                        return false
                    }
                }).into(imageView)
    }
}
