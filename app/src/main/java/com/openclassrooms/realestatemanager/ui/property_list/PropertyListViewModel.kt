package com.openclassrooms.realestatemanager.ui.property_list

import java.util.concurrent.Executor
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PictureDataRepository
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository

class PropertyListViewModel(private val mPropertyDataRepository: PropertyDataRepository, private val mPictureDataRepository: PictureDataRepository, private val mExecutor: Executor) : ViewModel() {

    // --- FOR PROPERTY ---

    // --- GET ---
    fun getAllProperty(): LiveData<List<Property>> {
        return mPropertyDataRepository.getAllProperty()
    }

    fun getProperty(propertyId: Long): LiveData<Property>{
        return mPropertyDataRepository.getProperty(propertyId)
    }

    // --- CREATE ---
    fun createProperty(property: Property) {
        mExecutor.execute {mPropertyDataRepository.createProperty(property)}
    }

    // --- DELETE ---
    fun deleteProperty(propertyId: Long) {
        mExecutor.execute { mPropertyDataRepository.deleteProperty(propertyId) }
    }

    // --- UPDATE ---
    fun updateItem(property: Property) {
        mExecutor.execute { mPropertyDataRepository.updateProperty(property) }
    }

    // --- FOR PICTURE ---

    // --- GET ---
    fun getPicture(pictureId: Long): LiveData<List<Picture>> {
        return mPictureDataRepository.getPicture(pictureId)
    }

    // --- CREATE ---
    fun createPicture(picture: Picture) {
        mPictureDataRepository.createPicture(picture)
    }

    // --- DELETE ---
    fun deletePicture(pictureId: Long) {
        mPictureDataRepository.deletePicture(pictureId)
    }

    fun deleteAllPictureFromProperty(propertyId: Long) {
        mPictureDataRepository.deleteAllPictureFromProperty(propertyId)
    }
}