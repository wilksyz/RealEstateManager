package com.openclassrooms.realestatemanager.ui.property_list.recycler_view

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import kotlinx.android.synthetic.main.item_list_property.view.*

class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun updateListOfProperty(property: Property, picture: Picture?, glide: RequestManager){
        if (picture != null){
            //glide.load(Uri.parse(picture.uri)).into(itemView.picture_view_holder_imageView)
            //itemView.picture_view_holder_imageView.setImageURI(Uri.parse(picture.uri))
        }



        itemView.type_property_view_holder_textView.text = property.typeProperty
        itemView.price_view_holder_textView.text = property.price
        itemView.location_view_holder_textView.text = property.address
    }

}