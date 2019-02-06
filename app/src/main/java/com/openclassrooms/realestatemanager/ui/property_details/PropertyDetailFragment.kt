package com.openclassrooms.realestatemanager.ui.property_details


import android.annotation.SuppressLint
import android.arch.lifecycle.*
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_create.PropertyGridRecyclerViewAdapter
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import kotlinx.android.synthetic.main.dialog_full_screen_picture.view.*
import kotlinx.android.synthetic.main.fragment_details_property.*
import kotlinx.android.synthetic.main.fragment_details_property.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
private const val PROPERTY_ID: String = "property id"
class PropertyDetailFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private lateinit var mPropertyDetailViewModel: PropertyDetailViewModel
    private lateinit var mProperty: Property
    private var mPropertyId: Long = 0
    private var mAdapterRecycler: PropertyGridRecyclerViewAdapter = PropertyGridRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_details_property, container, false)

        mPropertyId = this.getPropertyId()
        this.configureViewModel()
        this.configureGridRecyclerView()
        this.configureClickGridRecyclerView()
        this.getProperty(mPropertyId)
        this.getPicture(mPropertyId)

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
        val mViewModelFactory = context?.let { Injection().provideViewModelFactory(it) }
        this.mPropertyDetailViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyDetailViewModel::class.java)
    }

    private fun configureGridRecyclerView(){
        val recyclerView = context?.let { RecyclerView(it) }
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        recyclerView?.layoutParams = layoutParams
        val gridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)
        viewOfLayout.detail_property_recycler_view.adapter = mAdapterRecycler
        viewOfLayout.detail_property_recycler_view.layoutManager = gridLayoutManager
    }

    private fun configureClickGridRecyclerView(){
        ItemClickSupport.addTo(viewOfLayout.detail_property_recycler_view, R.layout.item_grid_picture_property)
                .setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                    override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                        val picture = mAdapterRecycler.getPicture(position)
                        val builder = context?.let { AlertDialog.Builder(it) }
                        val mView = layoutInflater.inflate(R.layout.dialog_full_screen_picture, null)
                        builder?.setView(mView)
                        mView.full_screen_picture_imageView.setImageURI(Uri.parse(picture.uri))
                        builder?.setPositiveButton(getString(R.string.close)) { _, _ ->
                        }
                        builder?.create()
                        builder?.show()
                    }
                })
    }

    // Update an property
    private fun updateProperty(property: Property) {
        this.mPropertyDetailViewModel.updateProperty(property)
    }

    // get an property
    @SuppressLint("SetTextI18n")
    private fun getProperty(id: Long) {
        this.mPropertyDetailViewModel.getProperty(id).observe(this, Observer { property ->
            property?.let { mProperty = it }
            detail_property_surface_textView.text = property?.surface
            detail_property_room_textView.text = property?.numberOfRooms
            detail_property_location_textView.text = property?.address?.number+" "+property?.address?.street+"\n"+property?.address?.postCode+"\n"+property?.address?.city
            detail_property_description_textView.text = property?.descriptionProperty
            Toast.makeText(context,"$mPropertyId type: ${mProperty.typeProperty}", Toast.LENGTH_SHORT).show()
            property?.let { getInterestPoint(it) }
        })
    }

    private fun getInterestPoint(property: Property){
        if (!property.interestPoint.doctor){
            doctor_imageView.visibility = View.GONE
            doctor_textView.visibility = View.GONE
        }
        if (property.interestPoint.hobbies){
            hobbies_imageView.visibility = View.GONE
            hobbies_textView.visibility = View.GONE
        }
        if (property.interestPoint.parc){
            parc_imageView.visibility = View.GONE
            parc_textView.visibility = View.GONE
        }
        if (property.interestPoint.school){
            school_imageView.visibility = View.GONE
            school_textView.visibility = View.GONE
        }
        if (property.interestPoint.store){
            stores_imageView.visibility = View.GONE
            stores_textView.visibility = View.GONE
        }
        if (property.interestPoint.transport){
            public_transport_imageView.visibility = View.GONE
            public_transport_textView.visibility = View.GONE
        }
    }

    private fun getPicture(id: Long){
        this.mPropertyDetailViewModel.getPicture(id).observe(this, Observer { list ->
            list?.let { mAdapterRecycler.updateData(it) }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putAll(outState)
        }
        super.onSaveInstanceState(outState)
    }
}
