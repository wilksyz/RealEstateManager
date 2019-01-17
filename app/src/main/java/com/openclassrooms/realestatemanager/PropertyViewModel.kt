package com.openclassrooms.realestatemanager

import java.util.concurrent.Executor
import android.os.AsyncTask.execute
import android.content.ClipData.Item
import android.arch.lifecycle.LiveData



class PropertyViewModel(propertyDataRepository: PropertyDataRepository, executor: Executor) {

    private val mPropertyDataSource: PropertyDataRepository = propertyDataRepository
    private val mExecutor: Executor = executor

    fun getProperty(): LiveData<List<Property>> {
        return mPropertyDataSource.getProperty()
    }

    fun createProperty(property: Property) {
        mExecutor.execute {mPropertyDataSource.createProperty(property)}
    }

    fun deleteProperty(propertyId: Long) {
        mExecutor.execute { mPropertyDataSource.deleteProperty(propertyId) }
    }

    fun updateItem(property: Property) {
        mExecutor.execute { mPropertyDataSource.updateProperty(property) }
    }
}