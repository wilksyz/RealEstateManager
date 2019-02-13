package com.openclassrooms.realestatemanager.ui.property_maps

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository

class PropertyMapsViewModel(private val mPropertyDataRepository: PropertyDataRepository): ViewModel() {

    fun getAllProperty(): LiveData<List<Property>> {
        return mPropertyDataRepository.getAllProperty()
    }
}