package com.openclassrooms.realestatemanager.ui.property_list


import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build.ID
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.*
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailFragment
import com.openclassrooms.realestatemanager.ui.property_list.recycler_view.PropertyRecyclerViewAdapter
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import com.openclassrooms.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.activity_list_property.*
import kotlinx.android.synthetic.main.fragment_list_property.view.*
import android.R.attr.y
import android.R.attr.x
import android.content.Intent
import android.graphics.Point
import android.view.Display
import android.util.DisplayMetrics
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailActivity


/**
 * A simple [Fragment] subclass.
 *
 */
private const val PROPERTY_ID: String = "property id"
class PropertyListFragment : Fragment() {

    private lateinit var mPropertyListViewModel: PropertyListViewModel
    private lateinit var mAdapter: PropertyRecyclerViewAdapter
    private lateinit var viewOfLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_list_property, container, false)
        this.configureRecyclerView()
        this.configureViewModel()
        this.configureClickRecyclerView()
        this.getAllProperty()

        return viewOfLayout
    }

    private fun configureRecyclerView(){
        this.mAdapter = PropertyRecyclerViewAdapter(Glide.with(this))
        viewOfLayout.property_recyclerView_container.adapter = this.mAdapter
        viewOfLayout.property_recyclerView_container.layoutManager = LinearLayoutManager(this.context)
    }

    private fun configureClickRecyclerView(){
        ItemClickSupport.addTo(viewOfLayout.property_recyclerView_container, R.layout.item_list_property)
                .setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
                    override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                        //Toast.makeText(context,"$position", Toast.LENGTH_SHORT).show()
                        val property = mAdapter.getProperty(position)
                        checkDetailsFragment(property)
                    }
                })
    }

    private fun checkDetailsFragment(property: Property){
        var detailsPropertyFragment = activity?.supportFragmentManager?.findFragmentById(R.id.details_of_the_property_container)
        val displayMetrics = context?.resources?.displayMetrics
        val dpWidth = displayMetrics?.widthPixels?.div(displayMetrics.density)

        if (dpWidth != null) {
            if (dpWidth > 600){
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                detailsPropertyFragment = PropertyDetailFragment()
                val args = Bundle()
                args.putLong(PROPERTY_ID, property.mPropertyId)
                detailsPropertyFragment.setArguments(args)
                fragmentTransaction?.replace(R.id.details_of_the_property_container, detailsPropertyFragment)
                fragmentTransaction?.commit()
            }else{
                val intent = Intent(activity, PropertyDetailActivity::class.java)
                intent.putExtra(PROPERTY_ID, property.mPropertyId)
                startActivity(intent)
            }
        }
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this.context!!)
        this.mPropertyListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyListViewModel::class.java)
    }

    // Get all properties in database
    private fun getAllProperty() {
        val pictureList: MutableList<Picture?> = ArrayList()
        this.mPropertyListViewModel.getAllProperty().observe(this, Observer{list ->
            val propertyList = list?.iterator()
            if (propertyList != null) {
                for (property in propertyList){
                    this.mPropertyListViewModel.getPicture(property.mPropertyId).observe(this, Observer {l ->
                        if (l?.size == 0){
                            pictureList.add(null)
                        }else{
                            l?.let { pictureList.add(it[0]) }
                        }
                        this.mAdapter.updateData(list, pictureList)
                    })
                }
            }
        } )
    }

    private fun getProperty(id: Long){
        this.mPropertyListViewModel.getProperty(id)
    }

    // Update an property
    private fun updateProperty(property: Property) {
        this.mPropertyListViewModel.updateItem(property)
    }
}
