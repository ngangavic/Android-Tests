package com.ngangavic.test.rv

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ngangavic.test.R
import org.json.JSONArray
import java.util.ArrayList
import java.util.HashMap

class RVActivity : AppCompatActivity() {
    lateinit var rv_test:RecyclerView
    lateinit var progressBar:ProgressBar
    private var peopleList: MutableList<People>? = null
    private var recyclerViewAdapter: PeopleAdapter? = null
    lateinit var queue: RequestQueue
    var url:String = "http://192.168.1.102/test/android.php"
    var p = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)
        rv_test=findViewById(R.id.rv_test)
        rv_test.layoutManager = LinearLayoutManager(applicationContext)
        progressBar=findViewById(R.id.progressBar)
        peopleList = ArrayList()
        queue = Volley.newRequestQueue(applicationContext)

        progressBar.visibility= View.VISIBLE
        rv_test.visibility= View.GONE
        loadData(applicationContext)
    }


    private fun loadData(context: Context) {

        val str = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->
                    Log.d("DATA:", response.toString())

                    val jsonArray = JSONArray(response)

                    for (i in 0 until jsonArray.length()) {

                        //getting product object from json array
                        val product = jsonArray.getJSONObject(i)

                        //adding the product to product list
                        val people = People(
                                product.getString("id"),
                                product.getString("name"),
                                product.getString("age")
                        )
                        peopleList?.add(people)

                    }
                    recyclerViewAdapter = PeopleAdapter(
                            context,
                            peopleList as ArrayList<People>
                    )
                    rv_test.adapter = recyclerViewAdapter
                    progressBar.visibility= View.GONE
                    rv_test.visibility= View.VISIBLE

                },
                Response.ErrorListener { error ->
                    error.printStackTrace()

                }) {
            override fun getParams(): Map<String, String> {
                val param = HashMap<String, String>()
                return param
            }
        }
        str.setRetryPolicy(
                DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        )
        queue.add(str)
    }


}
