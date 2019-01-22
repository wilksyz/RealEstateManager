package com.openclassrooms.realestatemanager.ui.property_list

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.stetho.Stetho
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailFragment
import kotlinx.android.synthetic.main.activity_list_property.*

class PropertyListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_property)
        Stetho.initializeWithDefaults(this)

        this.configureFragment()
        this.configureDetailsPropertyFragment()

    }

    private fun configureFragment(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = PropertyListFragment()
        fragmentTransaction.add(R.id.main_fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun configureDetailsPropertyFragment(){
        var detailsPropertyFragment = supportFragmentManager.findFragmentById(R.id.details_of_the_property_container)

        if (detailsPropertyFragment == null && details_of_the_property_container != null){
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            detailsPropertyFragment = PropertyDetailFragment()
            fragmentTransaction.add(R.id.details_of_the_property_container, detailsPropertyFragment)
            fragmentTransaction.commit()
        }
    }
}
