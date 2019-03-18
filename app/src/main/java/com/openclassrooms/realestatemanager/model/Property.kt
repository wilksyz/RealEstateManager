package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity
data class Property(var typeProperty: Int = -1,
                    var price: Int = 0,
                    var surface: Int = 0,
                    var numberOfRooms: String = "",
                    var descriptionProperty: String = "",
                    var dateOfSale: Date = Date(),
                    @Embedded var interestPoint: InterestPoint = InterestPoint(),
                    var estateAgent: String = "",
                    @Embedded var address: Address = Address(),
                    var numberOfPhotos: Int = 0) {

    @PrimaryKey(autoGenerate = true) var mPropertyId: Long = 0
    var dateSold: Date = Date()
    var saleStatus: Boolean = false

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
