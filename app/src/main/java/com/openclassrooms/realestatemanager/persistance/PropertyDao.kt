package com.openclassrooms.realestatemanager.persistance

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property

@Dao
abstract class PropertyDao(private val database: RealEstateManagerDatabase) {

    @Query("SELECT * FROM Property")
    abstract fun getAllProperty(): LiveData<List<Property>>

    @Query("SELECT * FROM Property WHERE mPropertyId = :propertyId")
    abstract fun getPropertyFromId(propertyId: Long): LiveData<Property>
/*
    @Query("SELECT * FROM Property WHERE typeProperty = :pTypeProperty " +
            "AND surface BETWEEN :pMinSurface AND :pMaxSurface " +
            "AND doctor IN (:pDoctor) " +
            "AND school IN (:pSchool) " +
            "AND hobbies IN (:pHobbies) " +
            "AND transport IN (:pTransport) " +
            "AND parc IN (:pParc)" +
            "AND store IN (:pStore)" +
            "AND ")
    abstract fun getPropertyFromResearch(pTypeProperty: String,
                                         pMinSurface: String,
                                         pMaxSurface: String,
                                         pDoctor: List<String>,
                                         pSchool: List<String>,
                                         pHobbies: List<String>,
                                         pTransport: List<String>,
                                         pParc: List<String>,
                                         pStore: List<String>,
                                         ): LiveData<Property>
*/
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
}