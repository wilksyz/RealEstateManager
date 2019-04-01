package com.openclassrooms.realestatemanager.ui.property_details


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_form.recyclerView.PropertyGridRecyclerViewAdapter
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import com.openclassrooms.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.dialog_full_screen_picture.view.*
import kotlinx.android.synthetic.main.fragment_details_property.*
import kotlinx.android.synthetic.main.fragment_details_property.view.*
import java.text.DateFormat
import java.util.*

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

        viewOfLayout.sold_buttons.setOnClickListener {
            getSoldDate()
        }

        return viewOfLayout
    }

    override fun onStart() {
        super.onStart()
        this.getProperty(mPropertyId)
        this.getPicture(mPropertyId)
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

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getSoldDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, pYear, pMonth, pDay ->
            val formattedDay = if (pDay < 10) "0$pDay" else "$pDay"
            val formattedMonth = pMonth + 1
            val dateString = if (formattedMonth < 10) "$formattedDay/0$formattedMonth/$pYear" else "$formattedDay/$formattedMonth/$pYear"
            val df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE)
            val date: Date
            date = df.parse(dateString)
            this.checkDate(date)
        }, year, month, day)
        dialog.datePicker.maxDate = Date().time
        dialog.show()
    }

    //Check that the date of sale is after the date of placing on the market
    private fun checkDate(date: Date){
        if (date >= mProperty.dateOfSale ){
            updateProperty(date)
            this.updateUiPropertySold(true)
        }else{
            Toast.makeText(context, getString(R.string.the_date_of_sold_must_be_greater_than_the_date_of_sale), Toast.LENGTH_LONG).show()
        }
    }

    // Update an property
    private fun updateProperty(date: Date) {
        mProperty.dateSold = date
        mProperty.saleStatus = true
        this.mPropertyDetailViewModel.updateProperty(mProperty)
    }

    // get an property
    private fun getProperty(id: Long) {
        this.mPropertyDetailViewModel.getProperty(id).observe(this, Observer { property ->
            if (property != null) {
                mProperty = property
                this.updateUI(property)
                this.getInterestPoint(property)
                this.getStaticMap(property)
                this.updateUiPropertySold(property.saleStatus)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(property: Property){
        if (property.surface != 0) detail_property_surface_textView.text = property.surface.toString() else detail_property_surface_textView.text = getString(R.string.n_c)
        if (property.numberOfRooms.isNotEmpty()) detail_property_room_textView.text = property.numberOfRooms else detail_property_room_textView.text = getString(R.string.n_c)
        if (property.address.city.isNotEmpty()) detail_property_location_textView.text = property.address.number+" "+property.address.street+"\n"+property.address.postCode+"\n"+property.address.city else detail_property_location_textView.text = getString(R.string.n_c)
        if (property.descriptionProperty.isNotEmpty()) detail_property_description_textView.text = property.descriptionProperty else detail_property_description_textView.text = getString(R.string.no_description)
        detail_property_price_textView.text ="$ ${property.price}"
    }

    // Show or not the points of interest if they are available
    private fun getInterestPoint(property: Property){
        if (!property.interestPoint.doctor){
            doctor_imageView.visibility = View.GONE
            doctor_textView.visibility = View.GONE
        }else {
            doctor_imageView.visibility = View.VISIBLE
            doctor_textView.visibility = View.VISIBLE
        }
        if (!property.interestPoint.hobbies){
            hobbies_imageView.visibility = View.GONE
            hobbies_textView.visibility = View.GONE
        }else {
            hobbies_imageView.visibility = View.VISIBLE
            hobbies_textView.visibility = View.VISIBLE
        }
        if (!property.interestPoint.parc){
            parc_imageView.visibility = View.GONE
            parc_textView.visibility = View.GONE
        }else {
            parc_imageView.visibility = View.VISIBLE
            parc_textView.visibility = View.VISIBLE
        }
        if (!property.interestPoint.school){
            school_imageView.visibility = View.GONE
            school_textView.visibility = View.GONE
        }else {
            school_imageView.visibility = View.VISIBLE
            school_textView.visibility = View.VISIBLE
        }
        if (!property.interestPoint.store){
            stores_imageView.visibility = View.GONE
            stores_textView.visibility = View.GONE
        }else {
            stores_imageView.visibility = View.VISIBLE
            stores_textView.visibility = View.VISIBLE
        }
        if (!property.interestPoint.transport){
            public_transport_imageView.visibility = View.GONE
            public_transport_textView.visibility = View.GONE
        }else {
            public_transport_imageView.visibility = View.VISIBLE
            public_transport_textView.visibility = View.VISIBLE
        }
    }

    private fun getStaticMap(property: Property){
        Utils{
            if (it){
                val key = getString(R.string.google_maps_api)
                val url = "https://maps.googleapis.com/maps/api/staticmap?size=400x400&markers=color:blue%7C${property.address.number} ${property.address.street} ${property.address.postCode} ${property.address.city}&key=$key"
                activity?.let { it1 ->
                    Glide.with(it1)
                            .load(url)
                            .into(static_maps_imageView)
                }
            }else{
                static_maps_imageView.visibility = View.GONE
            }
        }
    }

    //Modify the view based on the state of the sale
    private fun updateUiPropertySold(saleStatus: Boolean){
        if (saleStatus){
            viewOfLayout.sold_buttons.visibility = View.GONE
            viewOfLayout.sold_property_detail_textview.visibility = View.VISIBLE
        }else{
            viewOfLayout.sold_buttons.visibility = View.VISIBLE
            viewOfLayout.sold_property_detail_textview.visibility = View.GONE
        }
    }

    private fun getPicture(id: Long){
        this.mPropertyDetailViewModel.getPicture(id).observe(this, Observer { list ->
            list?.let { mAdapterRecycler.updateData(it) }
        })
    }
}
