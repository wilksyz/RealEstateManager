package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.net.URI

@Entity(foreignKeys = [ForeignKey(
        entity = Property::class,
        parentColumns = arrayOf("mPropertyId"),
        childColumns = arrayOf("propertyId"))]
)
data class Picture(var tittle: String, var uri: URI, var creationDate: String, var propertyId: Long) {

    @PrimaryKey(autoGenerate = true) var mPictureId: Long = 0
}