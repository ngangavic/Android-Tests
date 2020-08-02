package com.ngangavic.test.chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R

class RecipientHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    val textViewName: TextView = itemView.findViewById(R.id.textViewName) as TextView
}