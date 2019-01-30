package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Property(var typeProperty: String,
                    var price: String,
                    var surface: String,
                    var numberOfRooms: String,
                    var descriptionProperty: String,
                    var address: String,
                    var dateOfSale: String,
                    var interestPoint: String,
                    var estateAgent: String
                    ) {

    @PrimaryKey(autoGenerate = true) var mPropertyId: Long = 0
    var dateSold: String = ""
    var saleStatus: Boolean = false

/*
                    ,
                    var descriptionProperty: String,
                    var address: String,
                    var interestPoint: String,
                    var saleStatus: Boolean,
                    var dateOfSale: String,
                    var dateSold: String,
                    var estateAgent: String
 */

}