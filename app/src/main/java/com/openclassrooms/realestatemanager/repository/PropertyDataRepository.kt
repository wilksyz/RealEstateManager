package com.openclassrooms.realestatemanager.repository

import android.arch.lifecycle.LiveData
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.persistance.PropertyDao

class PropertyDataRepository(private val mPropertyDao: PropertyDao) {

    // --- GET ---
    fun getAllProperty(): LiveData<List<Property>> {
        return this.mPropertyDao.getAllProperty()
    }

    fun getProperty(propertyId: Long): LiveData<Property>{
        return  this.mPropertyDao.getProperty(propertyId)
    }

    // --- CREATE ---
    fun createProperty(property: Property) {
        mPropertyDao.insertProperty(property)
    }

    // --- DELETE ---
    fun deleteProperty(propertyId: Long) {
        mPropertyDao.deleteProperty(propertyId)
    }

    // --- UPDATE ---
    fun updateProperty(property: Property) {
        mPropertyDao.updateProperty(property)
    }
}