package com.openclassrooms.realestatemanager

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.InterestPoint
import com.openclassrooms.realestatemanager.model.Picture
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
class PictureDaoTest {


    // FOR DATA
    private lateinit var mDatabase: RealEstateManagerDatabase

    // DATA SET FOR TEST
    private val mPropertyId: Long = 1
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
    private val mPictureTest = Picture(1,
            "This is a picture.",
            "/storage/emulated/0/DCIM/Real Estate Manager/20190215_104146.jpg",
            "14/03/2019")

    @Rule
    @JvmField
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
    fun insertAndGetPicture() {
        // BEFORE : Adding a new property and new picture
        val id = this.mDatabase.propertyDao().insertProperty(mPropertyTest)
        this.mDatabase.pictureDao().insertPicture(mPictureTest)
        // TEST
        val pictureList = LiveDataTestUtil.getValue(this.mDatabase.pictureDao().getPicture(id))
        assertTrue(pictureList[0].description == "This is a picture." && pictureList[0].uri == "/storage/emulated/0/DCIM/Real Estate Manager/20190215_104146.jpg")
    }

    @Test
    @Throws(InterruptedException::class)
    fun getPicturesWhenNoPictureInserted() {
        // TEST
        val pictures = (LiveDataTestUtil.getValue(this.mDatabase.pictureDao().getPicture(mPropertyId)))
        assertTrue(pictures.isEmpty())
    }

    @Test
    @Throws(InterruptedException::class)
    fun insertAndDeletePicture() {
        // BEFORE : Adding a new property and new picture. Next, delete picture added
        val id = this.mDatabase.propertyDao().insertProperty(mPropertyTest)
        this.mDatabase.pictureDao().insertPicture(mPictureTest)
        this.mDatabase.pictureDao().deleteAllPictureFromProperty(id)

        //TEST
        val pictures = (LiveDataTestUtil.getValue(this.mDatabase.pictureDao().getPicture(mPropertyId)))
        assertTrue(pictures.isEmpty())
    }
}