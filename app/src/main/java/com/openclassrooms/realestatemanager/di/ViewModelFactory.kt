package com.openclassrooms.realestatemanager.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.repository.PictureDataRepository
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository
import com.openclassrooms.realestatemanager.ui.base_property_list.property_list.PropertyListViewModel
import com.openclassrooms.realestatemanager.ui.base_property_list.property_result_research.PropertyResultOfResearchViewModel
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailViewModel
import com.openclassrooms.realestatemanager.ui.property_form.property_create.PropertyCreateViewModel
import com.openclassrooms.realestatemanager.ui.property_form.property_edit.PropertyEditViewModel
import com.openclassrooms.realestatemanager.ui.property_maps.PropertyMapsViewModel
import java.util.concurrent.Executor

class ViewModelFactory(private val mPropertyDataRepository: PropertyDataRepository, private val mPictureDataRepository: PictureDataRepository, private val mExecutor: Executor) :ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyListViewModel::class.java)) {
            return PropertyListViewModel(mPropertyDataRepository, mPictureDataRepository) as T
                }
        if (modelClass.isAssignableFrom(PropertyCreateViewModel::class.java)) {
            return PropertyCreateViewModel(mPropertyDataRepository, mExecutor) as T
        }
        if (modelClass.isAssignableFrom(PropertyDetailViewModel::class.java)) {
            return PropertyDetailViewModel(mPropertyDataRepository, mPictureDataRepository, mExecutor) as T
        }
        if (modelClass.isAssignableFrom(PropertyMapsViewModel::class.java)) {
            return PropertyMapsViewModel(mPropertyDataRepository) as T
        }
        if (modelClass.isAssignableFrom(PropertyResultOfResearchViewModel::class.java)){
            return PropertyResultOfResearchViewModel(mPropertyDataRepository, mPictureDataRepository) as T
        }
        if (modelClass.isAssignableFrom(PropertyEditViewModel::class.java)){
            return PropertyEditViewModel(mPropertyDataRepository, mPictureDataRepository, mExecutor) as T
        }
            throw IllegalArgumentException("Unknown ViewModel class")
    }
}