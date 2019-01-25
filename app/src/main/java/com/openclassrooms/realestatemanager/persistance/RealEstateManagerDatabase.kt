package com.openclassrooms.realestatemanager.persistance

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property

@Database(entities = [Property::class, Picture::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao
    abstract fun pictureDao(): PictureDao
}