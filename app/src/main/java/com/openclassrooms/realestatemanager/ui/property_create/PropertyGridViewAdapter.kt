package com.openclassrooms.realestatemanager.ui.property_create

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Picture
import kotlinx.android.synthetic.main.item_create_property.view.*
import java.util.ArrayList
import android.widget.TextView



class PropertyGridViewAdapter(private val mContext: Context): BaseAdapter() {

    private var mPictureList = ArrayList<Picture>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        var holder: pictureViewHolder

        if (itemView == null)
        {
            val inflater = LayoutInflater.from(parent?.context)
            itemView = inflater.inflate(R.layout.item_create_property, parent, false)


            holder =  pictureViewHolder()
            holder.imgItem = itemView.picture_imageview_gridview
            holder.txtItem = itemView.tittle_imageview_gridview
            itemView.tag = holder
        }
        else
        {
            holder = itemView.tag as pictureViewHolder
        }
        holder.imgItem?.setImageURI(Uri.parse(mPictureList[position].uri))
        holder.txtItem?.text = mPictureList[position].tittle

        return itemView!!
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mPictureList.size
    }

    fun updateData(propertyList: ArrayList<Picture>) {
        this.mPictureList = propertyList
        this.notifyDataSetChanged()
    }

    internal class pictureViewHolder {
        var imgItem: ImageView? = null
        var txtItem: TextView? = null
    }
}