package com.openclassrooms.realestatemanager.repository

import android.arch.lifecycle.LiveData
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

    fun getPropertyResearch(pTypeProperty: Int,
                            pMinSurface: Int,
                            pMaxSurface: Int,
                            isNearDoctor: Boolean,
                            isNearSchool: Boolean,
                            isNearHobbies: Boolean,
                            isNearTransport: Boolean,
                            isNearParc: Boolean,
                            isNearStore: Boolean,
                            pCity: String,
                            pNumberOfPhotos: Int,
                            pMinPrice: Int,
                            pMaxPrice: Int,
                            pMinDateOfSale: Date,
                            pMaxDateOfSale: Date): LiveData<List<Property>> {
        return this.mPropertyDao.getPropertyResearch(pTypeProperty,pMinSurface,pMaxSurface,isNearDoctor,isNearSchool,isNearHobbies,isNearTransport,isNearParc,isNearStore,pCity,pNumberOfPhotos,pMinPrice,pMaxPrice,pMinDateOfSale,pMaxDateOfSale)
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
                                pMaxPrice: Int): LiveData<List<Property>> {
        return this.mPropertyDao.getPropertyResearchSold(typeProperty, minSurface, maxSurface, doctor, school, hobbies, transport, parc, store, minDateOfSale, maxDateOfSale, saleStatus, minDateSold, maxDateSold, city, pNumberOfPhotos, pMinPrice, pMaxPrice)
    }

    // --- CREATE ---
    fun createProperty(property: Property, pictureList: ArrayList<Picture>) {
        mPropertyDao.createProperty(property, pictureList)
    }

    // --- UPDATE ---
    fun updateProperty(property: Property) {
        mPropertyDao.updateProperty(property)
    }

    fun updatePropertyAndPictures(property: Property, pictureList: ArrayList<Picture>){
        mPropertyDao.updatePropertyAndPictures(property, pictureList)
    }
}