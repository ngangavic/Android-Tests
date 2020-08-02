package com.ngangavic.test.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R
import java.util.*

class SearchAdapter(private var context: Context, private val search: ArrayList<Search>) :
        RecyclerView.Adapter<SearchHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val viewHolder: SearchHolder?
        val layoutView =
                LayoutInflater.from(parent.context).inflate(R.layout.row_search, parent, false)
        viewHolder = SearchHolder(layoutView)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return search.size
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.textViewName.text = search[position].name

    }
}