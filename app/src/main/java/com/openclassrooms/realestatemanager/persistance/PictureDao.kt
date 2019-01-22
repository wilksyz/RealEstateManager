package com.openclassrooms.realestatemanager.persistance

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.openclassrooms.realestatemanager.model.Picture


interface PictureDao {

    @Query("SELECT * FROM Picture WHERE mPictureId = :pictureId")
    fun getPicture(pictureId: Long): LiveData<List<Picture>>

    @Insert
    fun insertPicture(picture: Picture): Long

    @Query("DELETE FROM Picture WHERE mPictureId = :pictureId")
    fun deletePicture(pictureId: Long): Int

    @Query("DELETE FROM Picture WHERE propertyId = :propertyId")
    fun deleteAllPictureFromProperty(propertyId: Long): Int
}