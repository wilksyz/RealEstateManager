package com.openclassrooms.realestatemanager.ui.property_research

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_property_search.*
import java.text.DateFormat
import java.util.*

class PropertyResearchActivity : AppCompatActivity() {

    private val surfaceBoard = arrayOf(0, 100, 200, 300, 400, 500, 750, 1000, 1250, 1500, 1750, 2000, 2500, 3000)
    private val numberPhotoBoard = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    private val priceBoard = arrayOf(0, 50_000, 100_000, 150_000, 200_000, 250_000, 300_000, 400_000, 500_000, 600_000, 700_000, 800_000, 900_000, 1_000_000)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_search)

        this.configureSpinner()

        date_min_search_button.setOnClickListener {
            this.getDate()
        }
        date_max_serach_button.setOnClickListener {
            this.getDate()
        }
        sold_search_checkBox.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked){
                true -> date_sold_search_spinner.visibility = View.VISIBLE
                false -> date_sold_search_spinner.visibility = View.INVISIBLE
            }
        }
    }

    private fun configureSpinner(){
        val typePropertyAdapter = ArrayAdapter.createFromResource(this, R.array.type_property_array, android.R.layout.simple_spinner_item)
        typePropertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        type_of_property_spinner_search.adapter = typePropertyAdapter
        val surfaceMinAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, surfaceBoard)
        surfaceMinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        surface_mini_spinner_search.adapter = surfaceMinAdapter
        val surfaceMaxAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, surfaceBoard)
        surfaceMaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        surface_maxi_spinner_search.adapter = surfaceMaxAdapter
        surface_maxi_spinner_search.setSelection(13)
        val numberOfPhoto = ArrayAdapter(this, android.R.layout.simple_spinner_item, numberPhotoBoard)
        numberOfPhoto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        number_photo_search_spinner.adapter = numberOfPhoto
        val priceMinAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priceBoard)
        priceMinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        price_min_search_spinner.adapter = priceMinAdapter
        val priceMaxAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priceBoard)
        priceMaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        price_max_search_spinner.adapter = priceMaxAdapter
        price_max_search_spinner.setSelection(13)
        val dateSoldAdapter = ArrayAdapter.createFromResource(this, R.array.choice_date_sold_search, android.R.layout.simple_spinner_item)
        dateSoldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        date_sold_search_spinner.adapter = dateSoldAdapter
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, pYear, pMonth, pDay ->
            val formattedDay = if (pDay < 10) "0$pDay" else "$pDay"
            val formattedMonth = pMonth + 1
            val dateString = if (formattedMonth < 10) "$formattedDay/0$formattedMonth/$pYear" else "$formattedDay/$formattedMonth/$pYear"
            val df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE)
            val date: Date
            date = df.parse(dateString)
        }, year, month, day)
        dialog.datePicker.maxDate = Date().time
        dialog.show()
    }
}
