package com.openclassrooms.realestatemanager.activity

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.arch.lifecycle.ViewModelProviders
import android.util.Log
import com.facebook.stetho.Stetho
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private var textViewMain: TextView? = null
    private var textViewQuantity: TextView? = null
    private var itemViewModel: PropertyViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Stetho.initializeWithDefaults(this)

        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main)
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity)

        this.configureFragment()
        this.configureTextViewMain()
        this.configureTextViewQuantity()
        configureViewModel()
        //createProperty(Property("Manoir",650_000,300,27))

        getProperties()
    }

    private fun configureTextViewMain() {
        this.textViewMain!!.textSize = 15f
        this.textViewMain!!.text = "Le premier bien immobilier enregistré vaut "
    }

    private fun configureTextViewQuantity() {
        val quantity = Utils.convertDollarToEuro(100).toString()
        this.textViewQuantity!!.textSize = 20f
        this.textViewQuantity!!.text = quantity
    }

    private fun configureFragment(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = MainFragment()
        fragmentTransaction.add(R.id.main_fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this)
        this.itemViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel::class.java)
    }

    // Get all properties in database
    private fun getProperties() {
        val nameObserver = Observer<List<Property>> { newName ->
            // Update the UI, in this case, a TextView.
            val property = newName!![0]
            val string = "id: "+ property.id+" room: "+ property.numberOfRooms+" prix: "+property.price
            textViewQuantity!!.text = string
        }

        this.itemViewModel!!.getProperty().observe(this, nameObserver)
    }

    // Create a new property
    private fun createProperty(property: Property) {
        this.itemViewModel!!.createProperty(property)
    }

    // Delete an property
    private fun deleteProperty(property: Property) {
        this.itemViewModel!!.deleteProperty(property.id)
    }

    // Update an property
    private fun updateProperty(property: Property) {
        this.itemViewModel!!.updateItem(property)
    }
}
