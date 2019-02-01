package com.openclassrooms.realestatemanager.ui.property_details


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.openclassrooms.realestatemanager.R

/**
 * A simple [Fragment] subclass.
 *
 */
private const val PROPERTY_ID: String = "property id"
class PropertyDetailFragment : Fragment() {

    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_details_property, container, false)

        var propertyId = arguments?.get(PROPERTY_ID)
        if (propertyId == null){
            propertyId = activity?.intent?.getLongExtra(PROPERTY_ID, 0)
        }
        Toast.makeText(context,"$propertyId", Toast.LENGTH_SHORT).show()

        return viewOfLayout
    }


}
