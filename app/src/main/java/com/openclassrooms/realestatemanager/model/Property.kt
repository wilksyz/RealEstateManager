package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Property(var typeProperty: String,
                    var price: String,
                    var surface: String,
                    var numberOfRooms: String,
                    var descriptionProperty: String,
                    var dateOfSale: String,
                    @Embedded var interestPoint: InterestPoint,
                    var estateAgent: String,
                    @Embedded var address: Address
                    ) {

    @PrimaryKey(autoGenerate = true) var mPropertyId: Long = 0
    var dateSold: String = ""
    var saleStatus: Boolean = false
}