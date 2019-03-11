package com.openclassrooms.realestatemanager.ui.property_research

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.DOCTOR
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.HOBBIES
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.PARC
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.PUBLIC_TRANSPORT
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.SCHOOL
import com.openclassrooms.realestatemanager.model.InterestPoint.Companion.STORES
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_result_research.PropertyResultOfResearchActivity
import kotlinx.android.synthetic.main.activity_property_search.*
import java.text.DateFormat
import java.util.*

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

class PropertyResearchActivity : AppCompatActivity(), OnItemSelectedListener {

    private val mSurfaceBoard = arrayOf(0, 100, 200, 300, 400, 500, 750, 1000, 1250, 1500, 1750, 2000, 2500, 3000)
    private val mNumberPhotoBoard = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    private val mPriceBoard = arrayOf(0, 50_000, 100_000, 150_000, 200_000, 250_000, 300_000, 400_000, 500_000, 600_000, 700_000, 800_000, 900_000, 1_000_000)
    private val mTypePropertySearch = arrayListOf<String>()
    private var mDateMinSale: Date = Date(1551100025819)
    private var mDateMaxSale: Date = Date()
    private var mDateCorrect: Boolean = true
    private var mSurfaceCorrect: Boolean = true
    private var mPriceCorrect: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_search)
        mTypePropertySearch.add(getString(R.string.all))
        mTypePropertySearch.addAll(resources.getStringArray(R.array.type_property_array))
        this.configureSpinner()

        date_min_search_button.setOnClickListener {
            this.getDate(0)
        }
        date_max_serach_button.setOnClickListener {
            this.getDate(1)
        }
        research_button.setOnClickListener {
            this.retrieveTheParametersOfTheResearch()
        }
        sold_search_checkBox.setOnCheckedChangeListener { _, isChecked ->
            when(isChecked){
                true -> date_sold_search_spinner.visibility = View.VISIBLE
                false -> date_sold_search_spinner.visibility = View.INVISIBLE
            }
        }
    }

    private fun configureSpinner(){
        val typePropertyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mTypePropertySearch)
        typePropertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        type_of_property_spinner_search.adapter = typePropertyAdapter
        val surfaceMinAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mSurfaceBoard)
        surfaceMinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        surface_mini_spinner_search.adapter = surfaceMinAdapter
        surface_mini_spinner_search.tag = 1941
        surface_mini_spinner_search.onItemSelectedListener = this
        val surfaceMaxAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mSurfaceBoard)
        surfaceMaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        surface_maxi_spinner_search.adapter = surfaceMaxAdapter
        surface_maxi_spinner_search.setSelection(13)
        surface_maxi_spinner_search.tag = 1942
        surface_maxi_spinner_search.onItemSelectedListener = this
        val numberOfPhoto = ArrayAdapter(this, android.R.layout.simple_spinner_item, mNumberPhotoBoard)
        numberOfPhoto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        number_photo_search_spinner.adapter = numberOfPhoto
        val priceMinAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mPriceBoard)
        priceMinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        price_min_search_spinner.adapter = priceMinAdapter
        price_min_search_spinner.tag = 1943
        price_min_search_spinner.onItemSelectedListener = this
        val priceMaxAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mPriceBoard)
        priceMaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        price_max_search_spinner.adapter = priceMaxAdapter
        price_max_search_spinner.setSelection(13)
        price_max_search_spinner.tag = 1944
        price_max_search_spinner.onItemSelectedListener = this
        val dateSoldAdapter = ArrayAdapter.createFromResource(this, R.array.choice_date_sold_search, android.R.layout.simple_spinner_item)
        dateSoldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        date_sold_search_spinner.adapter = dateSoldAdapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) { }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.tag == 1941 || parent?.tag == 1942){
            if (surface_maxi_spinner_search.selectedItemPosition < surface_mini_spinner_search.selectedItemPosition){
                error_surface_search_textView.visibility = View.VISIBLE
                ViewCompat.setBackgroundTintList(surface_mini_spinner_search, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorRed)))
                ViewCompat.setBackgroundTintList(surface_maxi_spinner_search, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorRed)))
                mSurfaceCorrect = false
                research_button.isEnabled = false
            }else {
                error_surface_search_textView.visibility = View.GONE
                ViewCompat.setBackgroundTintList(surface_mini_spinner_search, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.common_google_signin_btn_text_light_default)))
                ViewCompat.setBackgroundTintList(surface_maxi_spinner_search, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.common_google_signin_btn_text_light_default)))
                mSurfaceCorrect = true
                research_button.isEnabled = true
            }
        }
        if (parent?.tag == 1943 || parent?.tag == 1944){
            if (price_max_search_spinner.selectedItemPosition < price_min_search_spinner.selectedItemPosition){
                error_price_search_textView.visibility = View.VISIBLE
                ViewCompat.setBackgroundTintList(price_min_search_spinner, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorRed)))
                ViewCompat.setBackgroundTintList(price_max_search_spinner, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorRed)))
                mPriceCorrect = false
                research_button.isEnabled = false
            }else {
                error_price_search_textView.visibility = View.GONE
                ViewCompat.setBackgroundTintList(price_min_search_spinner, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.common_google_signin_btn_text_light_default)))
                ViewCompat.setBackgroundTintList(price_max_search_spinner, ColorStateList.valueOf(ContextCompat.getColor(this, R.color.common_google_signin_btn_text_light_default)))
                mPriceCorrect = true
                research_button.isEnabled = true
            }
        }
    }

    private fun retrieveTheParametersOfTheResearch(){
        if (mDateCorrect && mSurfaceCorrect && mPriceCorrect){
            val intent = Intent(this, PropertyResultOfResearchActivity::class.java)
            intent.putExtra(TYPE_PROPERTY, getTypeProperty())
            intent.putExtra(SURFACE_MIN, mSurfaceBoard[surface_mini_spinner_search.selectedItemPosition])
            intent.putExtra(SURFACE_MAX, mSurfaceBoard[surface_maxi_spinner_search.selectedItemPosition])
            intent.putExtra(SCHOOL, school_search_checkBox.isChecked)
            intent.putExtra(PARC, parc_search_checkBox.isChecked)
            intent.putExtra(STORES, stores_search_checkBox.isChecked)
            intent.putExtra(PUBLIC_TRANSPORT, public_transport_search_checkBox.isChecked)
            intent.putExtra(DOCTOR, doctor_search_checkBox.isChecked)
            intent.putExtra(HOBBIES, hobbies_search_checkBox.isChecked)
            intent.putExtra(PROPERTY_SOLD, sold_search_checkBox.isChecked)
            intent.putExtra(SOLD_DATE, date_sold_search_spinner.selectedItemPosition)
            intent.putExtra(CITY_NAME, city_search_editText.text.toString())
            intent.putExtra(NUMBER_PHOTO, mNumberPhotoBoard[number_photo_search_spinner.selectedItemPosition])
            intent.putExtra(PRICE_MIN, mPriceBoard[price_min_search_spinner.selectedItemPosition])
            intent.putExtra(PRICE_MAX, mPriceBoard[price_max_search_spinner.selectedItemPosition])
            intent.putExtra(DATE_MIN_SALE, mDateMinSale.time)
            intent.putExtra(DATE_MAX_SALE, mDateMaxSale.time)
            startActivity(intent)
        }
    }

    private fun getTypeProperty(): Int{
        return when(type_of_property_spinner_search.selectedItemPosition){
            0 -> Property.TYPE_ALL
            1 -> Property.TYPE_HOUSE
            2 -> Property.TYPE_LOFT
            3 -> Property.TYPE_CASTLE
            4 -> Property.TYPE_APARTMENT
            5 -> Property.TYPE_RANCH
            6 -> Property.TYPE_PENTHOUSE
            7 -> Property.TYPE_MANOR
            else -> -1
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getDate(tagButton: Int){
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
            when(tagButton){
                0 -> {
                    date_min_search_button.text = dateString
                    mDateMinSale = date
                }
                1 -> {
                    date_max_serach_button.text = dateString
                    mDateMaxSale = date
                }
            }
            mDateCorrect = this.checkDate()
        }, year, month, day)
        dialog.datePicker.maxDate = Date().time
        dialog.show()
    }

    private fun checkDate(): Boolean{
        return if (mDateMaxSale >= mDateMinSale){
            date_max_search_view.setBackgroundColor(ContextCompat.getColor(this, R.color.common_google_signin_btn_text_light_default))
            date_min_search_view.setBackgroundColor(ContextCompat.getColor(this, R.color.common_google_signin_btn_text_light_default))
            research_button.isEnabled = true
            true
        }else {
            date_max_search_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
            date_min_search_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
            research_button.isEnabled = false
            false
        }
    }
}
