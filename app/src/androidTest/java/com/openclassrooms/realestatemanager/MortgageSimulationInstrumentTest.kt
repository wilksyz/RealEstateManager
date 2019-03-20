package com.openclassrooms.realestatemanager

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.openclassrooms.realestatemanager.ui.base_property_list.property_list.PropertyListActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.android.synthetic.main.activity_list_property.*
import kotlinx.android.synthetic.main.fragment_property_mortgage.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MortgageSimulationInstrumentTest {

    @get:Rule
    var activityRule: ActivityTestRule<PropertyListActivity> = ActivityTestRule(PropertyListActivity::class.java)

    @Test
    @LargeTest
    fun testMortgageSimulation(){
        onView(ViewMatchers.withId(R.id.action_mortgage_simulation)).perform(ViewActions.click())
        assertTrue(activityRule.activity.bottom_Navigation_View.menu.findItem(R.id.action_mortgage_simulation).isChecked)

        onView(ViewMatchers.withId(R.id.price_mortgage_edit_text)).perform(ViewActions.typeText("210000"))
        onView(withId(R.id.price_mortgage_edit_text)).check(matches(withText("210000")))

        onView(ViewMatchers.withId(R.id.provision_mortgage_editext)).perform(ViewActions.typeText("10000"))
        onView(withId(R.id.provision_mortgage_editext)).check(matches(withText("10000")))

        onView(ViewMatchers.withId(R.id.simulate_mortgage_button)).perform(ViewActions.click())

        assertEquals("Borrowed money: $ 200000", activityRule.activity.money_mortgage_textView.text.toString())
        assertEquals("Interest rate: 0.92%", activityRule.activity.rate_mortgage_textview.text.toString())
        assertEquals("Your monthly: $ 8413.43", activityRule.activity.monthly_mortgage_textView.text.toString())
        assertEquals("Cost of the mortgage: $ 1922.32", activityRule.activity.cost_mortgage_textview.text.toString())
    }
}