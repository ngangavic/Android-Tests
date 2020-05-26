package com.ngangavic.test.spinner

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.ngangavic.test.R


class SpinnerActivity : AppCompatActivity() {

    lateinit var spinnerCounty: Spinner
    lateinit var spinnerWard: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner)

        spinnerCounty = findViewById(R.id.spinnerCounty)
        spinnerWard = findViewById(R.id.spinnerWard)

        subCountySpinner()
    }

    fun subCountySpinner() {
        val Dadapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.sub_county))
        Dadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerCounty.setAdapter(Dadapter)
        spinnerCounty.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDistrict = position

                // do yourself a favor and intoduce an empty choice for 7;
                if (parent?.getItemAtPosition(selectedDistrict).toString()!="Select County") {
                    wardSpinner(parent?.getItemAtPosition(selectedDistrict).toString())
                }
//                wardSpinner(selectedDistrict)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        )
    }

    private fun getDistrictResourceId(subCounty: String): Int {
        var resId = R.array.kilifi_north
        when (subCounty) {
            "Kilifi North" -> resId = R.array.kilifi_north
            "Kilifi South" -> resId = R.array.kilifi_south
            "Kilifi Central" -> resId = R.array.kilifi_central
            "Malindi Subcounty" -> resId = R.array.malindi_sub_county
        }
        return resId
    }

    fun wardSpinner(subCounty: String) {
        spinnerWard.visibility = View.VISIBLE
        spinnerCounty.visibility=View.GONE
        val resId = getDistrictResourceId(subCounty)

        val Cadapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, getResources().getStringArray(resId))
        Cadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerWard.setAdapter(Cadapter)
        spinnerWard.performClick()
        spinnerWard.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        )
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        spinnerCounty.visibility=View.VISIBLE
        spinnerWard.visibility=View.GONE
    }
}
