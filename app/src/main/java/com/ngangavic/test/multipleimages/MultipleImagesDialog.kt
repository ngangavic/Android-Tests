package com.ngangavic.test.multipleimages

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ngangavic.test.R

class MultipleImagesDialog(private val filePaths: ArrayList<Uri>) : DialogFragment() {

    lateinit var root: View
    lateinit var gridView: GridView
    lateinit var textViewClose: TextView
    lateinit var galleryAdapter: GalleryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.dialog_multiple_images, container)
        gridView = root.findViewById(R.id.gridView)
        textViewClose = root.findViewById(R.id.textViewClose)
        textViewClose.setOnClickListener { dialog?.dismiss() }
        galleryAdapter = GalleryAdapter(root.context, filePaths)
        val marginLayoutParams = gridView.layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.setMargins(0, gridView.horizontalSpacing, 0, 0)
        gridView.adapter = galleryAdapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            galleryAdapter.removeItem(position)
            Toast.makeText(root.context, "Removed item at : $position", Toast.LENGTH_LONG).show()
        }

        return root
    }

    fun newInstance(): MultipleImagesDialog {
        return MultipleImagesDialog(filePaths)
    }
}