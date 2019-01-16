package com.openclassrooms.realestatemanager

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Property(var typeProperty: String,
               var price: Int,
               var surface: Int,
               var numberOfRooms: Int,
               var descriptionProperty: String,
               var photos: Array<String>,
               var address: String,
               var interestPoint: Array<String>,
               var saleStatut: Boolean,
               var dateOfSale: String,
               var dateSold: String,
               var estateAgent: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}