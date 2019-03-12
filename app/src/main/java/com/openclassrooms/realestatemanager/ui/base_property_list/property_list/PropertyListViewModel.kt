package com.openclassrooms.realestatemanager.ui.base_property_list.property_list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PictureDataRepository
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository

class PropertyListViewModel(private val mPropertyDataRepository: PropertyDataRepository, private val mPictureDataRepository: PictureDataRepository) : ViewModel() {

    // --- FOR PROPERTY ---

    // --- GET ---
    fun getAllProperty(): LiveData<List<Property>> {
        return mPropertyDataRepository.getAllProperty()
    }

    // --- FOR PICTURE ---

    // --- GET ---
    fun getPicture(propertyId: Long): LiveData<List<Picture>> {
        return mPictureDataRepository.getPicture(propertyId)
    }
}