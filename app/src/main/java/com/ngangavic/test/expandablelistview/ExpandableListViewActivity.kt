package com.ngangavic.test.expandablelistview

import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.ngangavic.test.R


class ExpandableListViewActivity : AppCompatActivity() {

    lateinit var expandableListView: ExpandableListView
    lateinit var expandableListAdapter: ExpandableListAdapter
    lateinit var expandableListSubCounty: MutableList<String>
    private lateinit var expandableListWard: HashMap<String, List<String>>
    lateinit var tasks: List<String>

    //Firebase storage references
    private lateinit var mFirebasedatabase: FirebaseDatabase

    //Task database references:
    private lateinit var mTasksDatabaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable_list_view)

        expandableListView = findViewById(R.id.expandableListView) as ExpandableListView
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mTasksDatabaseReference = mFirebasedatabase.getReference().child("android-test").child("location")
        Log.d("DATA", mTasksDatabaseReference.toString())
        expandableListWard = HashMap()
        expandableListSubCounty = ArrayList()
        tasks = ArrayList()
//        getData()
        prepareListData()

        expandableListAdapter = ExpandableListAdapter(this, expandableListSubCounty, expandableListWard)

        expandableListView.setAdapter(expandableListAdapter)
//        expandableListAdapter.notifyDataSetChanged()

        expandableListView!!.setOnGroupExpandListener { groupPosition ->
            Toast.makeText(
                    applicationContext,
                    (expandableListSubCounty as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
            ).show()
        }
        expandableListView!!.setOnGroupCollapseListener { groupPosition ->
            Toast.makeText(
                    applicationContext,
                    (expandableListSubCounty as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
            ).show()
        }
        expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            Toast.makeText(
                    applicationContext,
                    "Clicked: " + (expandableListSubCounty as ArrayList<String>)[groupPosition] + " -> " + expandableListWard[(
                            expandableListSubCounty as
                                    ArrayList<String>
                            )
                            [groupPosition]]!!.get(
                            childPosition
                    ),
                    Toast.LENGTH_SHORT
            ).show()
            false
        }
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

    private fun getData() {
        mTasksDatabaseReference.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                Log.d("ChildChanged", p0.value.toString())
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                for (d in p0.children) {
                    //Getting Value from 1 to 10 in ArrayList(tasks)
//                    tasks.
                    Log.d("DATA CHILD", d.value.toString())
                    tasks.toMutableList().add(d.value.toString())
//                    expandableListWard.put(d.value.toString())
                }
                expandableListWard.put(p0.key.toString(), tasks)
//                p0.key?.let { expandableListWard.put(it, tasks) }
                expandableListSubCounty.toMutableList().add(p0.key.toString())
//                p0.key?.let { expandableListSubCounty.toMutableList().add(it) }
                expandableListAdapter.notifyDataSetChanged()
//                tasks=ArrayList()

                Log.d("DATA", "onChildAdded: dataSnapshot.getChildren: " + p0.getChildren());
                Log.d("DATA", "onChildAdded: KEY" + p0.getKey() + " value " + p0.getValue().toString());
            }

            override fun onChildRemoved(p0: DataSnapshot) {}

        })
    }

}
