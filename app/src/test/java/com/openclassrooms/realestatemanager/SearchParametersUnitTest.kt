package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.ui.base_property_list.property_result_research.SearchParameters
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*
import kotlin.collections.ArrayList

@RunWith(JUnit4::class)
class SearchParametersUnitTest {

    @Test
    fun getSoldDate(){
        val soldDate = SearchParameters.getSoldDate(1, true, Date(1552573750880))

        assertEquals(Date(1551968950880), soldDate)
    }

    @Test
    fun getTypeProperty(){
        val listChecked = ArrayList<Int>()
        listChecked.add(3)

        val typePropertyList = SearchParameters.getTypeProperty(3)

        assertEquals(listChecked, typePropertyList)
    }

    @Test
    fun getInterestPoint(){
        val listChecked = ArrayList<Boolean>()
        listChecked.add(true)
        listChecked.add(false)

        val interestPointList = SearchParameters.getInterestPointList(false)

        assertEquals(listChecked, interestPointList)
    }
}