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

    @Query("SELECT * FROM Property WHERE typeProperty IN (:isNearTypeProperty) AND surface BETWEEN :isNearMinSurface AND :isNearMaxSurface AND doctor = :isNearDoctor AND school = :isNearSchool AND hobbies = :isNearHobbies AND transport = :isNearTransport AND parc = :isNearParc AND store = :isNearStore AND city LIKE :isNearCity AND numberOfPhotos >= :isNearNumberOfPhotos AND price BETWEEN :isNearMinPrice AND :isNearMaxPrice AND dateOfSale BETWEEN :isNearMinDateOfSale AND :isNearMaxDateOfSale AND saleStatus = :isNearSaleStatus AND dateSold BETWEEN :isNearMinDateSold AND :isNearMaxDateSold")
    abstract fun getPropertyResearch(isNearTypeProperty: List<Int>,
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
                                     isNearMaxDateSold: Date?): LiveData<List<Property>>

    @Query("SELECT * FROM Property WHERE typeProperty = :pTypeProperty AND surface BETWEEN :pMinSurface AND :pMaxSurface AND doctor IN (:pDoctor) AND school IN (:pSchool) AND hobbies IN (:pHobbies) AND transport IN (:pTransport) AND parc IN (:pParc) AND store IN (:pStore) AND dateOfSale BETWEEN :pMinDateOfSale AND :pMaxDateOfSale AND saleStatus = :pSaleStatus AND dateSold BETWEEN :pMinDateSold AND :pMaxDateSold AND city = :pCity AND numberOfPhotos >= :pNumberOfPhotos AND price BETWEEN :pMinPrice AND :pMaxPrice")
    abstract fun getPropertyResearchSold(pTypeProperty: String,
                                         pMinSurface: Int,
                                         pMaxSurface: Int,
                                         pDoctor: List<Int>,
                                         pSchool: List<Int>,
                                         pHobbies: List<Int>,
                                         pTransport: List<Int>,
                                         pParc: List<Int>,
                                         pStore: List<Int>,
                                         pMinDateOfSale: Date,
                                         pMaxDateOfSale: Date,
                                         pSaleStatus: Int,
                                         pMinDateSold: Date,
                                         pMaxDateSold: Date,
                                         pCity: String,
                                         pNumberOfPhotos: Int,
                                         pMinPrice: Int,
                                         pMaxPrice: Int): LiveData<List<Property>>

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