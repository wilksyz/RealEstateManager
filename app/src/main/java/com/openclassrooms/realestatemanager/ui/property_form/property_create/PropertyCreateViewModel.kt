package com.openclassrooms.realestatemanager.ui.property_form.property_create

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor

class PropertyCreateViewModel(private val mPropertyDataRepository: PropertyDataRepository, private val mExecutor: Executor): ViewModel() {

    // --- CREATE ---
    fun createProperty(property: Property, pictureList: ArrayList<Picture>, context: Context) {
        mExecutor.execute {mPropertyDataRepository.createProperty(property, pictureList, context)}
    }
}