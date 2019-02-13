package com.openclassrooms.realestatemanager.model.geocoding_api

data class AddressComponent(
        val long_name: String,
        val short_name: String,
        val types: List<String>
)