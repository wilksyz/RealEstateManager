package com.openclassrooms.realestatemanager.ui.base_property_list.recycler_view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property

class PropertyRecyclerViewAdapter(private val context: Context?) : RecyclerView.Adapter<PropertyViewHolder>(){

    private var mPropertyList: MutableList<Property> = ArrayList()
    private var mPictureList: MutableList<Picture?> = ArrayList()
    private var mSelectedPosition: Int = RecyclerView.NO_POSITION

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
        propertyViewHolder.itemView.isSelected = mSelectedPosition == position

        Log.e("TAG Adapter", "${mPropertyList.size}, ${mPictureList.size}")
        return propertyViewHolder.updateListOfProperty(mPropertyList[position], mPictureList[position], context)
    }

    fun updateData(propertyList: List<Property>, pictureList: List<Picture?>) {
        this.mPropertyList = propertyList as MutableList<Property>
        this.mPictureList = pictureList as MutableList<Picture?>
        this.notifyDataSetChanged()
    }

    fun clearList(){
        this.mPropertyList.clear()
        this.mPictureList.clear()
        this.notifyDataSetChanged()
    }

    fun getProperty(position: Int): Property {
        return mPropertyList[position]
    }

    fun onClickRecyclerView(position: Int){
        notifyItemChanged(mSelectedPosition)
        mSelectedPosition = position
        notifyItemChanged(mSelectedPosition)
    }
}