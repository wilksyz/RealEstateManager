package com.openclassrooms.realestatemanager.ui.property_maps

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_property_maps.*
import android.annotation.SuppressLint
import android.location.Location
import android.util.Log
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

private const val KEY_CAMERA_POSITION = "camera_position"
private const val KEY_LOCATION = "location"
private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1918
private const val DEFAULT_ZOOM = 18
private const val LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
class PropertyMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mGoogleMap: GoogleMap
    private var mLastKnownLocation: Location? = null
    private var mCameraPosition: CameraPosition? = null
    private var mSavedInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_maps)
        mSavedInstanceState = savedInstanceState
        createMap(savedInstanceState)

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
    }

    private fun hasLocationPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, LOCATION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
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
                        Log.e("TAG","last location")
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

    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
