package com.ngangavic.test.expandablelistview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.fragment.app.DialogFragment
import com.ngangavic.test.R

class ExpandableListDialog : DialogFragment() {

    lateinit var expandableListView: ExpandableListView
    lateinit var expandableListAdapter: ExpandableListAdapter
    lateinit var expandableListSubCounty: MutableList<String>
    private lateinit var expandableListWard: HashMap<String, List<String>>
    lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.dialog_expandable_list, container)
        expandableListView = root.findViewById(R.id.expandableListView) as ExpandableListView
        prepareListData()
        expandableListAdapter = ExpandableListAdapter(requireActivity(), expandableListSubCounty, expandableListWard)

        expandableListView.setAdapter(expandableListAdapter)

        expandableListView.setOnGroupExpandListener(object : ExpandableListView.OnGroupExpandListener {
            var prev = -1
            override fun onGroupExpand(groupPosition: Int) {
                if (groupPosition != prev) {
                    expandableListView.collapseGroup(prev)
                    prev = groupPosition
                }
            }

        })
        return root
    }

    fun newInstance(): ExpandableListDialog {
        return ExpandableListDialog()
    }

    private fun prepareListData() {
        expandableListWard = HashMap()
        expandableListSubCounty = ArrayList()
        // Adding child data
        expandableListSubCounty.add("Kilifi North")
        expandableListSubCounty.add("Kilifi South")
        expandableListSubCounty.add("Malindi")

        // Adding child data
        val kilifiNorth = ArrayList<String>()
        kilifiNorth.add("KN 1")
        kilifiNorth.add("KN 2")
        kilifiNorth.add("KN 3")
        kilifiNorth.add("KN 4")
        kilifiNorth.add("KN 5")
        kilifiNorth.add("KN 6")
        kilifiNorth.add("KN 7")
        kilifiNorth.add("KN 8")
        kilifiNorth.add("KN 9")
        kilifiNorth.add("KN 10")

        val kilifiSouth = ArrayList<String>()
        kilifiSouth.add("KS 1")
        kilifiSouth.add("KS 2")
        kilifiSouth.add("KS 3")
        kilifiSouth.add("KS 4")
        kilifiSouth.add("KS 5")
        kilifiSouth.add("KS 6")
        kilifiSouth.add("KS 7")
        kilifiSouth.add("KS 8")
        kilifiSouth.add("KS 9")
        kilifiSouth.add("KS 10")

        val malindi = ArrayList<String>()
        malindi.add("ML 1")
        malindi.add("ML 2")
        malindi.add("ML 3")
        malindi.add("ML 4")
        malindi.add("ML 5")
        malindi.add("ML 6")
        malindi.add("ML 7")
        malindi.add("ML 8")
        malindi.add("ML 9")
        malindi.add("ML 10")

        expandableListWard[expandableListSubCounty[0]] = kilifiNorth // Header, Child data
        expandableListWard[expandableListSubCounty[1]] = kilifiSouth
        expandableListWard[expandableListSubCounty[2]] = malindi
//        expandableListWard.put("kilifi North",kilifiNorth)
//        expandableListWard.put("kilifi South",kilifiSouth)
//        expandableListWard.put("Malindi",malindi)
    }
}