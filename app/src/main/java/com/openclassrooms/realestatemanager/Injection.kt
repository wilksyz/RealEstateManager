package com.openclassrooms.realestatemanager

import android.arch.persistence.room.Room
import android.content.Context
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

    private fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dataSourceProperty = providePropertyDataSource(context)
        val executor = provideExecutor()
        return ViewModelFactory(dataSourceProperty, executor)
    }
}