package com.openclassrooms.realestatemanager.ui.property_mortgage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.MortgageSimulation
import kotlinx.android.synthetic.main.activity_property_mortgage.*


private const val PROPERTY_PRICE: String = "property price"

class PropertyMortgageActivity : AppCompatActivity() {

    private var mPropertyPrice: Int = 0
    private var mContribution: Int = 0
    private var mRate: Double = 0.0
    private var mDuration: Double = 0.0
    private var mMoneyBorrowed: Int = 0
    private var mCheckPriceAndContribution: Boolean = false
    val rateBoard: DoubleArray = doubleArrayOf(0.92,0.92,0.92,0.92,0.92,0.92,0.92,0.92,
            1.1,1.1,
            1.25,1.25,1.25,
            1.36,1.36,1.36,1.36,1.36,
            1.56,1.56,1.56,1.56,1.56,
            1.79,1.79,1.79,1.79,1.79,
            2.21)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_mortgage)

        this.getPrice()
        this.configureSpinner()

        simulate_mortgage_button.setOnClickListener {
            this.getSettingsForSimulate()
            if (mCheckPriceAndContribution) updateUI(MortgageSimulation.getMonthly(mDuration, mMoneyBorrowed, mRate))
        }
    }

    private fun getPrice(){
        mPropertyPrice = intent.getIntExtra(PROPERTY_PRICE, 0)
        val priceString = "$ $mPropertyPrice"
        price_mortgage_textview.text = priceString
    }

    private fun configureSpinner(){
        val durationMortgageSimulation = ArrayAdapter.createFromResource(this, R.array.duration_mortgage_simulation, android.R.layout.simple_spinner_item)
        durationMortgageSimulation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        duration_mortgage_spinner.adapter = durationMortgageSimulation
        rate_mortgage_inputext.text = SpannableStringBuilder(rateBoard[0].toString())
        duration_mortgage_spinner.onItemSelectedListener = object : OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                rate_mortgage_inputext.text = SpannableStringBuilder(rateBoard[position].toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getSettingsForSimulate(){
        mContribution = if (bring_mortgage_editext.text.toString().isNotEmpty()) Integer.parseInt(bring_mortgage_editext.text.toString()) else 0
        mRate = if (rate_mortgage_inputext.text.toString().isNotEmpty()) rate_mortgage_inputext.text.toString().toDouble() else 0.0
        mDuration = (duration_mortgage_spinner.selectedItemPosition + 2).toDouble()

        mCheckPriceAndContribution = this.getTheAmountBorrowed(mContribution, mPropertyPrice)
    }

    private fun getTheAmountBorrowed(contribution: Int, price: Int): Boolean{
        return if (price > contribution){
            mMoneyBorrowed = price - contribution
            true
        }else {
            Toast.makeText(this, "The contribution must not exceed the price of the property", Toast.LENGTH_LONG).show()
            false
        }
    }

    private fun updateUI(monthly: Double){
        val moneyBorrowedString = "Borrowed money: $ $mMoneyBorrowed"
        money_mortgage_textView.text = moneyBorrowedString
        val rateString = "Interest rate: $mRate%"
        rate_mortgage_textview.text = rateString
        val monthlyString = "Your monthly: $ $monthly"
        monthly_mortgage_textView.text = monthlyString
        val costMortgage = MortgageSimulation.getCostMortgage(mDuration, mMoneyBorrowed, monthly)
        val costMortgageString = "Cost of the mortgage: $ $costMortgage"
        cost_mortgage_textview.text = costMortgageString
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            putAll(outState)
        }
        super.onSaveInstanceState(outState)
    }
}
