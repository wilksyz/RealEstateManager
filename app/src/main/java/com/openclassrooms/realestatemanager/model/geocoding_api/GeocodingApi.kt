package com.openclassrooms.realestatemanager.model.geocoding_api

data class GeocodingApi(
        val results: List<Result>,
        val status: String
)