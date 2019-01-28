package com.openclassrooms.realestatemanager.ui.property_create

import android.arch.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PictureDataRepository
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor

class PropertyCreateViewModel(private val mPropertyDataRepository: PropertyDataRepository, private val mPictureDataRepository: PictureDataRepository, private val mExecutor: Executor): ViewModel() {

    // --- CREATE ---
    fun createProperty(property: Property, pictureList: ArrayList<Picture>) {
        mExecutor.execute {mPropertyDataRepository.createProperty(property, pictureList)}
    }
}