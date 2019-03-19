package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.persistance.Converters
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class ConvertersUnitTest{

    @Test
    fun convertDateToTimestamp(){
        val dateLong = Converters().dateToTimestamp(Date(1552573750880))

        Assert.assertEquals(1552573750880, dateLong)
    }

    @Test
    fun convertTimestampToDate(){
        val date = Converters().fromTimestamp(1552573750880)

        Assert.assertEquals(Date(1552573750880), date)
    }
}