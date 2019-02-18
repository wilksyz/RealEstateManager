package com.openclassrooms.realestatemanager.ui.property_result_research

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PictureDataRepository
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository
import java.util.*

class PropertyResultOfResearchViewModel(private val mPropertyDataRepository: PropertyDataRepository, private val mPictureDataRepository: PictureDataRepository) : ViewModel() {

    // --- FOR PROPERTY ---

    // --- GET ---
    fun getPropertyResearch(typeProperty: String,
                            minSurface: Int,
                            maxSurface: Int,
                            doctor: List<Int>,
                            school: List<Int>,
                            hobbies: List<Int>,
                            transport: List<Int>,
                            parc: List<Int>,
                            store: List<Int>,
                            minDateOfSale: Date,
                            maxDateOfSale: Date,
                            saleStatus: Int,
                            minDateSold: Date,
                            maxDateSold: Date,
                            city: String,
                            pNumberOfPhotos: Int,
                            pMinPrice: Int,
                            pMaxPrice: Int): LiveData<List<Property>>{
        return mPropertyDataRepository.getPropertyResearch(typeProperty, minSurface, maxSurface, doctor, school, hobbies, transport, parc, store, minDateOfSale, maxDateOfSale, saleStatus, minDateSold, maxDateSold, city, pNumberOfPhotos, pMinPrice, pMaxPrice)
    }

    // --- FOR PICTURE ---

    // --- GET ---
    fun getPicture(propertyId: Long): LiveData<List<Picture>> {
        return mPictureDataRepository.getPicture(propertyId)
    }
}