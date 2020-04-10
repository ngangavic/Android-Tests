package com.ngangavic.test.firebasestorage

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R

class ImageHolder(itemView: View, private val imageObject: List<Image>) :
        RecyclerView.ViewHolder(itemView) {

    var imageView: ImageView
    var textView: TextView
    var progressBar:ProgressBar
    var constraint_layout:ConstraintLayout

    init {
        imageView = itemView.findViewById(R.id.imageView) as ImageView
        textView = itemView.findViewById(R.id.textViewName) as TextView
        progressBar = itemView.findViewById(R.id.progressBar) as ProgressBar
        constraint_layout = itemView.findViewById(R.id.constraint_layout) as ConstraintLayout
    }
}