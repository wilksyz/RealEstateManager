package com.openclassrooms.realestatemanager.ui.property_maps

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.GeocodingApiStream
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.model.geocoding_api.GeocodingApi
import com.openclassrooms.realestatemanager.ui.property_details.PropertyDetailActivity
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_property_maps.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.util.HashMap
import kotlin.collections.ArrayList

private const val KEY_CAMERA_POSITION = "camera_position"
private const val KEY_LOCATION = "location"
private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1918
private const val DEFAULT_ZOOM = 13
private const val LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
private const val PROPERTY_ID: String = "property id"
class PropertyMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private lateinit var mPropertyMapsViewModel: PropertyMapsViewModel
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mGoogleMap: GoogleMap
    private var mLastKnownLocation: Location? = null
    private var mCameraPosition: CameraPosition? = null
    private var mSavedInstanceState: Bundle? = null
    lateinit var disposable: Disposable
    private var mPropertyList: List<Property> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_maps)
        mSavedInstanceState = savedInstanceState
        this.configureViewModel()
        this.createMap(savedInstanceState)

    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this)
        this.mPropertyMapsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyMapsViewModel::class.java)
    }

    private fun createMap(savedInstanceState: Bundle?){
        property_maps_mapView.onCreate(savedInstanceState)
        property_maps_mapView.getMapAsync(this)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        if (mSavedInstanceState == null){
            this.checkPermissionAndLoadTheMap()
        }else{
            updateLocationUI()
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition))
        }
        getAllProperty()
    }

    private fun hasLocationPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        if (requestCode == RESULT_OK && requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION){
            finish()
            startActivity(intent)
        }
    }

    @AfterPermissionGranted(PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
    private fun checkPermissionAndLoadTheMap() {
        if (hasLocationPermissions()) {
            // Have permission, do the thing!
            this.getLastLocation()
            this.updateLocationUI()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.to_see_the_good_around_you),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationProviderClient.lastLocation
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful && task.result != null) {
                        mLastKnownLocation = task.result
                        mLastKnownLocation?.let { centerCameraOnLocation(it) }
                    } else {
                        Log.w("TAG", "getLastLocation:exception", task.exception)
                    }
                }
    }

    private fun centerCameraOnLocation(mLastKnownLocation: Location) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(mLastKnownLocation.latitude,
                        mLastKnownLocation.longitude), DEFAULT_ZOOM.toFloat()))
    }

    private fun updateLocationUI() {
        try {
            if (hasLocationPermissions()) {
                mGoogleMap.isMyLocationEnabled = true
                mGoogleMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mGoogleMap.isMyLocationEnabled = false
                mGoogleMap.uiSettings.isMyLocationButtonEnabled = false
            }
        } catch (e: SecurityException) {
            Log.e("Exception: ", e.message)
        }
    }

    private fun getAllProperty(){
        mPropertyMapsViewModel.getAllProperty().observe(this, Observer { propertyList ->
            if (propertyList != null) {
                mPropertyList = propertyList
                this.convertAddressToLocation(propertyList)
            }
        })
    }

    //@SuppressLint("CheckResult")
    private fun convertAddressToLocation(propertyList: List<Property>){
        var i = 0
        for (property in propertyList){
            val queryLocation: MutableMap<String, String> = HashMap()
            val address = "${property.address.number} ${property.address.street} ${property.address.postCode} ${property.address.city}"
            Log.e("Tag address", address)
            //queryLocation["key"] =
            //queryLocation["address"] = address
            queryLocation.put("address", address)
            queryLocation.put("key", getString(R.string.google_maps_api))
            disposable = GeocodingApiStream.getLocation(queryLocation).subscribeWith(object: DisposableObserver<GeocodingApi>() {

                override fun onNext(geocodingApi: GeocodingApi) {
                    val lat: Double = geocodingApi.results[0].geometry.location.lat
                    val lng: Double = geocodingApi.results[0].geometry.location.lng
                    Log.e("Tag maps", "${geocodingApi.status}")
                    Log.e("Tag maps", "$geocodingApi")
                    Log.e("Tag maps", "${geocodingApi.results}")
                    Log.e("Tag maps", "$lat, $lng, $i")
                    addMarker(geocodingApi, i)
                    i++
                }

                override fun onError(e: Throwable) {
                    Log.e("TAG","On Error",e)
                }

                override fun onComplete() {
                    Log.i("TAG","On Complete !!")
                }
            })
        }
    }

    private fun addMarker(geocodingApi: GeocodingApi, position: Int){
        val lat: Double = geocodingApi.results[0].geometry.location.lat
        val lng: Double = geocodingApi.results[0].geometry.location.lng
        val location = LatLng(lat, lng)
        val marker = mGoogleMap.addMarker(MarkerOptions()
                .position(location)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        marker.tag = position
        mGoogleMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val tag: Int = marker.tag as Int
        val intent = Intent(this, PropertyDetailActivity::class.java)
        intent.putExtra(PROPERTY_ID, mPropertyList[tag].mPropertyId)
        startActivity(intent)
        return false
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(KEY_CAMERA_POSITION, mGoogleMap.cameraPosition)
        outState?.putParcelable(KEY_LOCATION, mLastKnownLocation)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        mLastKnownLocation = savedInstanceState?.getParcelable(KEY_LOCATION)
        mCameraPosition = savedInstanceState?.getParcelable(KEY_CAMERA_POSITION)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        property_maps_mapView.onResume()
    }

    override fun onPause() {
        property_maps_mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        property_maps_mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        property_maps_mapView.onLowMemory()
        super.onLowMemory()
    }
}
