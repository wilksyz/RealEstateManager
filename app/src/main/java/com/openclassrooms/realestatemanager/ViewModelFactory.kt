package com.openclassrooms.realestatemanager

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import java.util.concurrent.Executor

class ViewModelFactory(propertyDataRepository: PropertyDataRepository, executor: Executor) :ViewModelProvider.Factory{

    private val mPropertyDataSource: PropertyDataRepository = propertyDataRepository
    private val mExecutor: Executor = executor

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyViewModel::class.java)) {
                    return PropertyViewModel(mPropertyDataSource, mExecutor) as T
                }
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}