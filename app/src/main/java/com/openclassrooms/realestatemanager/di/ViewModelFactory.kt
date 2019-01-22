package com.openclassrooms.realestatemanager.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository
import com.openclassrooms.realestatemanager.ui.property_list.PropertyListViewModel
import java.util.concurrent.Executor

class ViewModelFactory(propertyDataRepository: PropertyDataRepository, executor: Executor) :ViewModelProvider.Factory{

    private val mPropertyDataSource: PropertyDataRepository = propertyDataRepository
    private val mExecutor: Executor = executor

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyListViewModel::class.java)) {
                    return PropertyListViewModel(mPropertyDataSource, mExecutor) as T
                }
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}