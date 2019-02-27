package com.openclassrooms.realestatemanager.ui.property_research

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_property_search.*

class PropertyResearchActivity : AppCompatActivity() {

    private val surfaceBoard = arrayOf(0,100,200,300,400,500,750,1000,1250,1500,1750,2000,2500,3000)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_search)

        this.configureSpinner()
    }

    private fun configureSpinner(){
        val typePropertyAdapter = ArrayAdapter.createFromResource(this, R.array.type_property_array, android.R.layout.simple_spinner_item)
        typePropertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        type_of_property_spinner_search.adapter = typePropertyAdapter
        val surfaceMinAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, surfaceBoard)
        surfaceMinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        surface_mini_spinner_search.adapter = surfaceMinAdapter
        val surfaceMaxAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, surfaceBoard)
        surfaceMaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        surface_maxi_spinner_search.adapter = surfaceMaxAdapter
        surface_maxi_spinner_search.setSelection(13)
    }
}
