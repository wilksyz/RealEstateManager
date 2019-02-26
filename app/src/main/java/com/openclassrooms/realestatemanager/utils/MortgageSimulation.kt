package com.openclassrooms.realestatemanager.utils

object MortgageSimulation {

        fun getMonthly(duration: Double, borrowedMoney: Int, rate: Double): Double{
            val monthDuration = getMonth(duration)
            return if (rate != 0.0){
                val rateNumberDecimal = rate / 100
                val firstPart = borrowedMoney * rateNumberDecimal / 12
                val secondPart = 1 - (Math.pow((1 + rateNumberDecimal / 12), -monthDuration))
                firstPart / secondPart
            }else {
                borrowedMoney / monthDuration
            }
        }

        private fun getMonth(duration: Double): Double{
            return duration * 12
        }

        fun getCostMortgage(duration: Double, borrowedMoney: Int, monthly: Double): Double{
            val monthDuration = getMonth(duration)
            return (monthly * monthDuration) - borrowedMoney
        }
}