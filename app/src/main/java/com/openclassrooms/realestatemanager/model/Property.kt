package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Property(var typeProperty: Int,
                    var price: Int,
                    var surface: Int?,
                    var numberOfRooms: String,
                    var descriptionProperty: String,
                    var dateOfSale: Date,
                    @Embedded var interestPoint: InterestPoint,
                    var estateAgent: String,
                    @Embedded var address: Address,
                    var numberOfPhotos: Int
                    ) {

    @PrimaryKey(autoGenerate = true) var mPropertyId: Long = 0
    var dateSold: Date? = null
    var saleStatus: Boolean = false

    companion object {
        const val TYPE_APARTMENT = 3
    }
}
