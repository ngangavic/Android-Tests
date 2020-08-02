package com.ngangavic.test.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R

class RecipientAdapter(private val username: ArrayList<Recipient>, private val userSelected: SelectedRecipient) :
        RecyclerView.Adapter<RecipientHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipientHolder {
        val viewHolder: RecipientHolder

        val layoutView =
                LayoutInflater.from(parent.context).inflate(R.layout.chat_recipient_row, parent, false)
        viewHolder = RecipientHolder(layoutView)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return username.size
    }

    override fun onBindViewHolder(holder: RecipientHolder, position: Int) {
        holder.textViewName.text = username[position].username
        holder.textViewName.setOnClickListener {
            userSelected.setUsername(username[position].username)
            userSelected.setRecipientId(username[position].recipientId)

        }
    }
}