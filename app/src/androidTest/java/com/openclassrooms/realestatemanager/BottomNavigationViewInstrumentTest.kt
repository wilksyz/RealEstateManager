package com.openclassrooms.realestatemanager

import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.Test
import android.support.test.filters.SmallTest
import android.support.test.annotation.UiThreadTest
import android.view.Menu
import com.openclassrooms.realestatemanager.ui.base_property_list.property_list.PropertyListActivity
import kotlinx.android.synthetic.main.activity_list_property.*
import junit.framework.TestCase.*
import android.support.design.bottomnavigation.LabelVisibilityMode
import android.support.test.filters.LargeTest


@RunWith(AndroidJUnit4::class)
class BottomNavigationViewInstrumentTest {

    @get:Rule
    var activityRule: ActivityTestRule<PropertyListActivity> = ActivityTestRule(PropertyListActivity::class.java)

    @Test
    @LargeTest
    fun testNavigationItem(){
        onView(withId(R.id.action_mortgage_simulation)).perform(click())
        assertTrue(activityRule.activity.bottom_Navigation_View.menu.findItem(R.id.action_mortgage_simulation).isChecked)
        onView(withId(R.id.action_home)).perform(click())
        assertTrue(activityRule.activity.bottom_Navigation_View.menu.findItem(R.id.action_home).isChecked)
        onView(withId(R.id.action_maps)).perform(click())
        assertTrue(activityRule.activity.bottom_Navigation_View.menu.findItem(R.id.action_maps).isChecked)
    }

    @UiThreadTest
    @Test
    @SmallTest
    @Throws(Throwable::class)
    fun testItemChecking() {
        val menu = activityRule.activity.bottom_Navigation_View.menu
        assertTrue(menu.getItem(0).isChecked)
        checkAndVerifyExclusiveItem(menu, R.id.action_home)
        checkAndVerifyExclusiveItem(menu, R.id.action_maps)
        checkAndVerifyExclusiveItem(menu, R.id.action_mortgage_simulation)
    }

    @UiThreadTest
    @Test
    @SmallTest
    @Throws(Throwable::class)
    fun testClearingMenu() {
        activityRule.activity.bottom_Navigation_View.menu.clear()
        assertEquals(0, activityRule.activity.bottom_Navigation_View.menu.size())
        activityRule.activity.bottom_Navigation_View.inflateMenu(R.menu.bottom_navigation_menu)
        assertEquals(3, activityRule.activity.bottom_Navigation_View.menu.size())
    }

    @UiThreadTest
    @Test
    @SmallTest
    @Throws(Throwable::class)
    fun testAutoLabelVisibilityItemChecking() {
        activityRule.activity.bottom_Navigation_View.menu.clear()
        activityRule.activity.bottom_Navigation_View.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_AUTO
        activityRule.activity.bottom_Navigation_View.inflateMenu(R.menu.bottom_navigation_menu)
        val menu = activityRule.activity.bottom_Navigation_View.menu
        assertTrue(menu.getItem(0).isChecked)
        checkAndVerifyExclusiveItem(menu, R.id.action_home)
        checkAndVerifyExclusiveItem(menu, R.id.action_maps)
        checkAndVerifyExclusiveItem(menu, R.id.action_mortgage_simulation)
    }

    @UiThreadTest
    @Test
    @SmallTest
    @Throws(Throwable::class)
    fun testSettingLabeledMenuItemVisibility() {
        activityRule.activity.bottom_Navigation_View.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        val homeMenuItem = activityRule.activity.bottom_Navigation_View.menu.findItem(R.id.action_home)
        assertTrue(homeMenuItem.isVisible)
        homeMenuItem.isVisible = false
        assertFalse(homeMenuItem.isVisible)
    }

    @Throws(Throwable::class)
    private fun checkAndVerifyExclusiveItem(menu: Menu, id: Int) {
        menu.findItem(id).isChecked = true
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            if (item.itemId == id) {
                assertTrue(item.isChecked)
            } else {
                assertFalse(item.isChecked)
            }
        }
    }
}