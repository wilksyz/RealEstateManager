package com.openclassrooms.realestatemanager.ui.property_list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PictureDataRepository
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor

class PropertyListViewModel(private val mPropertyDataRepository: PropertyDataRepository, private val mPictureDataRepository: PictureDataRepository, private val mExecutor: Executor) : ViewModel() {

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