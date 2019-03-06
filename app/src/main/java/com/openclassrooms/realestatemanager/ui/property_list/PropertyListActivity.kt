package com.openclassrooms.realestatemanager.ui.property_list

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
    private var mDetailsPropertyFragment = PropertyDetailFragment()
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
                this.configureFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_maps -> {
                this.configureMapFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_mortgage_simulation -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @Suppress("PLUGIN_WARNING")
    private fun configureFragment(){
        if (view_tablet_guideline != null){
            this.setConstrainOfFragmentContainer(false)
            tablet_display_line.visibility = View.VISIBLE
            mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, PropertyListFragment()).commit()
        }else {
            mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, PropertyListFragment()).commit()
        }
    }

    @Suppress("PLUGIN_WARNING")
    private fun configureMapFragment(){
        if (view_tablet_guideline != null){
            mFragmentManager.beginTransaction().detach(mDetailsPropertyFragment).commit()
            this.setConstrainOfFragmentContainer(true)
            this.updateOptionsMenu(false)
            tablet_display_line.visibility = View.GONE
            mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, PropertyMapFragment()).commit()
        }else {
            mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, PropertyMapFragment()).commit()
        }
    }

    @Suppress("PLUGIN_WARNING")
    private fun setConstrainOfFragmentContainer(widenContainer: Boolean){
        val constraintSet = ConstraintSet()
        constraintSet.clone(view_tablet_constraint_layout)
        if (widenContainer){
            constraintSet.connect(main_fragment_container.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
            constraintSet.applyTo(view_tablet_constraint_layout)
        }else{
            constraintSet.connect(main_fragment_container.id, ConstraintSet.END, view_tablet_guideline.id, ConstraintSet.START)
            constraintSet.applyTo(view_tablet_constraint_layout)
        }
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

    fun configureDetailsPropertyFragment(property: Property){
        if (detail_of_the_property_container != null){
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val args = Bundle()
            args.putLong(PROPERTY_ID, property.mPropertyId)
            mDetailsPropertyFragment = PropertyDetailFragment()
            mDetailsPropertyFragment.arguments = args
            fragmentTransaction.replace(R.id.detail_of_the_property_container, mDetailsPropertyFragment)
            fragmentTransaction.commit()
            mPropertyId = property.mPropertyId
            mPropertyPrice = property.price
            mStateButtonEdit = true
            updateOptionsMenu(mStateButtonEdit)
        }else{
            val intent = Intent(this, PropertyDetailActivity::class.java)
            intent.putExtra(PROPERTY_ID, property.mPropertyId)
            startActivity(intent)
        }
    }

    private fun updateOptionsMenu(stateGroup: Boolean){
        mMenu.setGroupVisible(R.id.detail_menu_group, stateGroup)
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
