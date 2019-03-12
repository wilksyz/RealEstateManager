package com.openclassrooms.realestatemanager.ui.base_property_list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.base_property_list.recycler_view.PropertyRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_base_list_property.view.*

abstract class BasePropertyListFragment: Fragment() {

    protected lateinit var viewOfLayout: View
    protected lateinit var mAdapter: PropertyRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_base_list_property, container, false)

        this.configureRecyclerView()

        return viewOfLayout
    }

    private fun configureRecyclerView(){
        this.mAdapter = PropertyRecyclerViewAdapter(context)
        viewOfLayout.property_recyclerView_container.adapter = this.mAdapter
        viewOfLayout.property_recyclerView_container.layoutManager = LinearLayoutManager(this.context)
    }
}