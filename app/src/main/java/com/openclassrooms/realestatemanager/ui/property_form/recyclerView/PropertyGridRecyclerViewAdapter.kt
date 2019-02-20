package com.openclassrooms.realestatemanager.ui.property_form.recyclerView

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Picture
import kotlinx.android.synthetic.main.item_grid_picture_property.view.*

class PropertyGridRecyclerViewAdapter: RecyclerView.Adapter<PropertyGridRecyclerViewAdapter.PropertyGridRecyclerViewViewHolder>() {
    private var mPictureList: List<Picture> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PropertyGridRecyclerViewViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_grid_picture_property, parent, false)
        return PropertyGridRecyclerViewViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mPictureList.size
    }

    override fun onBindViewHolder(holder: PropertyGridRecyclerViewViewHolder, position: Int) {
        holder.updateUiGridLayout(mPictureList[position])
    }

    fun updateData(propertyList: List<Picture>) {
        this.mPictureList = propertyList
        this.notifyDataSetChanged()
    }

    fun getPicture(position: Int): Picture{
        return mPictureList[position]
    }

    class PropertyGridRecyclerViewViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun updateUiGridLayout(picture: Picture){
            view.picture_imageview_gridview.setImageURI(Uri.parse(picture.uri))
            view.tittle_imageview_gridview.text = picture.title
        }
    }
}