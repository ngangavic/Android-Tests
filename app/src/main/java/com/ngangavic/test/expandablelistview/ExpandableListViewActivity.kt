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
    lateinit var expandableListSubCounty: List<String>

    //    lateinit var expandableListWard:List<String>
    private lateinit var expandableListWard: HashMap<String, List<String>>
    lateinit var tasks: List<String>

    //Firebase storage references
    private lateinit var mFirebasedatabase: FirebaseDatabase

    //Task database references:
    private lateinit var mTasksDatabaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable_list_view)

        expandableListView = findViewById(R.id.expandableListView)
        mFirebasedatabase = FirebaseDatabase.getInstance();
        mTasksDatabaseReference = mFirebasedatabase.getReference().child("android-test").child("location")
        Log.d("DATA", mTasksDatabaseReference.toString())
        expandableListWard = HashMap<String, List<String>>()
        expandableListSubCounty = ArrayList()
        tasks = ArrayList()
        getData()
//        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
//        expandableListDetail = ExpandableListDataPump.getData();
//        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = ExpandableListAdapter(this, expandableListSubCounty, expandableListWard)
//        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setAdapter(expandableListAdapter)
        expandableListAdapter.notifyDataSetChanged()

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

//                tasks=ArrayList()

                Log.d("DATA", "onChildAdded: dataSnapshot.getChildren: " + p0.getChildren());
                Log.d("DATA", "onChildAdded: KEY" + p0.getKey() + " value " + p0.getValue().toString());
            }

            override fun onChildRemoved(p0: DataSnapshot) {}

        })
    }

}
