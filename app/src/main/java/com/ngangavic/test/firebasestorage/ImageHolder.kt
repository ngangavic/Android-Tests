package com.ngangavic.test.firebasestorage

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R

class ImageHolder(itemView: View, private val imageObject: List<Image>) :
        RecyclerView.ViewHolder(itemView) {

    var imageView: ImageView
    var textView: TextView

    init {
        imageView = itemView.findViewById(R.id.imageView) as ImageView
        textView = itemView.findViewById(R.id.textView) as TextView
    }
}