package com.openclassrooms.realestatemanager

import android.arch.persistence.room.Room
import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.InterestPoint
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.persistance.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.provider.PropertyContentProvider
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyContentProviderTest {

    // FOR DATA
    private lateinit var mContentResolver: ContentResolver

    // DATA SET FOR TEST
    private val propertyID: Long = 1

    @Before
    fun setUp() {
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateManagerDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        mContentResolver = InstrumentationRegistry.getContext().contentResolver
    }

    @Test
    fun getItemsWhenNoPropertyInserted() {
        val cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyContentProvider.URI_PROPERTY, propertyID), null, null, null, null)
        assertThat<Cursor>(cursor, notNullValue())
        assertThat<Int>(cursor!!.count, `is`<Int>(1))
        cursor.close()
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @Test
    fun insertAndGetProperty() {
        // BEFORE : Adding demo property
        val propertyId = mContentResolver.insert(PropertyContentProvider.URI_PROPERTY, generateProperty())

        // TEST
        val cursor = mContentResolver.query(ContentUris.withAppendedId(PropertyContentProvider.URI_PROPERTY, propertyId.encodedPath.split("/")[2].toLong()), null, null, null, null)
        assertThat(cursor, notNullValue())
        assertThat(cursor!!.count, `is`(1))
        assertThat(cursor.moveToFirst(), `is`(true))
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("descriptionProperty")), `is`("It's magic loft"))
    }

    private fun generateProperty(): ContentValues {
        val values = ContentValues()
        values.put(TYPE_PROPERTY, Property.TYPE_LOFT)
        values.put(PRICE, 355000)
        values.put(SURFACE, 180)
        values.put(NUMBER_OF_ROOMS, "8")
        values.put(DESCRIPTION_PROPERTY, "It's magic loft")
        values.put(DATE_OF_SALE, 1552573750880)
        values.put(ESTATE_AGENT, "Emmanuel Macron")
        values.put(InterestPoint.DOCTOR, true)
        values.put(InterestPoint.STORES, true)
        values.put(NUMBER_OF_PHOTOS, 1)
        values.put(SALE_STATUS, false)
        values.put(URI, "/storage/emulated/0/DCIM/Real Estate Manager/20190215_104146.jpg")
        values.put(DESCRIPTION_PICTURE, "TV screen red")
        values.put(CREATION_DATE, "14/03/2019")
        values.put(Address.NUMBER, "128")
        values.put(Address.STREET, "rue Henri Champion")
        values.put(Address.POST_CODE, "72100")
        values.put(Address.CITY, "Le Mans")
        return values
    }

    companion object {
        //key test content provider
        private const val TYPE_PROPERTY = "type property"
        private const val PRICE = "price"
        private const val SURFACE = "surface"
        private const val NUMBER_OF_ROOMS = "number of rooms"
        private const val DESCRIPTION_PROPERTY = "description property"
        private const val DATE_OF_SALE = "date of sale"
        private const val ESTATE_AGENT = "estate agent"
        private const val NUMBER_OF_PHOTOS = "number of photos"
        private const val SALE_STATUS = "sale status"
        private const val URI = "uri"
        private const val DESCRIPTION_PICTURE = "description picture"
        private const val CREATION_DATE = "creation date"
    }
}