package com.openclassrooms.realestatemanager

import android.arch.lifecycle.LiveData

class PropertyDataRepository(propertyDao: PropertyDao) {

    private val mPropertyDao: PropertyDao = propertyDao

    fun getProperty(): LiveData<List<Property>> {
        return this.mPropertyDao.getProperty()
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