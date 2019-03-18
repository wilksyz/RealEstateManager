package com.openclassrooms.realestatemanager.persistance

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.openclassrooms.realestatemanager.model.Picture

@Dao
interface PictureDao {

    @Query("SELECT * FROM Picture WHERE property_id = :propertyId")
    fun getPicture(propertyId: Long): LiveData<List<Picture>>

    @Insert
    fun insertPicture(picture: Picture): Long

    @Query("DELETE FROM Picture WHERE property_id = :propertyId")
    fun deleteAllPictureFromProperty(propertyId: Long): Int
}