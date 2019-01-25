package com.openclassrooms.realestatemanager.ui.property_list


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_list.recycler_view.PropertyRecyclerViewAdapter
import com.openclassrooms.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.fragment_list_property.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
class PropertyListFragment : Fragment() {

    private lateinit var mPropertyListViewModel: PropertyListViewModel
    private lateinit var mAdapter: PropertyRecyclerViewAdapter
    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_list_property, container, false)
        this.configureRecyclerView()
        this.configureViewModel()
        getAllProperty()
        //createProperty(Property("Manoir",650_000,300,27))

        return viewOfLayout
    }

    private fun createPicture(picture: Picture) {
        this.mPropertyListViewModel.createPicture(picture)
    }

    private fun configureRecyclerView(){
        this.mAdapter = PropertyRecyclerViewAdapter(Glide.with(this))
        viewOfLayout.property_recyclerView_container.adapter = this.mAdapter
        viewOfLayout.property_recyclerView_container.layoutManager = LinearLayoutManager(this.context)
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this.context!!)
        this.mPropertyListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyListViewModel::class.java)
    }

    // Get all properties in database
    private fun getAllProperty() {
        this.mPropertyListViewModel.getAllProperty().observe(this, Observer{list ->
            list?.let{ this.mAdapter.updateData(it) }
        } )
    }

    private fun getProperty(id: Long){
        this.mPropertyListViewModel.getProperty(id)
    }

    private fun updatePropertiesList(property: List<Property>) {
        this.mAdapter.updateData(property)
    }

    // Create a new property
    private fun createProperty(property: Property) {
        this.mPropertyListViewModel.createProperty(property)
    }

    // Delete an property
    private fun deleteProperty(property: Property) {
        this.mPropertyListViewModel.deleteProperty(property.mPropertyId)
    }

    // Update an property
    private fun updateProperty(property: Property) {
        this.mPropertyListViewModel.updateItem(property)
    }
}
