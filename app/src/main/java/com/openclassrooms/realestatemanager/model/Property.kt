package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.content.ContentValues
import com.openclassrooms.realestatemanager.model.Address.Companion.CITY
import com.openclassrooms.realestatemanager.model.Address.Companion.NUMBER
import com.openclassrooms.realestatemanager.model.Address.Companion.POST_CODE
import com.openclassrooms.realestatemanager.model.Address.Companion.STREET
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.DOCTOR
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.HOBBIES
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.PARC
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.PUBLIC_TRANSPORT
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.SCHOOL
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.STORES
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

        // KEY CONTENT PROVIDER
        private const val TYPE_PROPERTY = "type property"
        private const val PRICE = "price"
        private const val SURFACE = "surface"
        private const val NUMBER_OF_ROOMS = "number of rooms"
        private const val DESCRIPTION_PROPERTY = "description property"
        private const val DATE_OF_SALE = "date of sale"
        private const val ESTATE_AGENT = "estate agent"
        private const val NUMBER_OF_PHOTOS = "number of photos"
        private const val PROPERTY_ID = "property id"
        private const val SALE_STATUS = "sale status"
        private const val DATE_SOLD = "date sold"

        fun fromContentValues(values: ContentValues): Property {
            val property = Property()
            if (values.containsKey(TYPE_PROPERTY)) property.typeProperty = values.getAsInteger(TYPE_PROPERTY)
            if (values.containsKey(PRICE)) property.price = values.getAsInteger(PRICE)
            if (values.containsKey(SURFACE)) property.surface = values.getAsInteger(SURFACE)
            if (values.containsKey(NUMBER_OF_ROOMS)) property.numberOfRooms = values.getAsString(NUMBER_OF_ROOMS)
            if (values.containsKey(DESCRIPTION_PROPERTY)) property.descriptionProperty = values.getAsString(DESCRIPTION_PROPERTY)
            if (values.containsKey(DATE_OF_SALE)) property.dateOfSale = Date(values.getAsLong(DATE_OF_SALE))
            if (values.containsKey(ESTATE_AGENT)) property.estateAgent = values.getAsString(ESTATE_AGENT)
            if (values.containsKey(NUMBER_OF_PHOTOS)) property.numberOfPhotos = values.getAsInteger(NUMBER_OF_PHOTOS)
            if (values.containsKey(PROPERTY_ID)) property.mPropertyId = values.getAsLong(PROPERTY_ID)
            if (values.containsKey(SALE_STATUS)) property.saleStatus = values.getAsBoolean(SALE_STATUS)
            if (values.containsKey(DATE_SOLD)) property.dateSold = Date(values.getAsLong(DATE_SOLD))
            if (values.containsKey(DOCTOR)) property.interestPoint.doctor = values.getAsBoolean(DOCTOR)
            if (values.containsKey(SCHOOL)) property.interestPoint.school = values.getAsBoolean(SCHOOL)
            if (values.containsKey(HOBBIES)) property.interestPoint.hobbies = values.getAsBoolean(HOBBIES)
            if (values.containsKey(PUBLIC_TRANSPORT)) property.interestPoint.transport = values.getAsBoolean(PUBLIC_TRANSPORT)
            if (values.containsKey(PARC)) property.interestPoint.parc = values.getAsBoolean(PARC)
            if (values.containsKey(STORES)) property.interestPoint.store = values.getAsBoolean(STORES)
            if (values.containsKey(NUMBER)) property.address.number = values.getAsString(NUMBER)
            if (values.containsKey(STREET)) property.address.street = values.getAsString(STREET)
            if (values.containsKey(POST_CODE)) property.address.postCode = values.getAsString(POST_CODE)
            if (values.containsKey(CITY)) property.address.city = values.getAsString(CITY)
            return property
        }
    }
}
