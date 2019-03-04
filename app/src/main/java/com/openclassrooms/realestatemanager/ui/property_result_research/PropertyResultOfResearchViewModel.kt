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
    fun getPropertyResearch(isNearTypeProperty: List<Int>,
                            isNearMinSurface: Int,
                            isNearMaxSurface: Int,
                            isNearDoctor: List<Boolean>,
                            isNearSchool: List<Boolean>,
                            isNearHobbies: List<Boolean>,
                            isNearTransport: List<Boolean>,
                            isNearParc: List<Boolean>,
                            isNearStore: List<Boolean>,
                            isNearCity: String,
                            isNearNumberOfPhotos: Int,
                            isNearMinPrice: Int,
                            isNearMaxPrice: Int,
                            isNearMinDateOfSale: Date,
                            isNearMaxDateOfSale: Date,
                            isNearSaleStatus: Boolean,
                            isNearMinDateSold: Date?,
                            isNearMaxDateSold: Date?): LiveData<List<Property>> {
        return mPropertyDataRepository.getPropertyResearch(isNearTypeProperty,isNearMinSurface,isNearMaxSurface,isNearDoctor,isNearSchool,isNearHobbies,isNearTransport,isNearParc,isNearStore,isNearCity,isNearNumberOfPhotos,isNearMinPrice,isNearMaxPrice,isNearMinDateOfSale,isNearMaxDateOfSale,isNearSaleStatus,isNearMinDateSold,isNearMaxDateSold)
    }

    // --- FOR PICTURE ---

    // --- GET ---
    fun getPicture(propertyId: Long): LiveData<List<Picture>> {
        return mPictureDataRepository.getPicture(propertyId)
    }
}