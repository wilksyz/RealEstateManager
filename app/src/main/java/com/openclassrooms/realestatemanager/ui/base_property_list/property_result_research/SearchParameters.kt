package com.openclassrooms.realestatemanager.ui.base_property_list.property_result_research

import com.openclassrooms.realestatemanager.model.Property
import java.util.*

object SearchParameters {

    private val mIntervalDate = arrayOf(-730, -7, -14, -30, -60, -180, -365)

    // Get the minimum date sold of the property
    fun getSoldDate(soldDateIndex: Int, propertySold: Boolean, date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return if (propertySold){
            calendar.add(Calendar.DAY_OF_YEAR, mIntervalDate[soldDateIndex])
            calendar.time
        }else{
            calendar.add(Calendar.DAY_OF_YEAR, mIntervalDate[0])
            calendar.time
        }
    }

    // Get the type of property for the search request
    fun getTypeProperty(typePropertyIndex: Int): ArrayList<Int> {
        val typeProperty = arrayListOf<Int>()
        val typePropertyInt = arrayOf(0, 1, 2, 3, 4, 5, 6)
        return if (typePropertyIndex == Property.TYPE_ALL){
            typeProperty.addAll(typePropertyInt)
            typeProperty
        }else{
            typeProperty.add(typePropertyIndex)
            typeProperty
        }
    }

    // Determine if the point of interest must be present or not
    fun getInterestPointList(interestPoint: Boolean): ArrayList<Boolean> {
        val interestPointList = arrayListOf<Boolean>()
        interestPointList.add(true)
        if (!interestPoint) interestPointList.add(false)
        return interestPointList
    }
}