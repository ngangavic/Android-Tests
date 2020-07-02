package com.ngangavic.test.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.ngangavic.test.R

class MessagesAdapter(private val messages: ArrayList<Message>) :
        RecyclerView.Adapter<MessageHolder>() {

    private val me = 100
    private var auth: FirebaseAuth=FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val viewHolder: MessageHolder
        viewHolder = if (viewType == me) {
            val layoutView =
                    LayoutInflater.from(parent.context).inflate(R.layout.row_chat_me, parent, false)
            MessageHolder(layoutView)
        } else {
            val layoutView =
                    LayoutInflater.from(parent.context).inflate(R.layout.row_chat_other, parent, false)
            MessageHolder(layoutView)
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.textViewMessage.text=messages[position].message
//        holder.textViewName.text=messages[position].person
        holder.textViewTime.text=messages[position].time
    }

    override fun getItemViewType(position: Int): Int {
        if (messages[position].uid == auth.currentUser!!.uid) {
            return me
        }
        return position
    }
}