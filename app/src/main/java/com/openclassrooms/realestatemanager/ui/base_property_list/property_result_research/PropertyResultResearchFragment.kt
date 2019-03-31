package com.openclassrooms.realestatemanager.ui.base_property_list.property_result_research


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.InterestPoint
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.base_property_list.BasePropertyListFragment
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import kotlinx.android.synthetic.main.fragment_base_list_property.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */

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
private const val SELECTED_POSITION = "selected position"

class PropertyResultResearchFragment : BasePropertyListFragment() {

    private lateinit var mPropertyResultOfResearchViewModel: PropertyResultOfResearchViewModel
    private var mSelectedPosition: Int = RecyclerView.NO_POSITION

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.configureViewModel()
        this.configureClickRecyclerView()
        this.getSettingsOfResearch()
        if (savedInstanceState != null){
            mSelectedPosition = savedInstanceState.getInt(SELECTED_POSITION)
            mAdapter.onClickRecyclerView(mSelectedPosition)
        }
    }

    private fun configureViewModel() {
        val mViewModelFactory = context?.let { Injection().provideViewModelFactory(it) }
        this.mPropertyResultOfResearchViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyResultOfResearchViewModel::class.java)
    }

    private fun configureClickRecyclerView(){
        ItemClickSupport.addTo(viewOfLayout.property_recyclerView_container, R.layout.item_list_property)
                .setOnItemClickListener { _, position, _ ->
                    mSelectedPosition = position
                    mAdapter.onClickRecyclerView(position)
                    val property = mAdapter.getProperty(position)
                    (activity as PropertyResultOfResearchActivity).configureDetailsPropertyFragment(property)
                }
    }

    // Get the parameters for the search request
    private fun getSettingsOfResearch(){
        val typePropertyIndex = activity?.intent?.getIntExtra(TYPE_PROPERTY, -1)!!
        val surfaceMin = activity?.intent?.getIntExtra(SURFACE_MIN, -1)!!
        val surfaceMax = activity?.intent?.getIntExtra(SURFACE_MAX, -1)!!
        val school = activity?.intent?.getBooleanExtra(InterestPoint.SCHOOL, false)!!
        val parc = activity?.intent?.getBooleanExtra(InterestPoint.PARC, false)!!
        val stores = activity?.intent?.getBooleanExtra(InterestPoint.STORES, false)!!
        val publicTransport = activity?.intent?.getBooleanExtra(InterestPoint.PUBLIC_TRANSPORT, false)!!
        val doctor = activity?.intent?.getBooleanExtra(InterestPoint.DOCTOR, false)!!
        val hobbies = activity?.intent?.getBooleanExtra(InterestPoint.HOBBIES, false)!!
        val propertySold = activity?.intent?.getBooleanExtra(PROPERTY_SOLD, false)!!
        val soldDateIndex = activity?.intent?.getIntExtra(SOLD_DATE, -1)!!
        val cityName = activity?.intent?.getStringExtra(CITY_NAME)!!
        val numberPhoto = activity?.intent?.getIntExtra(NUMBER_PHOTO, -1)!!
        val priceMin = activity?.intent?.getIntExtra(PRICE_MIN, -1)!!
        val priceMax = activity?.intent?.getIntExtra(PRICE_MAX, -1)!!
        val dateMinSale = activity?.intent?.getLongExtra(DATE_MIN_SALE, -1)?.let { Date(it) }!!
        val dateMaxSale = activity?.intent?.getLongExtra(DATE_MAX_SALE, -1)?.let { Date(it) }!!

        this.startRequestOnDatabase(typePropertyIndex,
                surfaceMin,
                surfaceMax,
                SearchParameters.getInterestPointList(school),
                SearchParameters.getInterestPointList(parc),
                SearchParameters.getInterestPointList(stores),
                SearchParameters.getInterestPointList(publicTransport),
                SearchParameters.getInterestPointList(doctor),
                SearchParameters.getInterestPointList(hobbies),
                propertySold,
                soldDateIndex,
                cityName,
                numberPhoto,
                priceMin,
                priceMax,
                dateMinSale,
                dateMaxSale)
    }

    // Search in database the properties corresponding to the request
    private fun startRequestOnDatabase(typePropertyIndex: Int,
                                       surfaceMin: Int,
                                       surfaceMax: Int,
                                       school: List<Boolean>,
                                       parc: List<Boolean>,
                                       stores: List<Boolean>,
                                       publicTransport: List<Boolean>,
                                       doctor: List<Boolean>,
                                       hobbies: List<Boolean>,
                                       propertySold: Boolean,
                                       soldDateIndex: Int,
                                       cityName: String,
                                       numberPhoto: Int,
                                       priceMin: Int,
                                       priceMax: Int,
                                       dateMinSale: Date,
                                       dateMaxSale: Date){
        this.mAdapter.clearList()
        val soldMinDate = SearchParameters.getSoldDate(soldDateIndex, propertySold, Date())
        val typeProperty = SearchParameters.getTypeProperty(typePropertyIndex)
        val cityNameLike = "%$cityName%"

        mPropertyResultOfResearchViewModel.getPropertyResearch(typeProperty,
                surfaceMin,surfaceMax,
                doctor,school,hobbies,publicTransport,parc,stores,
                cityNameLike,numberPhoto,
                priceMin, priceMax,
                dateMinSale,dateMaxSale,
                propertySold,soldMinDate, Date()).observe(this, android.arch.lifecycle.Observer { propertyListLambda ->
            propertyListLambda?.let { this.getPicture(it) }
        })
    }

    // Get the pictures of the property
    private fun getPicture(propertyList: List<Property>){
        val pictureList: MutableList<Picture?> = ArrayList()
        var i = 0
        if (propertyList.isNotEmpty()){
            for (property in propertyList){
                mPropertyResultOfResearchViewModel.getPicture(property.mPropertyId).observe(this, android.arch.lifecycle.Observer {pictureListLambda ->
                    if (pictureListLambda?.size == 0){
                        pictureList.add(null)
                    }else{
                        pictureListLambda?.let { pictureList.add(it[0]) }
                    }
                    i++
                    if (i == propertyList.size){
                        this.mAdapter.updateData(propertyList, pictureList)
                    }
                })
            }
        }else {
            viewOfLayout.no_result_research_textView.visibility = View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putAll(outState)
            putInt(SELECTED_POSITION, mSelectedPosition)
        }
        super.onSaveInstanceState(outState)
    }
}
