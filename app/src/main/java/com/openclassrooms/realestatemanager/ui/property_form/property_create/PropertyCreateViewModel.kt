package com.openclassrooms.realestatemanager.ui.property_form.property_create

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.widget.Toast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.notification.Notification
import com.openclassrooms.realestatemanager.repository.PropertyDataRepository
import java.util.concurrent.Executor

class PropertyCreateViewModel(private val mPropertyDataRepository: PropertyDataRepository, private val mExecutor: Executor): ViewModel() {

    // --- CREATE ---
    fun createProperty(property: Property, pictureList: ArrayList<Picture>, context: Context) {
        try {
            mExecutor.execute {mPropertyDataRepository.createProperty(property, pictureList)}
            Notification.sendNotification(context)
        }catch ( e:Exception){
            Toast.makeText(context, context.getString(R.string.error_when_creating_the_property), Toast.LENGTH_LONG).show()
        }
    }
}