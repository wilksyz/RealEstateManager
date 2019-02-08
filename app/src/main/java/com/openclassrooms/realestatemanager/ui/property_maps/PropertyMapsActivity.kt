package com.openclassrooms.realestatemanager.ui.property_maps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_property_maps.*

class PropertyMapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_maps)

        property_maps_mapView
    }
}
