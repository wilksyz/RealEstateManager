package com.openclassrooms.realestatemanager.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.Injection
import com.openclassrooms.realestatemanager.Property
import com.openclassrooms.realestatemanager.PropertyViewModel

import com.openclassrooms.realestatemanager.R
/**
 * A simple [Fragment] subclass.
 *
 */
class MainFragment : Fragment() {

    private var itemViewModel: PropertyViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_main, container, false)


        //createProperty(Property("Manoir",650_000,300,27))
        return v
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this.context!!)
        this.itemViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel::class.java)
    }

    // Get all properties in database
    private fun getProperties() {
        val nameObserver = Observer<List<Property>> { newName ->
            // Update the UI, in this case, a TextView.
            val property = newName!![0]
            val string = "id: "+ property.id+" room: "+ property.numberOfRooms+" prix: "+property.price

        }

        this.itemViewModel!!.getProperty().observe(this, nameObserver)
    }

    // Create a new property
    private fun createProperty(property: Property) {
        this.itemViewModel!!.createProperty(property)
    }

    // Delete an property
    private fun deleteProperty(property: Property) {
        this.itemViewModel!!.deleteProperty(property.id)
    }

    // Update an property
    private fun updateProperty(property: Property) {
        this.itemViewModel!!.updateItem(property)
    }
}
