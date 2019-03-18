package com.openclassrooms.realestatemanager.model

import android.arch.persistence.room.Ignore

data class Address(var number: String,
                   var street: String,
                   var postCode: String,
                   var city: String){

    @Ignore constructor() : this(number = "",
            street = "",
            postCode = "",
            city = "")

    companion object {
        //KEY
        const val NUMBER = "number"
        const val STREET = "street"
        const val POST_CODE = "post code"
        const val CITY = "city"
    }
}