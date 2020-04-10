package com.ngangavic.test.firebasestorage

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ngangavic.test.R


class ImageAdapter(private var context: Context, private val images: ArrayList<Image>) :
        RecyclerView.Adapter<ImageHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val viewHolder: ImageHolder
        val layoutView =
                LayoutInflater.from(parent.context).inflate(R.layout.image_row, parent, false)
        viewHolder = ImageHolder(layoutView, images)
        return viewHolder
    }

    override fun getItemCount(): Int {
       return images.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {

        holder.textView.text = images[position].name
        holder.imageView.setOnClickListener {
            val intent = Intent(context, ViewImageActivity::class.java)
            intent.putExtra("image_url", images[position].url)
            context.startActivity(intent)
        }
        Glide.with(context).load(images[position].url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        holder.imageView.setImageResource(R.drawable.ic_image)
                        holder.progressBar.visibility=View.GONE
                        holder.imageView.visibility=View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        holder.progressBar.visibility=View.GONE
                        holder.imageView.visibility=View.VISIBLE
                        return false
                    }
                }).into(holder.imageView)
    }


}