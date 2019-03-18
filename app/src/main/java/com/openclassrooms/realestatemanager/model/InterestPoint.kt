package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Ignore

data class InterestPoint(var doctor: Boolean,
                         var school: Boolean,
                         var hobbies: Boolean,
                         var transport: Boolean,
                         var parc: Boolean,
                         var store: Boolean){

    @Ignore constructor() : this(doctor = false,
            school = false,
            hobbies = false,
            transport = false,
            parc = false,
            store = false)

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