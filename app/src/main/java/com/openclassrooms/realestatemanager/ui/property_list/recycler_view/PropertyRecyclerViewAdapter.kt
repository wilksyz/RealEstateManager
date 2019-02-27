package com.openclassrooms.realestatemanager.ui.property_list.recycler_view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property

class PropertyRecyclerViewAdapter(private val context: Context?) : RecyclerView.Adapter<PropertyViewHolder>(){

    private var mPropertyList: List<Property> = ArrayList()
    private var mPictureList: List<Picture?> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PropertyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_list_property, parent, false)
        return PropertyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mPropertyList.size
    }

    override fun onBindViewHolder(propertyViewHolder: PropertyViewHolder, position: Int) {
        return propertyViewHolder.updateListOfProperty(mPropertyList[position], mPictureList[position], context)
    }

    fun updateData(propertyList: List<Property>, pictureList: List<Picture?>) {
        this.mPropertyList = propertyList
        this.mPictureList = pictureList
        this.notifyDataSetChanged()
    }

    fun clearList(){
        this.mPropertyList = ArrayList()
        this.mPictureList = ArrayList()
        this.notifyDataSetChanged()
    }

    fun getProperty(position: Int): Property {
        return mPropertyList[position]
    }
}