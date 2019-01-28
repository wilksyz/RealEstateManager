package com.openclassrooms.realestatemanager.persistance

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.util.Log
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property

@Dao
abstract class PropertyDao(private val database: RealEstateManagerDatabase) {

    @Query("SELECT * FROM Property")
    abstract fun getAllProperty(): LiveData<List<Property>>

    @Query("SELECT * FROM Property WHERE mPropertyId = :propertyId")
    abstract fun getProperty(propertyId: Long): LiveData<Property>

    @Transaction
    open fun createProperty(property: Property, pictureList: ArrayList<Picture>){
        val id = insertProperty(property)
        for (picture in pictureList){
            database.pictureDao().insertPicture(picture.apply { propertyId = id })
        }
    }

    @Insert
    abstract fun insertProperty(property: Property): Long

    @Update
    abstract fun updateProperty(property: Property): Int

    @Query("DELETE FROM Property WHERE mPropertyId = :propertyId")
    abstract fun deleteProperty(propertyId: Long): Int
}