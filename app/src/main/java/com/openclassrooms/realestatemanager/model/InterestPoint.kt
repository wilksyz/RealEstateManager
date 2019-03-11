package com.openclassrooms.realestatemanager.model

data class InterestPoint(var doctor: Boolean = false,
                         var school: Boolean = false,
                         var hobbies: Boolean = false,
                         var transport: Boolean = false,
                         var parc: Boolean = false,
                         var store: Boolean = false){

    companion object {

        //KEY
        const val DOCTOR = "doctor"
        const val SCHOOL = "school"
        const val HOBBIES = "hobbies"
        const val PUBLIC_TRANSPORT = "public transport"
        const val PARC = "parc"
        const val STORES = "stores"
    }
}