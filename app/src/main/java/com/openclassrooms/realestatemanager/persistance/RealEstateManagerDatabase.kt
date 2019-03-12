package com.openclassrooms.realestatemanager.persistance

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property

private var INSTANCE: RealEstateManagerDatabase? = null

@Database(entities = [Property::class, Picture::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao
    abstract fun pictureDao(): PictureDao

    companion object {
        fun getInstance(context: Context): RealEstateManagerDatabase? {
            if (INSTANCE == null) {
                synchronized(RealEstateManagerDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                RealEstateManagerDatabase::class.java, "Property.db")
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}