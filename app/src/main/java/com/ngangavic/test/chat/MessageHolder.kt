package com.ngangavic.test.chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R

class MessageHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    //    var textViewName: TextView=itemView.findViewById(R.id.textViewName) as TextView
    var textViewTime: TextView = itemView.findViewById(R.id.textViewTime) as TextView
    var textViewMessage: TextView = itemView.findViewById(R.id.textViewMessage) as TextView

}