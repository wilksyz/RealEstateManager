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

    fun getPropertyResearch(pTypeProperty: String,
                            pMinSurface: Int,
                            pMaxSurface: Int,
                            pDoctor: List<Int>,
                            pSchool: List<Int>,
                            pHobbies: List<Int>,
                            pTransport: List<Int>,
                            pParc: List<Int>,
                            pStore: List<Int>,
                            pCity: String,
                            pNumberOfPhotos: Int,
                            pMinPrice: Int,
                            pMaxPrice: Int,
                            pMinDateOfSale: Date,
                            pMaxDateOfSale: Date): LiveData<List<Property>> {
        return this.mPropertyDao.getPropertyResearch(pTypeProperty,pMinSurface,pMaxSurface,pDoctor,pSchool,pHobbies,pTransport,pParc,pStore,pCity,pNumberOfPhotos,pMinPrice,pMaxPrice,pMinDateOfSale,pMaxDateOfSale)
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
}