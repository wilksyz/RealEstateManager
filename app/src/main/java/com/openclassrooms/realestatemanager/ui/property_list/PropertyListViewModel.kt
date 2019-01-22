package com.openclassrooms.realestatemanager.ui.property_list

import java.util.concurrent.Executor
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository


class PropertyListViewModel(propertyDataRepository: PropertyDataRepository, executor: Executor) : ViewModel() {

    private val mPropertyDataSource: PropertyDataRepository = propertyDataRepository
    private val mExecutor: Executor = executor

    fun getAllProperty(): LiveData<List<Property>> {
        return mPropertyDataSource.getAllProperty()
    }

    fun getProperty(id: Long): LiveData<Property>{
        return mPropertyDataSource.getProperty(id)
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