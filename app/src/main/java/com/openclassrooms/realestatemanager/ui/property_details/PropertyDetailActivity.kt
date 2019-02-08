package com.openclassrooms.realestatemanager.ui.property_details

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R

class PropertyDetailActivity : AppCompatActivity() {

    private lateinit var fragment: PropertyDetailFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_property)

        if (savedInstanceState == null){
            this.configureFragment()
        }else {
            fragment = supportFragmentManager.findFragmentById(R.id.details_of_the_property_container) as PropertyDetailFragment
        }
    }

    private fun configureFragment(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragment = PropertyDetailFragment()
        fragmentTransaction.add(R.id.details_of_the_property_container, fragment)
        fragmentTransaction.commit()
    }
}
