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
                            isNearDoctor: Boolean,
                            isNearSchool: Boolean,
                            isNearHobbies: Boolean,
                            isNearTransport: Boolean,
                            isNearParc: Boolean,
                            isNearStore: Boolean,
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

    fun getPropertyResearchSold(typeProperty: String,
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
        return mPropertyDataRepository.getPropertyResearchSold(typeProperty, minSurface, maxSurface, doctor, school, hobbies, transport, parc, store, minDateOfSale, maxDateOfSale, saleStatus, minDateSold, maxDateSold, city, pNumberOfPhotos, pMinPrice, pMaxPrice)
    }

    fun getAllProperty(): LiveData<List<Property>> {
        return mPropertyDataRepository.getAllProperty()
    }


    // --- FOR PICTURE ---

    // --- GET ---
    fun getPicture(propertyId: Long): LiveData<List<Picture>> {
        return mPictureDataRepository.getPicture(propertyId)
    }
}