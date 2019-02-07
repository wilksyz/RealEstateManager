package com.openclassrooms.realestatemanager.ui.property_details


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.arch.lifecycle.*
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_create.PropertyGridRecyclerViewAdapter
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import com.openclassrooms.realestatemanager.utils.Utils
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_full_screen_picture.view.*
import kotlinx.android.synthetic.main.fragment_details_property.*
import kotlinx.android.synthetic.main.fragment_details_property.view.*
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.time.Month
import java.time.Year

/**
 * A simple [Fragment] subclass.
 *
 */
private const val PROPERTY_ID: String = "property id"
class PropertyDetailFragment : Fragment(), DatePickerDialog.OnDateSetListener {

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
        sold_button.setOnClickListener {
            this.getSoldDate()
        }

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

    private fun getSoldDate(){

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            property?.let { getInterestPoint(it) }
            property?.let { getStaticMap(it) }
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

    private fun getStaticMap(property: Property){
        Utils{
            Toast.makeText(context,"$it", Toast.LENGTH_SHORT).show()
            if (it){
                val url = "https://maps.googleapis.com/maps/api/staticmap?size=400x400&markers=color:blue%7C${property.address.number}${property.address.street}${property.address.postCode}${property.address.city}&key="
                Log.e("TAG", url)
                activity?.let { it1 ->
                    Glide.with(it1)
                            .load(url)
                            .into(static_maps_imageView)
                }
            }
        }
    }

    private fun getPicture(id: Long){
        this.mPropertyDetailViewModel.getPicture(id).observe(this, Observer { list ->
            list?.let { mAdapterRecycler.updateData(it) }
        })
    }
/*
override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putAll(outState)
        }
        super.onSaveInstanceState(outState)
    }
 */

}
