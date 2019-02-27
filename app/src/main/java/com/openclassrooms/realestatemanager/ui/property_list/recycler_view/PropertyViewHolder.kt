package com.openclassrooms.realestatemanager.ui.property_list.recycler_view

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import kotlinx.android.synthetic.main.item_list_property.view.*

class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun updateListOfProperty(property: Property, picture: Picture?, context: Context?){

        if (picture != null){
            itemView.picture_view_holder_imageView.setImageURI(Uri.parse(picture.uri))
        }
        itemView.type_property_view_holder_textView.text = property.typeProperty.toString()
        itemView.type_property_view_holder_textView.text = when(property.typeProperty){
            0 -> context?.resources?.getStringArray(R.array.type_property_array)?.get(0)
            1 -> context?.resources?.getStringArray(R.array.type_property_array)?.get(1)
            2 -> context?.resources?.getStringArray(R.array.type_property_array)?.get(2)
            3 -> context?.resources?.getStringArray(R.array.type_property_array)?.get(3)
            4 -> context?.resources?.getStringArray(R.array.type_property_array)?.get(4)
            5 -> context?.resources?.getStringArray(R.array.type_property_array)?.get(5)
            6 -> context?.resources?.getStringArray(R.array.type_property_array)?.get(6)
            else -> "NC"
        }
        itemView.price_view_holder_textView.text = "$ ${property.price}"
        itemView.location_view_holder_textView.text = "${property.address.postCode} ${property.address.city}"
    }

}