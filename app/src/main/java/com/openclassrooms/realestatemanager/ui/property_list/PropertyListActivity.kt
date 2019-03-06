package com.openclassrooms.realestatemanager.ui.property_list

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.facebook.stetho.Stetho
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailActivity
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailFragment
import com.openclassrooms.realestatemanager.ui.property_form.property_create.PropertyCreateActivity
import com.openclassrooms.realestatemanager.ui.property_form.property_edit.PropertyEditActivity
import com.openclassrooms.realestatemanager.ui.property_maps.PropertyMapFragment
import com.openclassrooms.realestatemanager.ui.property_mortgage.PropertyMortgageActivity
import com.openclassrooms.realestatemanager.ui.property_research.PropertyResearchActivity
import kotlinx.android.synthetic.main.activity_list_property.*



private const val PROPERTY_ID: String = "property id"
private const val PROPERTY_PRICE: String = "property price"
private const val VISIBILITY_EDIT_BUTTON:String = "visibility edit button"

class PropertyListActivity : AppCompatActivity() {

    private val mFragmentManager = supportFragmentManager
    private val mFragmentTransaction = mFragmentManager.beginTransaction()
    private lateinit var mMenu: Menu
    private var mPropertyId: Long? = null
    private var mPropertyPrice: Int? = null
    private var mStateButtonEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_property)
        Stetho.initializeWithDefaults(this)

        bottom_Navigation_View.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        this.configureFragment()

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when(menuItem.itemId){
            R.id.action_home -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.action_maps -> {
                mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, PropertyMapFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_mortgage_simulation -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_activity_property_list, menu)
        menu.setGroupVisible(R.id.detail_menu_group, mStateButtonEdit)
        mMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.create_property -> {
            val intent= Intent(this, PropertyCreateActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.research_property -> {
            val intent = Intent(this, PropertyResearchActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.edit -> {
            val intent = Intent(this, PropertyEditActivity::class.java)
            intent.putExtra(PROPERTY_ID, mPropertyId)
            startActivity(intent)
            true
        }
        R.id.mortgage -> {
            val intent = Intent(this, PropertyMortgageActivity::class.java)
            intent.putExtra(PROPERTY_PRICE, mPropertyPrice)
            startActivity(intent)
            true
        }else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun configureFragment(){
        val fragment = PropertyListFragment()
        mFragmentTransaction.add(R.id.main_fragment_container, fragment)
        mFragmentTransaction.commit()
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
            mPropertyId = property.mPropertyId
            mPropertyPrice = property.price
            mStateButtonEdit = true
            updateOptionsMenu()
        }else{
            val intent = Intent(this, PropertyDetailActivity::class.java)
            intent.putExtra(PROPERTY_ID, property.mPropertyId)
            startActivity(intent)
        }
    }

    private fun updateOptionsMenu(){
        mMenu.setGroupVisible(R.id.detail_menu_group, mStateButtonEdit)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putAll(outState)
            putBoolean(VISIBILITY_EDIT_BUTTON, mStateButtonEdit)
            mPropertyId?.let { putLong(PROPERTY_ID, it) }
            mPropertyPrice?.let { putInt(PROPERTY_PRICE, it) }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null){
            mStateButtonEdit = savedInstanceState.getBoolean(VISIBILITY_EDIT_BUTTON)
            mPropertyId = savedInstanceState.getLong(PROPERTY_ID)
            mPropertyPrice = savedInstanceState.getInt(PROPERTY_PRICE)
        }
    }
}
