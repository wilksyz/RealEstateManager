package com.openclassrooms.realestatemanager

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import android.arch.lifecycle.LiveData

@Dao
interface PropertyDao {

    @Query("SELECT * FROM Property")
    fun getProperty(): LiveData<List<Property>>

    @Insert
    fun insertProperty(property: Property): Long

    @Update
    fun updateProperty(property: Property): Int

    @Query("DELETE FROM Property WHERE id = :propertyId")
    fun deleteProperty(propertyId: Long): Int
}