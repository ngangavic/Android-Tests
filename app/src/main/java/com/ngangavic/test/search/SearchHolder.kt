package com.ngangavic.test.search

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R

class SearchHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    var textViewName: TextView = itemView.findViewById(R.id.textViewName)

}