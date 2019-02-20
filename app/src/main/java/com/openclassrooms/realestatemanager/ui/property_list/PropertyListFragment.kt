package com.openclassrooms.realestatemanager.ui.property_list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.ui.property_list.recycler_view.PropertyRecyclerViewAdapter
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
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
        this.configureClickRecyclerView()
        this.getAllProperty()

        return viewOfLayout
    }

    private fun configureRecyclerView(){
        this.mAdapter = PropertyRecyclerViewAdapter(Glide.with(this))
        viewOfLayout.property_recyclerView_container.adapter = this.mAdapter
        viewOfLayout.property_recyclerView_container.layoutManager = LinearLayoutManager(this.context)
    }

    private fun configureClickRecyclerView(){
        ItemClickSupport.addTo(viewOfLayout.property_recyclerView_container, R.layout.item_list_property)
                .setOnItemClickListener { _, position, _ ->
                    Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
                    val property = mAdapter.getProperty(position)
                    (activity as PropertyListActivity).configureDetailsPropertyFragment(property)
                }
    }

    override fun onResume() {

        super.onResume()
    }

    private fun configureViewModel() {
        val mViewModelFactory = context?.let { Injection().provideViewModelFactory(it) }
        this.mPropertyListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyListViewModel::class.java)
    }

    // Get all properties in database
    private fun getAllProperty() {
        val pictureList: MutableList<Picture?> = ArrayList()
        this.mPropertyListViewModel.getAllProperty().observe(this, android.arch.lifecycle.Observer{ list ->
            val propertyList = list?.iterator()
            if (propertyList != null) {
                for (property in propertyList){
                    this.mPropertyListViewModel.getPicture(property.mPropertyId).observe(this, android.arch.lifecycle.Observer {l ->
                        if (l?.size == 0){
                            pictureList.add(null)
                        }else{
                            l?.let { pictureList.add(it[0]) }
                        }
                        this.mAdapter.updateData(list, pictureList)
                    })
                }
            }
        } )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putAll(outState)
        }
        super.onSaveInstanceState(outState)
    }
}
