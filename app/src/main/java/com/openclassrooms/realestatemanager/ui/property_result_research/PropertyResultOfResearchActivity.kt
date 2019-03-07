package com.openclassrooms.realestatemanager.ui.property_result_research

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_list.recycler_view.PropertyRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_property_result_of_research.*
import java.util.*
import kotlin.collections.ArrayList

private const val TYPE_PROPERTY = "type property"
private const val SURFACE_MIN = "surface min"
private const val SURFACE_MAX = "surface max"
private const val SCHOOL = "school"
private const val PARC = "parc"
private const val STORES = "stores"
private const val PUBLIC_TRANSPORT = "public transport"
private const val DOCTOR = "doctor"
private const val HOBBIES = "hobbies"
private const val PROPERTY_SOLD = "property sold"
private const val SOLD_DATE = "sold date"
private const val CITY_NAME = "city name"
private const val NUMBER_PHOTO = "number photo"
private const val PRICE_MIN = "price min"
private const val PRICE_MAX = "price max"
private const val DATE_MIN_SALE = "date min sale"
private const val DATE_MAX_SALE = "date max sale"

class PropertyResultOfResearchActivity : AppCompatActivity() {

    private lateinit var mPropertyResultOfResearchViewModel: PropertyResultOfResearchViewModel
    private lateinit var mAdapter: PropertyRecyclerViewAdapter
    private val mIntervalDate = arrayOf(-730, -7, -14, -30, -60, -180, -365)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_result_of_research)

        this.configureViewModel()
        this.configureRecyclerView()
        this.getSettingsOfResearch()
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this)
        this.mPropertyResultOfResearchViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyResultOfResearchViewModel::class.java)
    }

    private fun configureRecyclerView(){
        this.mAdapter = PropertyRecyclerViewAdapter(this)
        property_recyclerView_research_container.adapter = this.mAdapter
        property_recyclerView_research_container.layoutManager = LinearLayoutManager(this)
    }

    private fun getSettingsOfResearch(){
        val typePropertyIndex = intent.getIntExtra(TYPE_PROPERTY, -1)
        val surfaceMin = intent.getIntExtra(SURFACE_MIN, -1)
        val surfaceMax = intent.getIntExtra(SURFACE_MAX, -1)
        val school = intent.getBooleanExtra(SCHOOL, false)
        val parc = intent.getBooleanExtra(PARC, false)
        val stores = intent.getBooleanExtra(STORES, false)
        val publicTransport = intent.getBooleanExtra(PUBLIC_TRANSPORT, false)
        val doctor = intent.getBooleanExtra(DOCTOR, false)
        val hobbies = intent.getBooleanExtra(HOBBIES, false)
        val propertySold = intent.getBooleanExtra(PROPERTY_SOLD, false)
        val soldDateIndex = intent.getIntExtra(SOLD_DATE, -1)
        val cityName = intent.getStringExtra(CITY_NAME)
        val numberPhoto = intent.getIntExtra(NUMBER_PHOTO, -1)
        val priceMin = intent.getIntExtra(PRICE_MIN, -1)
        val priceMax = intent.getIntExtra(PRICE_MAX, -1)
        val dateMinSale = Date(intent.getLongExtra(DATE_MIN_SALE, -1))
        val dateMaxSale = Date(intent.getLongExtra(DATE_MAX_SALE, -1))

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
                propertySold,soldMinDate, Date()).observe(this, Observer { propertyListLambda ->
            val propertyList = propertyListLambda?.iterator()
            if (propertyList != null) {
                for (property in propertyList){
                    var i = 0
                    this.mPropertyResultOfResearchViewModel.getPicture(property.mPropertyId).observe(this, Observer {pictureListLambda ->
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

    private fun getSoldDate(soldDateIndex: Int, propertySold: Boolean): Date{
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
