package com.openclassrooms.realestatemanager.ui.property_mortgage

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_property_mortgage.*

private const val PROPERTY_PRICE: String = "property price"

class PropertyMortgageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_mortgage)

        this.getPrice()
        this.configureSpinner()


    }

    private fun getPrice(){
        val price = intent.getIntExtra(PROPERTY_PRICE, 0)
        val priceString = "$ $price"
        price_mortgage_textview.text = priceString
    }

    private fun configureSpinner(){
        val durationMortgageSimulation = ArrayAdapter.createFromResource(this, R.array.duration_mortgage_simulation, android.R.layout.simple_spinner_item)
        durationMortgageSimulation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        duration_mortgage_spinner.adapter = durationMortgageSimulation
    }
}
