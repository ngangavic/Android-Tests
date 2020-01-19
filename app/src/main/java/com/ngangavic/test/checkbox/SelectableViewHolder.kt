package com.ngangavic.test.checkbox

import android.R
import android.graphics.Color
import android.view.View
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView


class SelectableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val MULTI_SELECTION = 2
    val SINGLE_SELECTION = 1
    var textView: CheckedTextView? = null
    var mItem: SelectableItem? = null
    var itemSelectedListener: OnItemSelectedListener? = null

    fun SelectableViewHolder(view: View, listener: OnItemSelectedListener) {
//        super(view)
        itemSelectedListener = listener
        textView = view.findViewById<View>(R.id.checked_text_item) as CheckedTextView
        textView!!.setOnClickListener {
            if (mItem!!.isSelected() && itemViewType === MULTI_SELECTION) {
                setChecked(false)
            } else {
                setChecked(true)
            }
            itemSelectedListener!!.onItemSelected(mItem)
        }
    }

    fun setChecked(value: Boolean) {
        if (value) {
            textView!!.setBackgroundColor(Color.LTGRAY)
        } else {
            textView!!.background = null
        }
        mItem!!.setSelected(value)
        textView!!.isChecked = value
    }

    interface OnItemSelectedListener {
        fun onItemSelected(item: SelectableItem?)
    }
}