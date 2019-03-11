package com.openclassrooms.realestatemanager.model

data class Address(var number: String = "",
                   var street: String = "",
                   var postCode: String = "",
                   var city: String = ""){

    companion object {
        //KEY
        const val NUMBER = "number"
        const val STREET = "street"
        const val POST_CODE = "post code"
        const val CITY = "city"
    }
}