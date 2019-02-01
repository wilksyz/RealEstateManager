package com.openclassrooms.realestatemanager.ui.property_details


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Property

/**
 * A simple [Fragment] subclass.
 *
 */
private const val PROPERTY_ID: String = "property id"
class PropertyDetailFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private lateinit var mPropertyDetailViewModel: PropertyDetailViewModel
    private var mProperty: Property? = null
    private var mPropertyId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_details_property, container, false)

        mPropertyId = this.getPropertyId()
        this.configureViewModel()
        mProperty = this.getProperty(mPropertyId)
        //Toast.makeText(context,"$mPropertyId type: ${mProperty?.typeProperty}", Toast.LENGTH_SHORT).show()


        return viewOfLayout
    }

    private fun getPropertyId(): Long{
        var propertyId = arguments?.get(PROPERTY_ID)
        if (propertyId == null){
            propertyId = activity?.intent?.getLongExtra(PROPERTY_ID, 0)
        }
        return propertyId as Long
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this.context!!)
        this.mPropertyDetailViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyDetailViewModel::class.java)
    }

    // Update an property
    private fun updateProperty(property: Property) {
        this.mPropertyDetailViewModel.updateProperty(property)
    }

    // get an property
    private fun getProperty(id: Long): Property? {
        val t = this.mPropertyDetailViewModel.getProperty(id).value
        Toast.makeText(context,"$mPropertyId type: ${mProperty?.typeProperty}", Toast.LENGTH_SHORT).show()
        return t
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putAll(outState)
        }
        super.onSaveInstanceState(outState)
    }
}
