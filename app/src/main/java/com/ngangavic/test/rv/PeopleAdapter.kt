package com.ngangavic.test.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
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
        val ac = RVActivity()

        holder.textViewName.text = p[position].name
        holder.textViewAge.text = p[position].age
        holder.linearLayout.setOnClickListener {
            //creating a popup menu
            val popup = PopupMenu(context, holder.linearLayout)
            //inflating menu from xml resource
            popup.inflate(R.menu.rv_menu)
            //adding click listener
            popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.action_delete -> {
                            ac.loadDelete(p[position].id.toString(), context)
                            p.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, itemCount)
                        }
                        R.id.action_edit -> {
                            Toast.makeText(context, "Edit item", Toast.LENGTH_SHORT).show()
                        }
                    }
                    return false
                }
            })
            //displaying the popup
            //displaying the popup
            popup.show()
        }
        holder.cardView.setOnClickListener {

        }

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