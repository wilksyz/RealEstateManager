package com.openclassrooms.realestatemanager.model.geocoding_api

data class Geometry(
        val location: Location,
        val location_type: String,
        val viewport: Viewport
)