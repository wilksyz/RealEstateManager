package com.openclassrooms.realestatemanager.persistance

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import android.arch.lifecycle.LiveData
import com.openclassrooms.realestatemanager.model.Property

@Dao
interface PropertyDao {

    @Query("SELECT * FROM Property")
    fun getAllProperty(): LiveData<List<Property>>

    @Query("SELECT * FROM Property WHERE Id = :id")
    fun getProperty(id: Long): LiveData<Property>

    @Insert
    fun insertProperty(property: Property): Long

    @Update
    fun updateProperty(property: Property): Int

    @Query("DELETE FROM Property WHERE id = :propertyId")
    fun deleteProperty(propertyId: Long): Int
}