package com.openclassrooms.realestatemanager.ui.property_mortgage


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.MortgageSimulation
import kotlinx.android.synthetic.main.fragment_property_mortgage.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
class PropertyMortgageFragment : Fragment() {

    private lateinit var viewOfLayout: View
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater.inflate(R.layout.fragment_property_mortgage, container, false)
        this.configureSpinner()

        viewOfLayout.simulate_mortgage_button.setOnClickListener {
            this.getSettingsForSimulate()
            if (mCheckPriceAndContribution) updateUI(MortgageSimulation.getMonthly(mDuration, mMoneyBorrowed, mRate))
        }
        return viewOfLayout
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun configureSpinner(){
        val durationMortgageSimulation = ArrayAdapter.createFromResource(context, R.array.duration_mortgage_simulation, android.R.layout.simple_spinner_item)
        durationMortgageSimulation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewOfLayout.duration_mortgage_spinner.adapter = durationMortgageSimulation
        viewOfLayout.rate_mortgage_inputext.text = SpannableStringBuilder(rateBoard[0].toString())
        viewOfLayout.duration_mortgage_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewOfLayout.rate_mortgage_inputext.text = SpannableStringBuilder(rateBoard[position].toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getSettingsForSimulate(){
        mPropertyPrice = if (viewOfLayout.price_mortgage_edit_text.text.toString().isNotEmpty()) Integer.parseInt(viewOfLayout.price_mortgage_edit_text.text.toString()) else 0
        mContribution = if (viewOfLayout.bring_mortgage_editext.text.toString().isNotEmpty()) Integer.parseInt(viewOfLayout.bring_mortgage_editext.text.toString()) else 0
        mRate = if (viewOfLayout.rate_mortgage_inputext.text.toString().isNotEmpty()) viewOfLayout.rate_mortgage_inputext.text.toString().toDouble() else 0.0
        mDuration = (viewOfLayout.duration_mortgage_spinner.selectedItemPosition + 2).toDouble()

        mCheckPriceAndContribution = this.getTheAmountBorrowed(mContribution, mPropertyPrice)
    }

    private fun getTheAmountBorrowed(contribution: Int, price: Int): Boolean{
        return when {
            price > contribution -> {
                mMoneyBorrowed = price - contribution
                viewOfLayout.price_mortgage_textInputLayout.error = null
                viewOfLayout.provision_mortgage_textInputLayout.error = null
                true
            }
            price == 0 -> {
                viewOfLayout.price_mortgage_textInputLayout.error = getString(R.string.price_must_not_be_zero)
                false
            }
            else -> {
                viewOfLayout.provision_mortgage_textInputLayout.error = getString(R.string.the_contribution_must_not_exceed_the_price_of_the_property)
                false
            }
        }
    }

    private fun updateUI(monthly: Double){
        val moneyBorrowedString = "Borrowed money: $ $mMoneyBorrowed"
        viewOfLayout.money_mortgage_textView.text = moneyBorrowedString
        val rateString = "Interest rate: $mRate%"
        viewOfLayout.rate_mortgage_textview.text = rateString
        val monthlyString = "Your monthly: $ $monthly"
        viewOfLayout.monthly_mortgage_textView.text = monthlyString
        val costMortgage = MortgageSimulation.getCostMortgage(mDuration, mMoneyBorrowed, monthly)
        val costMortgageString = "Cost of the mortgage: $ $costMortgage"
        viewOfLayout.cost_mortgage_textview.text = costMortgageString
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putAll(outState)
        }
        super.onSaveInstanceState(outState)
    }
}
