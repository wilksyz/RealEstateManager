package com.openclassrooms.realestatemanager.ui.property_form.property_create

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.ui.property_form.PropertyEditForms
import kotlinx.android.synthetic.main.activity_property_create.*

private const val STATE_PICTURE_LIST = "state picture list"

class PropertyCreateActivity : PropertyEditForms() {

    private lateinit var mPropertyCreateViewModel: PropertyCreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureSpinner()
        this.configureViewModel()
        configureGridRecyclerView()
        configureClickGridRecyclerView()
        add_photo_button.setOnClickListener {
            onCreateDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_activity_property_create, menu)
        return true
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this)
        this.mPropertyCreateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyCreateViewModel::class.java)
    }

    private fun configureSpinner(){
        val typePropertyAdapter = ArrayAdapter.createFromResource(this, R.array.type_property_array, android.R.layout.simple_spinner_item)
        typePropertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        type_of_property_spinner.adapter = typePropertyAdapter
        val estateAgentAdapter = ArrayAdapter.createFromResource(this, R.array.estate_agent_array, android.R.layout.simple_spinner_item)
        estateAgentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estate_agent_spinner.adapter = estateAgentAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.create -> {
            this.createProperty()
            true
        }else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun createProperty(){
        val property = retrieveInformationEntered()
        this.mPropertyCreateViewModel.createProperty(property, mPictureList)
        onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putAll(outState)
            putParcelableArrayList(STATE_PICTURE_LIST, mPictureList)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState.run {
            mPictureList = savedInstanceState?.getParcelableArrayList<Picture>(STATE_PICTURE_LIST) as ArrayList<Picture>
            mAdapterRecycler.updateData(mPictureList)
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}
