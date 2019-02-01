package com.openclassrooms.realestatemanager.ui.property_list

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.facebook.stetho.Stetho
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.ui.property_create.PropertyCreateActivity
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailActivity
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailFragment
import kotlinx.android.synthetic.main.activity_list_property.*

class PropertyListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_property)
        Stetho.initializeWithDefaults(this)

        this.configureFragment()
        //this.configureDetailsPropertyFragment()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.create_property -> {
            val intent= Intent(this, PropertyCreateActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
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
