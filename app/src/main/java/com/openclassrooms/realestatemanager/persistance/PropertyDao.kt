package com.openclassrooms.realestatemanager.persistance

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import java.util.*

@Dao
abstract class PropertyDao(private val database: RealEstateManagerDatabase) {

    @Query("SELECT * FROM Property")
    abstract fun getAllProperty(): LiveData<List<Property>>

    @Query("SELECT * FROM Property WHERE mPropertyId = :propertyId")
    abstract fun getPropertyFromId(propertyId: Long): LiveData<Property>

    @Query("SELECT * FROM Property WHERE typeProperty IN (:isNearTypeProperty) AND surface BETWEEN :isNearMinSurface AND :isNearMaxSurface AND doctor IN (:isNearDoctor) AND school IN (:isNearSchool) AND hobbies IN (:isNearHobbies) AND transport IN (:isNearTransport) AND parc IN (:isNearParc) AND store IN (:isNearStore) AND city LIKE :isNearCity AND numberOfPhotos >= :isNearNumberOfPhotos AND price BETWEEN :isNearMinPrice AND :isNearMaxPrice AND dateOfSale BETWEEN :isNearMinDateOfSale AND :isNearMaxDateOfSale AND saleStatus = :isNearSaleStatus AND dateSold BETWEEN :isNearMinDateSold AND :isNearMaxDateSold")
    abstract fun getPropertyResearch(isNearTypeProperty: List<Int>,
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
                                     isNearMaxDateSold: Date?): LiveData<List<Property>>

    @Transaction
    open fun createProperty(property: Property, pictureList: ArrayList<Picture>){
        val id = this.insertProperty(property)
        for (picture in pictureList){
            database.pictureDao().insertPicture(picture.apply { propertyId = id })
        }
    }

    @Transaction
    open fun updatePropertyAndPictures(property: Property, pictureList: ArrayList<Picture>){
        this.updateProperty(property)
        val id = property.mPropertyId
        database.pictureDao().deleteAllPictureFromProperty(id)
        for (picture in pictureList){
            database.pictureDao().insertPicture(picture.apply { propertyId = id })
        }
    }

    @Insert
    abstract fun insertProperty(property: Property): Long

    @Update
    abstract fun updateProperty(property: Property): Int
}