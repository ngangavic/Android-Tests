package com.ngangavic.test.search

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ngangavic.test.R
import java.util.*

class SearchActivity : AppCompatActivity() {

    lateinit var recyclerviewSearch: RecyclerView
    private lateinit var searchList: MutableList<Search>
    private lateinit var recyclerViewAdapter: SearchAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerviewSearch = findViewById(R.id.recyclerviewSearch)
        recyclerviewSearch.layoutManager = LinearLayoutManager(applicationContext)
        searchList = ArrayList()

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        handleIntent(intent)

    }

    private fun getData(char: String) {
        val fetchMessageQuery = database.child("search")
        fetchMessageQuery.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("CHAT ERROR", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                searchList.clear()

                for (postSnapshot in p0.children) {
                    Log.e("DATA", postSnapshot.value.toString())
                    val key = postSnapshot.key.toString()
                    Log.e("KEY", key)
                    if (postSnapshot.value.toString().contains(char)) {
                        Log.e("VALUE", postSnapshot.value.toString())
                        searchList.add(Search(postSnapshot.value.toString()))
                    }
                }
                recyclerViewAdapter = SearchAdapter(this@SearchActivity, searchList as ArrayList<Search>)
                recyclerViewAdapter.notifyDataSetChanged()
                recyclerviewSearch.adapter = recyclerViewAdapter
                recyclerviewSearch.visibility = View.VISIBLE
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (!newText.isNullOrEmpty()) {
                    Toast.makeText(this@SearchActivity, newText.toString(), Toast.LENGTH_SHORT).show()
                    getData(newText.toString())
                }else{
                    Toast.makeText(this@SearchActivity, "Empty", Toast.LENGTH_SHORT).show()
                }
                return true
            }

        })

        return true
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            Toast.makeText(this, query, Toast.LENGTH_SHORT).show()
        }
    }
}