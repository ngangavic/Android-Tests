package com.ngangavic.test.firebasestorage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.ngangavic.test.R
import java.util.*

class ViewActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var buttonUpload: Button

    //    lateinit var progressBar:ProgressBar
    lateinit var storage: FirebaseStorage //android-test
    private var imagesList: MutableList<Image>? = null
    private var recyclerViewAdapter: ImageAdapter? = null
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        recyclerView = findViewById(R.id.recyclerView)
        buttonUpload = findViewById(R.id.buttonUpload)
//        progressBar = findViewById(R.id.progressBar)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
//        progressBar = findViewById(R.id.progressBar)
        imagesList = ArrayList()
        storage = Firebase.storage
        database = Firebase.database.reference

//        recyclerView.visibility = View.GONE
//        buttonUpload.visibility = View.GONE

        buttonUpload.setOnClickListener { startActivity(Intent(applicationContext, StorageActivity::class.java)) }

        fetchData()
    }

    private fun fetchData() {
        val myTopPostsQuery = database.child("android-test")

        myTopPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                imagesList?.clear()
                Log.d("DATA", dataSnapshot.childrenCount.toString())
                if (dataSnapshot.childrenCount.toString() == "0") {
//                    progressBar.visibility = View.GONE
                } else {
                    for (postSnapshot in dataSnapshot.children) {
                        Log.d("URLS", postSnapshot.child("url").value.toString())
                        val house = Image(
                                postSnapshot.child("name").value.toString(),
                                postSnapshot.child("url").value.toString()
                        )
                        imagesList?.add(house)
                    }
                    recyclerViewAdapter = ImageAdapter(
                            applicationContext,
                            imagesList as ArrayList<Image>
                    )
                    recyclerViewAdapter?.notifyDataSetChanged()
                    recyclerView.adapter = recyclerViewAdapter
//                    progressBar.visibility = View.GONE
//                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("HOME FRAGMENT", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }


}
