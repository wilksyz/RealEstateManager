package com.openclassrooms.realestatemanager.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.persistance.PropertyDao
import java.util.*

class PropertyDataRepository(private val mPropertyDao: PropertyDao) {

    // --- GET ---
    fun getAllProperty(): LiveData<List<Property>> {
        return this.mPropertyDao.getAllProperty()
    }

    fun getProperty(propertyId: Long): LiveData<Property>{
        return  this.mPropertyDao.getPropertyFromId(propertyId)
    }

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
                            isNearMinDateSold: Date,
                            isNearMaxDateSold: Date): LiveData<List<Property>> {
        return this.mPropertyDao.getPropertyResearch(isNearTypeProperty,isNearMinSurface,isNearMaxSurface,isNearDoctor,isNearSchool,isNearHobbies,isNearTransport,isNearParc,isNearStore,isNearCity,isNearNumberOfPhotos,isNearMinPrice,isNearMaxPrice,isNearMinDateOfSale,isNearMaxDateOfSale,isNearSaleStatus,isNearMinDateSold,isNearMaxDateSold)
    }

    // --- CREATE ---
    fun createProperty(property: Property, pictureList: ArrayList<Picture>, context: Context) {
        mPropertyDao.createProperty(property, pictureList, context)
    }

    // --- UPDATE ---
    fun updateProperty(property: Property) {
        mPropertyDao.updateProperty(property)
    }

    fun updatePropertyAndPictures(property: Property, pictureList: ArrayList<Picture>){
        mPropertyDao.updatePropertyAndPictures(property, pictureList)
    }
}