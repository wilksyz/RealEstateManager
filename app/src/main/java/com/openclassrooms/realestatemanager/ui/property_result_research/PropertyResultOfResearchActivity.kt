package com.openclassrooms.realestatemanager.ui.property_result_research

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.ui.property_list.recycler_view.PropertyRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_property_result_of_research.*
import java.util.*
import kotlin.collections.ArrayList

class PropertyResultOfResearchActivity : AppCompatActivity() {

    private lateinit var mPropertyResultOfResearchViewModel: PropertyResultOfResearchViewModel
    private lateinit var mAdapter: PropertyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_result_of_research)

        this.configureViewModel()
        this.configureRecyclerView()
        this.test()
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this)
        this.mPropertyResultOfResearchViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyResultOfResearchViewModel::class.java)
    }

    private fun configureRecyclerView(){
        this.mAdapter = PropertyRecyclerViewAdapter(Glide.with(this))
        property_recyclerView_research_container.adapter = this.mAdapter
        property_recyclerView_research_container.layoutManager = LinearLayoutManager(this)
    }

    private fun test(){
        val doctor: MutableList<Int> = ArrayList()
        doctor.add()
        val school = List<Int>
        val hobbies = List<Int>
        val store = List<Int>
        val transport = List<Int>
        val parc = List<Int>
        getPropertyResearch("Apartment",300,200,)
    }

    private fun getPropertyResearch(typeProperty: String,
                                    minSurface: Int,
                                    maxSurface: Int,
                                    doctor: List<Int>,
                                    school: List<Int>,
                                    hobbies: List<Int>,
                                    transport: List<Int>,
                                    parc: List<Int>,
                                    store: List<Int>,
                                    minDateOfSale: Date,
                                    maxDateOfSale: Date,
                                    saleStatus: Int,
                                    minDateSold: Date,
                                    maxDateSold: Date,
                                    city: String,
                                    pNumberOfPhotos: Int,
                                    pMinPrice: Int,
                                    pMaxPrice: Int){
        val pictureList: MutableList<Picture?> = ArrayList()
        mPropertyResultOfResearchViewModel.getPropertyResearch(typeProperty,
                minSurface,
                maxSurface,
                doctor,
                school,
                hobbies,
                transport,
                parc,
                store,
                minDateOfSale,
                maxDateOfSale,
                saleStatus,
                minDateSold,
                maxDateSold,
                city,
                pNumberOfPhotos,
                pMinPrice,
                pMaxPrice).observe(this, Observer { list ->
            val propertyList = list?.iterator()
            if (propertyList != null) {
                for (property in propertyList){
                    this.mPropertyResultOfResearchViewModel.getPicture(property.mPropertyId).observe(this, Observer {l ->
                        if (l?.size == 0){
                            pictureList.add(null)
                        }else{
                            l?.let { pictureList.add(it[0]) }
                        }
                        this.mAdapter.updateData(list, pictureList)
                    })
                }
            }
        })
    }
}
