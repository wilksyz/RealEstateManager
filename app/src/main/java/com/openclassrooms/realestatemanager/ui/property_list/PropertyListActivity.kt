package com.openclassrooms.realestatemanager.ui.property_list

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.facebook.stetho.Stetho
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_create.PropertyCreateActivity
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailActivity
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailFragment
import com.openclassrooms.realestatemanager.ui.property_maps.PropertyMapsActivity
import com.openclassrooms.realestatemanager.ui.property_result_research.PropertyResultOfResearchActivity
import kotlinx.android.synthetic.main.activity_list_property.*

private const val PROPERTY_ID: String = "property id"
class PropertyListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_property)
        Stetho.initializeWithDefaults(this)

        this.configureFragment()
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
        R.id.maps_view -> {
            val intent = Intent(this, PropertyMapsActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.research_property -> {
            val intent = Intent(this, PropertyResultOfResearchActivity::class.java)
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

    fun configureDetailsPropertyFragment(property: Property){
        if (detail_of_the_property_container != null){
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val detailsPropertyFragment = PropertyDetailFragment()
            val args = Bundle()
            args.putLong(PROPERTY_ID, property.mPropertyId)
            detailsPropertyFragment.arguments = args
            fragmentTransaction.replace(R.id.detail_of_the_property_container, detailsPropertyFragment)
            fragmentTransaction.commit()
        }else{
            val intent = Intent(this, PropertyDetailActivity::class.java)
            intent.putExtra(PROPERTY_ID, property.mPropertyId)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putAll(outState)
        }
        super.onSaveInstanceState(outState)
    }
}
