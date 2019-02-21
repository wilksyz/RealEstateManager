package com.openclassrooms.realestatemanager.ui.property_mortgage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_property_mortgage.*

private const val PROPERTY_PRICE: String = "property price"

class PropertyMortgageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_mortgage)

        val price = intent.getIntExtra(PROPERTY_PRICE, 0)
        price_mortgage_textview.text = price.toString()

    }
}
