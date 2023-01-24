package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import org.hyperskill.simplebankmanager.Extensions.showToast
import org.hyperskill.simplebankmanager.databinding.FragmentCalculateExchangeBinding


class CalculateExchangeFragment : Fragment() {

    companion object {
        val currenciesArray = arrayOf("EUR", "GBP", "USD")
    }

    private lateinit var binding: FragmentCalculateExchangeBinding
    private lateinit var spinnerAdapter : ArrayAdapter<String>
    private lateinit var convertFromSpinner: Spinner
    private lateinit var convertToSpinner: Spinner
    private lateinit var fundsToConvertEt: EditText
    private lateinit var buttonConvertFundsView: Button
    private lateinit var showConvertedAmountTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculateExchangeBinding
            .inflate(layoutInflater, container, false)

        binding.also {
            convertFromSpinner = it.calculateExchangeFromSpinner
            convertToSpinner = it.calculateExchangeToSpinner
            fundsToConvertEt = it.calculateExchangeEditText
            buttonConvertFundsView = it.calculateExchangeButton
            showConvertedAmountTextView = it.calculateExchangeDisplayTextView
        }

        buttonConvertFundsView.setOnClickListener {
            convert()
        }

        spinnerAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            currenciesArray
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        setSpinner()

        return binding.root
    }


    private fun setSpinner() {

        convertFromSpinner.adapter = spinnerAdapter
        convertToSpinner.adapter = spinnerAdapter
        convertFromSpinner.setSelection(0, true)

        convertToSpinner.setSelection(1, true)

        convertFromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (convertFromSpinner.selectedItem == convertToSpinner.selectedItem) {
                    showToast("Cannot convert to same currency")
                    when (convertFromSpinner.selectedItem) {
                        "USD" -> convertToSpinner.setSelection(0)
                        "EUR" -> convertToSpinner.setSelection(1)
                        else -> convertToSpinner.setSelection(2)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        convertToSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (convertFromSpinner.selectedItem == convertToSpinner.selectedItem) {
                    showToast("Cannot convert to same currency")
                    when (convertFromSpinner.selectedItem) {
                        "USD" -> convertToSpinner.setSelection(0)
                        "EUR" -> convertToSpinner.setSelection(1)
                        else -> convertToSpinner.setSelection(2)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }


    private fun convert() {
        val fundsToConvertEtValue = fundsToConvertEt.text.toString().trim()
        if (fundsToConvertEtValue.isBlank()) {
            showToast("Enter amount")
            return
        }


        val convertFrom = currenciesArray[convertFromSpinner.selectedItemPosition]
        val convertTo = currenciesArray[convertToSpinner.selectedItemPosition]
        val convertedAmount = when (convertFrom) {
            "USD" -> {
                when (convertTo) {
                    "EUR" -> fundsToConvertEt.text.toString().toDouble() * 1.00
                    "GBP" -> fundsToConvertEt.text.toString().toDouble() * 0.877
                    else -> 0.0
                }
            }
            "GBP" -> {
                when (convertTo) {
                    "EUR" -> fundsToConvertEt.text.toString().toDouble() * 1.14
                    "USD" -> fundsToConvertEt.text.toString().toDouble() * 1.14
                    else -> 0.0
                }
            }
            "EUR" -> {
                when (convertTo) {
                    "GBP" -> fundsToConvertEt.text.toString().toDouble() * 0.87
                    "USD" -> fundsToConvertEt.text.toString().toDouble() * 1.00
                    else -> 0.0
                }
            }
            else -> {
                0.0
            }
        }


        val fundsToConvert = fundsToConvertEt.text.toString().toDouble()

        val convertedAmountFormatted = String.format("%.2f", convertedAmount)
        showConvertedAmountTextView.text = "$fundsToConvert $convertFrom = $convertedAmountFormatted $convertTo"
        Log.d("amount", showConvertedAmountTextView.text.toString())
    }
}
