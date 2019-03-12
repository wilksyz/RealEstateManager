package com.openclassrooms.realestatemanager.ui.property_form.property_edit

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_form.PropertyEditForms
import kotlinx.android.synthetic.main.activity_property_form.*


private const val STATE_PICTURE_LIST = "state picture list"
private const val PROPERTY_ID: String = "property id"

class PropertyEditActivity : PropertyEditForms() {

    private lateinit var mPropertyEditViewModel: PropertyEditViewModel
    private lateinit var mProperty: Property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val propertyId = getPropertyId()
        this.configureViewModel()
        this.getProperty(propertyId)
        configureGridRecyclerView()
        this.getPicture(propertyId)
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
            updateProperty()
            true
        }else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun updateProperty(){
        if (mPictureList.size > 0){
            retrieveInformationEntered()
            this.mPropertyEditViewModel.updatePropertyAndPictures(mProperty, mPictureList)
            finish()
        }else{
            Toast.makeText(this, getString(R.string.you_must_select_at_least_one_picture), Toast.LENGTH_LONG).show()
        }
    }

    private fun retrieveInformationEntered(){
        mProperty.surface = if (surface_edit_text.text.toString().isNotEmpty()) Integer.parseInt(surface_edit_text.text.toString()) else 0
        mProperty.price = if (price_edit_text.text.toString().isNotEmpty()) Integer.parseInt(price_edit_text.text.toString()) else 0
        mProperty.numberOfRooms = number_of_room_edit_text.text.toString()
        mProperty.descriptionProperty = description_property_edit_text.text.toString()
        mProperty.address = retrieveAddress()
        mProperty.typeProperty = when(type_of_property_spinner.selectedItemPosition){
            0 -> Property.TYPE_HOUSE
            1 -> Property.TYPE_LOFT
            2 -> Property.TYPE_CASTLE
            3 -> Property.TYPE_APARTMENT
            4 -> Property.TYPE_RANCH
            5 -> Property.TYPE_PENTHOUSE
            6 -> Property.TYPE_MANOR
            else -> -1
        }
        mProperty.estateAgent = estate_agent_spinner.selectedItem.toString()
        mProperty.interestPoint = retrieveInterestPoint()
        mProperty.numberOfPhotos = mPictureList.size
    }

    private fun getProperty(propertyId: Long){
        this.mPropertyEditViewModel.getProperty(propertyId).observe(this, Observer { property ->
            if (property != null) {
                mProperty = property
                this.updateUI(property)
                this.setInterestPoint(property)
                this.configureSpinner()
                this.setAddress(property)
            }
        })
    }

    private fun updateUI(property: Property){
        if (property.surface != 0) surface_edit_text.text = SpannableStringBuilder(property.surface.toString())
        if (property.price != 0) price_edit_text.text =SpannableStringBuilder(property.price.toString())
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

    private fun setAddress(property: Property){
        number_edit_text.text = SpannableStringBuilder(property.address.number)
        street_edit_text.text = SpannableStringBuilder(property.address.street)
        postal_code_edit_text.text = SpannableStringBuilder(property.address.postCode)
        city_edit_text.text = SpannableStringBuilder(property.address.city)
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
        type_of_property_spinner.setSelection(mProperty.typeProperty)
        val estateAgentAdapter = ArrayAdapter.createFromResource(this, R.array.estate_agent_array, android.R.layout.simple_spinner_item)
        estateAgentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estate_agent_spinner.adapter = estateAgentAdapter
        estate_agent_spinner.setSelection(getIndex(estate_agent_spinner, mProperty.estateAgent))
    }

    private fun getIndex(spinner: Spinner, spinnerString: String): Int {
        var index = 0

        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == spinnerString) {
                index = i
            }
        }
        return index
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
