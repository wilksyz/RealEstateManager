package com.openclassrooms.realestatemanager.ui.base_property_list.property_list

import android.arch.lifecycle.ViewModelProviders
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.base_property_list.BasePropertyListFragment
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import kotlinx.android.synthetic.main.fragment_base_list_property.view.*

/**
 * A simple [Fragment] subclass.
 *
 */

private const val SELECTED_POSITION = "selected position"

class PropertyListFragment: BasePropertyListFragment() {

    private lateinit var mPropertyListViewModel: PropertyListViewModel
    private var mSelectedPosition: Int = RecyclerView.NO_POSITION

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.configureViewModel()
        this.configureClickRecyclerView()
        if (savedInstanceState != null){
            mSelectedPosition = savedInstanceState.getInt(SELECTED_POSITION)
            mAdapter.onClickRecyclerView(mSelectedPosition)
        }
    }

    override fun onResume() {
        this.getAllProperty()
        super.onResume()
    }

    private fun configureViewModel() {
        val mViewModelFactory = context?.let { Injection().provideViewModelFactory(it) }
        this.mPropertyListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyListViewModel::class.java)
    }

    private fun configureClickRecyclerView(){
        ItemClickSupport.addTo(viewOfLayout.property_recyclerView_container, R.layout.item_list_property)
                .setOnItemClickListener { _, position, _ ->
                    val property = mAdapter.getProperty(position)
                    mSelectedPosition = position
                    mAdapter.onClickRecyclerView(position)
                    (activity as PropertyListActivity).configureDetailsPropertyFragment(property)
                }
    }

    // Get all properties in database
    private fun getAllProperty() {
        this.mAdapter.clearList()
        mPropertyListViewModel.getAllProperty().observe(this, Observer{ propertyListLambda ->
            propertyListLambda?.let { this.getPicture(it) }
        })
    }

    // Get the pictures of the property
    private fun getPicture(propertyList: List<Property>){
        val pictureList: MutableList<Picture?> = ArrayList()
        var i = 0
        if (propertyList.isNotEmpty()){
            for (property in propertyList){
                mPropertyListViewModel.getPicture(property.mPropertyId).observe(this, Observer {pictureListLambda ->
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
