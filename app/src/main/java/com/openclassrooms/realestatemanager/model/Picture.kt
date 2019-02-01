package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(foreignKeys = [ForeignKey(
        entity = Property::class,
        parentColumns = arrayOf("mPropertyId"),
        childColumns = arrayOf("propertyId"))]
)
data class Picture(var title: String,
                   var uri: String,
                   var creationDate: String,
                   var propertyId: Long): Parcelable {

    @PrimaryKey(autoGenerate = true) var mPictureId: Long = 0

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong()) {
        mPictureId = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(uri)
        parcel.writeString(creationDate)
        parcel.writeLong(propertyId)
        parcel.writeLong(mPictureId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Picture> {
        override fun createFromParcel(parcel: Parcel): Picture {
            return Picture(parcel)
        }

        override fun newArray(size: Int): Array<Picture?> {
            return arrayOfNulls(size)
        }
    }
}