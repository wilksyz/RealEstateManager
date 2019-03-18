package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Property(var typeProperty: Int,
                    var price: Int,
                    var surface: Int,
                    var numberOfRooms: String,
                    var descriptionProperty: String,
                    var dateOfSale: Date,
                    @Embedded var interestPoint: InterestPoint,
                    var estateAgent: String,
                    @Embedded var address: Address,
                    var numberOfPhotos: Int) {

    @PrimaryKey(autoGenerate = true) var mPropertyId: Long = 0
    var dateSold: Date = Date()
    var saleStatus: Boolean = false

    @Ignore constructor() : this(typeProperty = -1,
            price = 0,
            surface = 0,
            numberOfRooms = "",
            descriptionProperty = "",
            dateOfSale = Date(),
            interestPoint = InterestPoint(),
            estateAgent = "",
            address = Address(),
            numberOfPhotos = 0)

    companion object {
        //TYPE OF PROPERTY
        const val TYPE_HOUSE = 0
        const val TYPE_LOFT = 1
        const val TYPE_CASTLE = 2
        const val TYPE_APARTMENT = 3
        const val TYPE_RANCH = 4
        const val TYPE_PENTHOUSE = 5
        const val TYPE_MANOR = 6
        const val TYPE_ALL = 7
    }
}
