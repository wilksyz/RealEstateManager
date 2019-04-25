package com.openclassrooms.realestatemanager.ui.property_mortgage

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object MortgageSimulation {

        fun getMonthly(duration: Double, borrowedMoney: Int, rate: Double): Double{
            val monthDuration = getMonth(duration)
            val decimalFormat = DecimalFormat("0.00", DecimalFormatSymbols(Locale.US))
            return if (rate != 0.0){
                val rateNumberDecimal = rate / 100
                val firstPart = borrowedMoney * rateNumberDecimal / 12
                val secondPart = 1 - (Math.pow((1 + rateNumberDecimal / 12), -monthDuration))
                decimalFormat.format(firstPart / secondPart).toDouble()
            }else {
                decimalFormat.format(borrowedMoney / monthDuration).toDouble()
            }
        }

        private fun getMonth(duration: Double): Double{
            return duration * 12
        }

        fun getCostMortgage(duration: Double, borrowedMoney: Int, monthly: Double): Double{
            val decimalFormat = DecimalFormat("0.00", DecimalFormatSymbols(Locale.US))
            val monthDuration = getMonth(duration)
            return decimalFormat.format((monthly * monthDuration) - borrowedMoney).toDouble()
        }
}