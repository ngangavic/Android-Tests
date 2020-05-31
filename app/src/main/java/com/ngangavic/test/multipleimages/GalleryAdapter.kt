package com.ngangavic.test.multipleimages

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.ngangavic.test.R

class GalleryAdapter(val context: Context, private val uriArray: ArrayList<Uri>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.grid_view_item, parent, false)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        imageView.setImageURI(uriArray.get(position))
        return itemView
    }

    override fun getItem(position: Int): Any {
        return uriArray.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return uriArray.size
    }
}