package com.openclassrooms.realestatemanager.ui.base_property_list.property_result_research

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.base_property_list.recycler_view.PropertyRecyclerViewAdapter

private const val TYPE_PROPERTY = "type property"
private const val SURFACE_MIN = "surface min"
private const val SURFACE_MAX = "surface max"
private const val PROPERTY_SOLD = "property sold"
private const val SOLD_DATE = "sold date"
private const val CITY_NAME = "city name"
private const val NUMBER_PHOTO = "number photo"
private const val PRICE_MIN = "price min"
private const val PRICE_MAX = "price max"
private const val DATE_MIN_SALE = "date min sale"
private const val DATE_MAX_SALE = "date max sale"

class PropertyResultOfResearchActivity : AppCompatActivity() {

    private val mFragmentManager = supportFragmentManager
    private lateinit var mAdapter: PropertyRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_result_of_research)
        this.configureFragment()


    }

    private fun configureFragment(){
        mFragmentManager.beginTransaction().replace(R.id.main_fragment_container, PropertyResultResearchFragment()).commit()
    }

}
