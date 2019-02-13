package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.model.geocoding_api.GeocodingApi
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GeocodingApiService {

    @GET("geocode/json?")
    fun getLocation(@QueryMap location: Map<String, String>): Observable<GeocodingApi>

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}