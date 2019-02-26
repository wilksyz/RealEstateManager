package com.openclassrooms.realestatemanager.ui.property_form.property_edit

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PictureDataRepository
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository
import java.util.*
import java.util.concurrent.Executor

class PropertyEditViewModel(private val mPropertyDataRepository: PropertyDataRepository, private val mPictureDataRepository: PictureDataRepository , private val mExecutor: Executor): ViewModel() {

    // --- GET ---
    fun getProperty(propertyId: Long): LiveData<Property> {
        return mPropertyDataRepository.getProperty(propertyId)
    }

    fun getPicture(propertyId: Long): LiveData<List<Picture>> {
        return mPictureDataRepository.getPicture(propertyId)
    }

    // ---UPDATE ---
    fun updatePropertyAndPictures(property: Property, pictureList: ArrayList<Picture>){
        mExecutor.execute { mPropertyDataRepository.updatePropertyAndPictures(property, pictureList) }
    }
}