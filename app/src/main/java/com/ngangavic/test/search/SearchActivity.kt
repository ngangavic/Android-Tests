package com.ngangavic.test.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ngangavic.test.R

class SearchActivity : AppCompatActivity() {

    lateinit var recyclerviewSearch:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerviewSearch=findViewById(R.id.recyclerviewSearch)

        handleIntent(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)

        val searchView= menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(this@SearchActivity,newText.toString(),Toast.LENGTH_SHORT).show()
                return true
            }

        })

        return true
    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            //use the query to search your data somehow
            Toast.makeText(this,query,Toast.LENGTH_SHORT).show()
        }
    }
}