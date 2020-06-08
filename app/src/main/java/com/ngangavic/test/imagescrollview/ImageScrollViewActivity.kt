package com.ngangavic.test.imagescrollview

import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.ngangavic.test.R
import com.squareup.picasso.Picasso


class ImageScrollViewActivity : AppCompatActivity() {

    lateinit var viewFlipper: ViewFlipper
    lateinit var urlList: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_scroll_view)

        viewFlipper = findViewById(R.id.viewFlipper)

        urlList = ArrayList()
        urlList.add("https://zdnet3.cbsistatic.com/hub/i/2019/03/16/e118b0c5-cf3c-4bdb-be71-103228677b25/android-logo.png")
        urlList.add("http://2.bp.blogspot.com/-7hXkXVR8T3Y/UcVfHYJVFrI/AAAAAAAAAdo/CFxe5TK1Jgc/s400/device-2013-06-22-134830.png")
        urlList.add("http://www.apnatutorials.com/img/android-viewflipper.png")

        Log.d("URL SIZE", urlList.size.toString())

        for (i in 0 until urlList.size) {
            Log.d("URL ", urlList[i])
            val imageView = ImageView(applicationContext)
            Picasso.get()
                    .load(urlList[i])
                    .into(imageView)
            viewFlipper.addView(imageView)
        }

        val animIn: Animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val animOut: Animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)
        // set the animation type's to ViewFlipper
        // set the animation type's to ViewFlipper
        viewFlipper.setInAnimation(animIn)
        viewFlipper.setOutAnimation(animOut)
        // set interval time for flipping between views
        // set interval time for flipping between views
        viewFlipper.setFlipInterval(3000)
        // set auto start for flipping between views
        // set auto start for flipping between views
        viewFlipper.setAutoStart(true)

    }
}