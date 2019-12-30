package com.ngangavic.test.rv

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R


class PeopleHolder(itemView: View, private val cartObject: List<People>) :
        RecyclerView.ViewHolder(itemView) {
    var textViewName: TextView
    var textViewAge: TextView
    var linearLayout: LinearLayout
    var cardView: CardView

    init {
        textViewName = itemView.findViewById(R.id.textViewName) as TextView
        textViewAge = itemView.findViewById(R.id.textViewAge) as TextView
        linearLayout = itemView.findViewById(R.id.linearLayout) as LinearLayout
        cardView = itemView.findViewById(R.id.cardView) as CardView
    }
}