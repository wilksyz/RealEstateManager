package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.model.geocoding_api.GeocodingApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GeocodingApiStream {

    companion object {
        fun getLocation(address: Map<String, String>): Observable<GeocodingApi> {
            val geocodingApiService: GeocodingApiService = GeocodingApiService.retrofit.create(GeocodingApiService::class.java)
            return geocodingApiService.getLocation(address).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(30, TimeUnit.SECONDS)
        }
    }

}