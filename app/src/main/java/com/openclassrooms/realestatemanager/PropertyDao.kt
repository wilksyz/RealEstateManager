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
    fun insertItem(property: Property): Long

    @Update
    fun updateItem(property: Property): Int

    @Query("DELETE FROM Property WHERE id = :propertyId")
    fun deleteItem(propertyId: Long): Int
}