package com.openclassrooms.realestatemanager

import android.app.Application
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import com.openclassrooms.realestatemanager.ui.property_form.RealPath
import com.openclassrooms.realestatemanager.ui.property_mortgage.MortgageSimulation
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MortgageSimulationUnitTest {

    @Test
    fun getMonthlyTest(){
        val monthly = MortgageSimulation.getMonthly(15.0, 175000, 1.36)

        assertEquals(1075.31, monthly, 0.0)
    }

    @Test
    fun getCostMortgageTest(){
        val coastMortgage = MortgageSimulation.getCostMortgage(15.0, 175000, 1075.31)

        assertEquals(18555.8, coastMortgage, 0.0)
    }

    @Test
    fun getRealPath(){
        val context = ApplicationProvider.getApplicationContext<Application>().applicationContext

        val realPathPicture = RealPath.getRealPathFromURI(context, Uri.parse("/storage/emulated/0/DCIM/Real Estate Manager/20190215_104146.jpg"))

        Assert.assertEquals("/storage/emulated/0/DCIM/Real Estate Manager/20190215_104146.jpg", realPathPicture)
    }
}