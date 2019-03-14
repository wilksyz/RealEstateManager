package com.openclassrooms.realestatemanager.model

import android.content.ContentValues
import java.util.*
import kotlin.collections.ArrayList

object FromContentValues {

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
    private const val URI = "uri"
    private const val DESCRIPTION_PICTURE = "description picture"
    private const val CREATION_DATE = "creation date"

    fun propertyFromContentValues(values: ContentValues): Property {
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
        if (values.containsKey(InterestPoint.DOCTOR)) property.interestPoint.doctor = values.getAsBoolean(InterestPoint.DOCTOR)
        if (values.containsKey(InterestPoint.SCHOOL)) property.interestPoint.school = values.getAsBoolean(InterestPoint.SCHOOL)
        if (values.containsKey(InterestPoint.HOBBIES)) property.interestPoint.hobbies = values.getAsBoolean(InterestPoint.HOBBIES)
        if (values.containsKey(InterestPoint.PUBLIC_TRANSPORT)) property.interestPoint.transport = values.getAsBoolean(InterestPoint.PUBLIC_TRANSPORT)
        if (values.containsKey(InterestPoint.PARC)) property.interestPoint.parc = values.getAsBoolean(InterestPoint.PARC)
        if (values.containsKey(InterestPoint.STORES)) property.interestPoint.store = values.getAsBoolean(InterestPoint.STORES)
        if (values.containsKey(Address.NUMBER)) property.address.number = values.getAsString(Address.NUMBER)
        if (values.containsKey(Address.STREET)) property.address.street = values.getAsString(Address.STREET)
        if (values.containsKey(Address.POST_CODE)) property.address.postCode = values.getAsString(Address.POST_CODE)
        if (values.containsKey(Address.CITY)) property.address.city = values.getAsString(Address.CITY)
        return property
    }

    fun picturesFromContentValues(values: ContentValues): ArrayList<Picture>{
        val pictureList = ArrayList<Picture>()
        var uriList: List<String>? = null
        var descriptionList: List<String>? = null
        var creationDateList: List<String>? = null
        if (values.containsKey(URI)) uriList = values.getAsString(URI).split("&")
        if (values.containsKey(DESCRIPTION_PICTURE)) descriptionList = values.getAsString(DESCRIPTION_PICTURE).split("&")
        if (values.containsKey(CREATION_DATE)) creationDateList = values.getAsString(CREATION_DATE).split("&")
        if (uriList != null && descriptionList != null && creationDateList != null){
            for ((i, uri) in uriList.withIndex()){
                val picture = Picture()
                picture.uri = uri
                picture.description = descriptionList[i]
                picture.creationDate = creationDateList[i]
                pictureList.add(picture)
            }
        }
        return pictureList
    }
}