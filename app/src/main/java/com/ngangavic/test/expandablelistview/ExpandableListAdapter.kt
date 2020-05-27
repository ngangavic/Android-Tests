package com.ngangavic.test.expandablelistview

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.ngangavic.test.R


class ExpandableListAdapter(var context: Context, var listSubCounty: List<String>, var listWard: HashMap<String, List<String>>) : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return this.listSubCounty.get(groupPosition)
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listTitle = getGroup(groupPosition) as String
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_group, null)
        }
        val listTitleTextView = convertView!!.findViewById(R.id.listTitle) as TextView
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convertView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return this.listWard.get(this.listSubCounty[groupPosition])!!.size
//        return 1
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.listWard[this.listSubCounty[groupPosition]]!!.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val expandedListText = getChild(groupPosition, childPosition) as String
        if (convertView == null) {
            val layoutInflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item, null)
        }
        val expandedListTextView = convertView!!.findViewById(R.id.expandedListItem) as TextView
        expandedListTextView.text = expandedListText
        return convertView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return this.listSubCounty.size
    }
}