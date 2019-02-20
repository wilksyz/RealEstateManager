package com.openclassrooms.realestatemanager.ui.property_form.property_edit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_form.PropertyEditForms
import kotlinx.android.synthetic.main.activity_property_create.*

private const val STATE_PICTURE_LIST = "state picture list"
private const val PROPERTY_ID: String = "property id"

class PropertyEditActivity : PropertyEditForms() {

    private lateinit var mPropertyEditViewModel: PropertyEditViewModel
    private lateinit var mProperty: Property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val propertyId = getPropertyId()
        this.configureViewModel()
        this.configureSpinner()
        this.getProperty(propertyId)
        this.getPicture(propertyId)
        configureGridRecyclerView()
        configureClickGridRecyclerView()
        add_photo_button.setOnClickListener {
            onCreateDialog()
        }
    }

    private fun getPropertyId(): Long{
        return intent.getLongExtra(PROPERTY_ID, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_activity_property_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.finish -> {

            true
        }else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun getProperty(propertyId: Long){
        this.mPropertyEditViewModel.getProperty(propertyId).observe(this, Observer { property ->
            if (property != null) {
                mProperty = property
                updateUI(property)
                setInterestPoint(property)
            }
        })
    }

    private fun updateUI(property: Property){
        surface_edit_text.text = SpannableStringBuilder(property.surface.toString())
        price_edit_text.text =SpannableStringBuilder(property.price.toString())
        number_of_room_edit_text.text = SpannableStringBuilder(property.numberOfRooms)
        description_property_edit_text.text = SpannableStringBuilder(property.descriptionProperty)
    }

    private fun setInterestPoint(property: Property){
        radioButton_doctor.isChecked = property.interestPoint.doctor
        radioButton_hobbies.isChecked = property.interestPoint.hobbies
        radioButton_public_transport.isChecked = property.interestPoint.transport
        radioButton_school.isChecked = property.interestPoint.school
        radioButton_stores.isChecked = property.interestPoint.store
        radioButton_parc.isChecked = property.interestPoint.parc
    }

    private fun getPicture(propertyId: Long){
        this.mPropertyEditViewModel.getPicture(propertyId).observe(this, Observer { list ->
            if (list != null){
                mAdapterRecycler.updateData(list)
                mPictureList = list as java.util.ArrayList<Picture>
            }
        })
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this)
        this.mPropertyEditViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyEditViewModel::class.java)
    }

    private fun configureSpinner(){
        val typePropertyAdapter = ArrayAdapter.createFromResource(this, R.array.type_property_array, android.R.layout.simple_spinner_item)
        typePropertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        type_of_property_spinner.adapter = typePropertyAdapter
        val estateAgentAdapter = ArrayAdapter.createFromResource(this, R.array.estate_agent_array, android.R.layout.simple_spinner_item)
        estateAgentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estate_agent_spinner.adapter = estateAgentAdapter
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
