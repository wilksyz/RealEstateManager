package com.openclassrooms.realestatemanager.ui.base_property_list.property_result_research

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailActivity
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailFragment
import com.openclassrooms.realestatemanager.ui.property_form.property_edit.PropertyEditActivity
import kotlinx.android.synthetic.main.activity_property_result_of_research.*

private const val PROPERTY_ID: String = "property id"
private const val VISIBILITY_EDIT_BUTTON:String = "visibility edit button"

class PropertyResultOfResearchActivity : AppCompatActivity() {

    private val mFragmentManager = supportFragmentManager
    private var mDetailsPropertyFragment = PropertyDetailFragment()
    private var mPropertyId: Long? = null
    private var mStateButtonEdit: Boolean = false
    private lateinit var mMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_result_of_research)
        this.configureFragment()
    }

    private fun configureFragment(){
        mFragmentManager.beginTransaction().replace(R.id.result_research_fragment_container, PropertyResultResearchFragment()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_activity_property_result_research, menu)
        menu.setGroupVisible(R.id.detail_menu_group, mStateButtonEdit)
        mMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.edit -> {
            val intent = Intent(this, PropertyEditActivity::class.java)
            intent.putExtra(PROPERTY_ID, mPropertyId)
            startActivity(intent)
            true
        }else -> {
            super.onOptionsItemSelected(item)
        }
    }

    fun configureDetailsPropertyFragment(property: Property){
        if (detail_of_the_property_research_container != null){
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val args = Bundle()
            args.putLong(PROPERTY_ID, property.mPropertyId)
            mDetailsPropertyFragment = PropertyDetailFragment()
            mDetailsPropertyFragment.arguments = args
            fragmentTransaction.replace(R.id.detail_of_the_property_container, mDetailsPropertyFragment)
            fragmentTransaction.commit()
            mPropertyId = property.mPropertyId
            mStateButtonEdit = true
            updateOptionsMenu(mStateButtonEdit)
        }else{
            val intent = Intent(this, PropertyDetailActivity::class.java)
            intent.putExtra(PROPERTY_ID, property.mPropertyId)
            startActivity(intent)
        }
    }

    private fun updateOptionsMenu(stateGroup: Boolean){
        mMenu.setGroupVisible(R.id.detail_menu_result_research_group, stateGroup)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putAll(outState)
            putBoolean(VISIBILITY_EDIT_BUTTON, mStateButtonEdit)
            mPropertyId?.let { putLong(PROPERTY_ID, it) }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null){
            mStateButtonEdit = savedInstanceState.getBoolean(VISIBILITY_EDIT_BUTTON)
            mPropertyId = savedInstanceState.getLong(PROPERTY_ID)
        }
    }
}
