package com.openclassrooms.realestatemanager

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.content.Context


@Database(entities = [Property::class], version = 1)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    @Volatile
    private var INSTANCE: RealEstateManagerDatabase? = null
    abstract fun propertyDao(): PropertyDao

    fun getInstance(context: Context): RealEstateManagerDatabase {
        if (INSTANCE == null) {
            synchronized(RealEstateManagerDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            RealEstateManagerDatabase::class.java, "Property.db")
                            .addCallback(prepopulateDatabase())
                            .build()
                }
            }
        }
        return INSTANCE!!
    }

    private fun prepopulateDatabase(): RoomDatabase.Callback {
        return object : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

            }
        }
    }
}