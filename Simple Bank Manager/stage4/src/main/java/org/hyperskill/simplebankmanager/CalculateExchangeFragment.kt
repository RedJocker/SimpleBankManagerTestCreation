package org.hyperskill.simplebankmanager
import android.media.MediaDrm.LogMessage
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import org.hyperskill.simplebankmanager.databinding.FragmentCalculateExchangeBinding
import java.math.BigDecimal


class CalculateExchangeFragment : Fragment() {

    lateinit var fragmentCalculateExchangeFragment: FragmentCalculateExchangeBinding
    var balanceSetter: BalanceSetter? = null

    var spinnerConvertFrom: Spinner? = null
    var spinnerConvertTo: Spinner? = null
    var currenciesArray: Array<String> = arrayOf<String>()
    var fundsToConvertEt: EditText? = null
    var buttonConvertFundsView: Button? = null
    var convertedAmount = 0.0
    private val balance: Double? = null
    var fundsToConvert = 0.0
    var showConvertedAmountTextView : TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        fragmentCalculateExchangeFragment = FragmentCalculateExchangeBinding.inflate(layoutInflater, container, false)

        spinnerConvertFrom = fragmentCalculateExchangeFragment.spinnerConvertFrom
        spinnerConvertTo = fragmentCalculateExchangeFragment.spinnerConvertTo
        fundsToConvertEt = fragmentCalculateExchangeFragment.inputFundsToConvertEditText
        buttonConvertFundsView = fragmentCalculateExchangeFragment.buttonConvertFunds
        showConvertedAmountTextView = fragmentCalculateExchangeFragment.calculateExchangeShowConvertedAmountTextView
        setSpinner()

        buttonConvertFundsView!!.setOnClickListener {
            convert()
        }

        return fragmentCalculateExchangeFragment.root
    }


    private fun setSpinner() {
        currenciesArray = arrayOf(
            "USD", "EUR", "GBP"
        )
        val sadapter = ArrayAdapter(
            activity!!,
            android.R.layout.simple_spinner_item, currenciesArray
        )

        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerConvertFrom!!.adapter = sadapter
        spinnerConvertTo!!.adapter = sadapter
    }

    private fun convert() {
        val convertFrom = currenciesArray[spinnerConvertFrom!!.selectedItemPosition]
        val convertTo = currenciesArray[spinnerConvertTo!!.selectedItemPosition]
        if (convertFrom == "USD") {
            when (convertTo) {
                "EUR" -> convertedAmount = fundsToConvertEt!!.text.toString().toDouble() * 1.00
                "GBP" -> convertedAmount = fundsToConvertEt!!.text.toString().toDouble() * 0.877
            }
        } else if (convertFrom == "GBP") {
            when (convertTo) {
                "EUR" -> convertedAmount = fundsToConvertEt!!.text.toString().toDouble() * 1.14
                "USD" -> convertedAmount = fundsToConvertEt!!.text.toString().toDouble() * 1.14
            }
        } else if (convertFrom == "EUR") {
            when (convertTo) {
                "GBP" -> convertedAmount = fundsToConvertEt!!.text.toString().toDouble() * 0.87
                "USD" -> convertedAmount = fundsToConvertEt!!.text.toString().toDouble() * 1.00
            }
        }

        if (fundsToConvertEt!!.text.toString().isBlank() ) {
            Toast.makeText(context, "Enter amount", Toast.LENGTH_SHORT).show()
        } else  if (convertFrom == convertTo) {
           return Toast.makeText(context, "Cannot convert to same currency",Toast.LENGTH_SHORT).show()
        } else {
            fundsToConvert = fundsToConvertEt!!.text.toString().toDouble()

            val convertedAmountFormatted = String.format("%.2f",convertedAmount)
            showConvertedAmountTextView?.setText("$fundsToConvert $convertFrom = ${convertedAmountFormatted} $convertTo")
                Log.d("amount",showConvertedAmountTextView?.text.toString())

            }
        }
    }
