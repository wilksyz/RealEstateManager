package com.openclassrooms.realestatemanager.repository

import android.arch.lifecycle.LiveData
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.persistance.PictureDao

class PictureDataRepository(private val mPictureDao: PictureDao) {

    // --- GET ---
    fun getPicture(pictureId: Long): LiveData<List<Picture>> {
        return this.mPictureDao.getPicture(pictureId)
    }

    // --- CREATE ---
    fun createPicture(picture: Picture) {
        mPictureDao.insertPicture(picture)
    }

    // --- DELETE ---
    fun deletePicture(pictureId: Long) {
        mPictureDao.deletePicture(pictureId)
    }

    fun deleteAllPictureFromProperty(propertyId: Long) {
        mPictureDao.deleteAllPictureFromProperty(propertyId)
    }
}