package com.openclassrooms.realestatemanager.ui.base_property_list.property_result_research


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
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

class PropertyResultResearchFragment : BasePropertyListFragment() {

    private lateinit var mPropertyResultOfResearchViewModel: PropertyResultOfResearchViewModel
    private val mIntervalDate = arrayOf(-730, -7, -14, -30, -60, -180, -365)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.configureViewModel()
        this.configureClickRecyclerView()
        this.getSettingsOfResearch()
    }

    private fun configureViewModel() {
        val mViewModelFactory = context?.let { Injection().provideViewModelFactory(it) }
        this.mPropertyResultOfResearchViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyResultOfResearchViewModel::class.java)
    }

    private fun configureClickRecyclerView(){
        ItemClickSupport.addTo(viewOfLayout.property_recyclerView_container, R.layout.item_list_property)
                .setOnItemClickListener { _, position, _ ->
                    Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
                    val property = mAdapter.getProperty(position)
                    (activity as PropertyResultOfResearchActivity).configureDetailsPropertyFragment(property)
                }
    }

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
                getInterestPointList(school),
                getInterestPointList(parc),
                getInterestPointList(stores),
                getInterestPointList(publicTransport),
                getInterestPointList(doctor),
                getInterestPointList(hobbies),
                propertySold,
                soldDateIndex,
                cityName,
                numberPhoto,
                priceMin,
                priceMax,
                dateMinSale,
                dateMaxSale)
    }

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
        val pictureList: MutableList<Picture?> = ArrayList()
        val soldMinDate = getSoldDate(soldDateIndex, propertySold)
        val typeProperty = getTypeProperty(typePropertyIndex)
        val cityNameLike = "%$cityName%"

        mPropertyResultOfResearchViewModel.getPropertyResearch(typeProperty,
                surfaceMin,surfaceMax,
                doctor,school,hobbies,publicTransport,parc,stores,
                cityNameLike,numberPhoto,
                priceMin, priceMax,
                dateMinSale,dateMaxSale,
                propertySold,soldMinDate, Date()).observe(this, android.arch.lifecycle.Observer { propertyListLambda ->
            val propertyList = propertyListLambda?.iterator()
            if (propertyList != null) {
                for (property in propertyList){
                    var i = 0
                    this.mPropertyResultOfResearchViewModel.getPicture(property.mPropertyId).observe(this, android.arch.lifecycle.Observer {pictureListLambda ->
                        if (pictureListLambda?.size == 0){
                            pictureList.add(null)
                        }else{
                            pictureListLambda?.let { pictureList.add(it[0]) }
                        }
                        i++
                        if (i == propertyListLambda.size){
                            this.mAdapter.updateData(propertyListLambda, pictureList)
                        }
                    })
                }
            }
        })
    }

    private fun getSoldDate(soldDateIndex: Int, propertySold: Boolean): Date {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        return if (propertySold){
            calendar.add(Calendar.DAY_OF_YEAR, mIntervalDate[soldDateIndex])
            calendar.time
        }else{
            calendar.add(Calendar.DAY_OF_YEAR, mIntervalDate[0])
            calendar.time
        }
    }

    private fun getTypeProperty(typePropertyIndex: Int): ArrayList<Int>{
        val typeProperty = arrayListOf<Int>()
        val typePropertyInt = arrayOf(0, 1, 2, 3, 4, 5, 6)
        return if (typePropertyIndex == Property.TYPE_ALL){
            typeProperty.addAll(typePropertyInt)
            typeProperty
        }else{
            typeProperty.add(typePropertyIndex)
            typeProperty
        }
    }

    private fun getInterestPointList(interestPoint: Boolean): ArrayList<Boolean>{
        val interestPointList = arrayListOf<Boolean>()
        interestPointList.add(true)
        if (!interestPoint) interestPointList.add(false)
        return interestPointList
    }
}
