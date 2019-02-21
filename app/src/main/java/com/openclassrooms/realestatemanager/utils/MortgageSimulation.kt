package com.openclassrooms.realestatemanager.utils

class MortgageSimulation {

    fun getMonthly(duration: Int, borrowedMoney: Int, rate: Float){
        val monthDuration = getMonth(duration)
        val d = 120.000 * 0.02 / 1-(1+ 0.02)
        //[C × t/12]÷[1−(1 + t/12)−n]
    }

    private fun getMonth(duration: Int): Int{
        return duration * 12
    }
}