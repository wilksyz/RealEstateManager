package com.openclassrooms.realestatemanager.di

import android.arch.persistence.room.Room
import android.content.Context
import com.openclassrooms.realestatemanager.persistance.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.repository.PictureDataRepository
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Injection {

    private fun providePropertyDataSource(context: Context): PropertyDataRepository {
        val database: RealEstateManagerDatabase = Room.databaseBuilder(
                context.applicationContext,
                RealEstateManagerDatabase::class.java, "Property.db"
        ).build()
        return PropertyDataRepository(database.propertyDao())
    }

    private fun providePictureDataSource(context: Context): PictureDataRepository {
        val database: RealEstateManagerDatabase = Room.databaseBuilder(
                context.applicationContext,
                RealEstateManagerDatabase::class.java, "Property.db"
        ).build()
        return PictureDataRepository(database.pictureDao())
    }

    private fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSourceProperty = providePropertyDataSource(context)
        val dataSourcePicture = providePictureDataSource(context)
        val executor = provideExecutor()
        return ViewModelFactory(dataSourceProperty, dataSourcePicture, executor)
    }
}