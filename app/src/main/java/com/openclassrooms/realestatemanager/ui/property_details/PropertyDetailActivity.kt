package com.openclassrooms.realestatemanager.ui.property_details

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.openclassrooms.realestatemanager.R

class PropertyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_property)

        this.configureFragment()
    }

    private fun configureFragment(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = PropertyDetailFragment()
        fragmentTransaction.add(R.id.details_of_the_property_container, fragment)
        fragmentTransaction.commit()
    }
}
