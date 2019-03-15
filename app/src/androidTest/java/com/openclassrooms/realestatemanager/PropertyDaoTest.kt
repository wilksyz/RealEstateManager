package com.openclassrooms.realestatemanager

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.InterestPoint
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.persistance.RealEstateManagerDatabase
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class PropertyDaoTest {

    // FOR DATA
    private lateinit var mDatabase: RealEstateManagerDatabase

    // DATA SET FOR TEST
    private val mPropertyTest = Property(6,
            450000,
            2500,
            "15",
            "It's beautiful",
            Date(),
            InterestPoint(),
            "RYAN Jack",
            Address(),
            0)

    @Rule @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    @Throws(Exception::class)
    fun initDb() {
        this.mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                RealEstateManagerDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        mDatabase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndGetProperty() {
        // BEFORE : Adding a new property
        val id = this.mDatabase.propertyDao().insertProperty(mPropertyTest)
        // TEST
        val property = LiveDataTestUtil.getValue(this.mDatabase.propertyDao().getPropertyFromId(id))
        assertTrue(property.typeProperty == Property.TYPE_MANOR && property.surface == 2500)
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndUpdateProperty() {
        // BEFORE : Adding a new property. Next, update property added & re-save it
        val id = this.mDatabase.propertyDao().insertProperty(mPropertyTest)
        val propertyAdded = LiveDataTestUtil.getValue(this.mDatabase.propertyDao().getPropertyFromId(id))
        propertyAdded.estateAgent = "PLAZA Stephane"
        propertyAdded.surface = 27000
        this.mDatabase.propertyDao().updateProperty(propertyAdded)

        //TEST
        val property = LiveDataTestUtil.getValue(this.mDatabase.propertyDao().getPropertyFromId(id))
        assertTrue(property.estateAgent == "PLAZA Stephane" && property.surface == 27000)
    }
}