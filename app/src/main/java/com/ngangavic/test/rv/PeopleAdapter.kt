package com.ngangavic.test.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R

class PeopleAdapter(private var context: Context, private val p: ArrayList<People>) :
    RecyclerView.Adapter<PeopleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleHolder {
        val viewHolder: PeopleHolder?
        val layoutView =
            LayoutInflater.from(parent.context).inflate(R.layout.rv_row, parent, false)
        viewHolder = PeopleHolder(layoutView, p)
        return viewHolder
    }

    override fun onBindViewHolder(holder: PeopleHolder, position: Int) {

        holder.textViewName.text = p[position].name
        holder.textViewAge.text = p[position].age

    }

    override fun getItemCount(): Int {
        return this.p.size
    }

    fun removeItem(position: Int) {
        p.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, p.size)
    }
}