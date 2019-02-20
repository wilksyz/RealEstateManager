package com.openclassrooms.realestatemanager.ui.property_details

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.property_form.property_edit.PropertyEditActivity

private const val PROPERTY_ID: String = "property id"

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_activity_property_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.edit -> {
            startActivityEditProperty()
            true
        }else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun startActivityEditProperty(){
        val propertyId: Long? = intent?.getLongExtra(PROPERTY_ID, 0)
        if (propertyId != null){
            val intent = Intent(this, PropertyEditActivity::class.java)
            intent.putExtra(PROPERTY_ID, propertyId)
            startActivity(intent)
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
