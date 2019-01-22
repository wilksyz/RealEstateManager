package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Property(var typeProperty: String,
                    var price: Int,
                    var surface: Int,
                    var numberOfRooms: Int
                    ) {

    @PrimaryKey(autoGenerate = true) var id: Long = 0

/*
                    ,
                    var descriptionProperty: String,
                    var photos: Array<String>,
                    var address: String,
                    var interestPoint: String,
                    var saleStatus: Boolean,
                    var dateOfSale: String,
                    var dateSold: String,
                    var estateAgent: String
 */

}