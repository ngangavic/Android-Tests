package com.ngangavic.test.rv

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R


class PeopleHolder(itemView: View, private val cartObject: List<People>) :
        RecyclerView.ViewHolder(itemView) {
    var textViewName: TextView
    var textViewAge: TextView

    init {
        textViewName = itemView.findViewById(R.id.textViewName) as TextView
        textViewAge = itemView.findViewById(R.id.textViewAge) as TextView
    }
}