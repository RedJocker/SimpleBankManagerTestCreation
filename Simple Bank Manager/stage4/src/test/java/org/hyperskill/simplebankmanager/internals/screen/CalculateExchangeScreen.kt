package org.hyperskill.simplebankmanager.internals.screen

import android.app.Activity
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.junit.Assert

class CalculateExchangeScreen<T : Activity>(private val test: SimpleBankManagerUnitTest<T>) {

    val calculateExchangeConvertFromTextView = with(test) {
        val idString = "convertFromTextView"
        val expectedText = "convert from"
        activity.findViewByString<TextView>(idString).apply {
            assertText(idString, expectedText)
        }
    }

    val calculateExchangeConvertToTextView = with(test) {
        val idString = "convertToTextView"
        val expectedText = "convert to"
        activity.findViewByString<TextView>(idString).apply {
            assertText(idString, expectedText)
        }
    }

    val calculateExchangeDropdownConvertFromSpinner = with(test) {
        val idString = "spinnerConvertFrom"
        val expectedDropdownText = arrayListOf<String>("usd", "eur", "gbp")
        activity.findViewByString<Spinner>(idString).apply {
            assertSpinnerText(idString, expectedDropdownText)
        }
    }

    val calculateExchangeDropdownConvertToSpinner = with(test) {
        val idString = "spinnerConvertTo"
        val expectedDropdownText = arrayListOf<String>("usd", "eur", "gbp")
        activity.findViewByString<Spinner>(idString).apply {
            assertSpinnerText(idString, expectedDropdownText)
        }
    }

    val calculateExchangeEnterAmountEditText = with(test) {
        val idString = "inputFundsToConvertEditText"
        val expectedType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        val typeString = "numberDecimal"
        activity.findViewByString<EditText>(idString).apply {

            assertEditText(idString, "Enter amount", expectedType, typeString)
        }
    }

    val calculateExchangeCalculateButton = with(test) {
        val idString = "buttonConvertFunds"
        val expectedText = "calculate"
        activity.findViewByString<Button>(idString).apply {
            assertText(idString, expectedText)
        }
    }


    fun calculateExchangeShowConvertedAmount(
        amountToConvert: String,
        convertFromText: String,
        convertToText: String,
        expectedConvertedAmount: String
    ) = with(test) {
        setSpinnerCurrencySelection(convertFromText,convertToText)

        calculateExchangeEnterAmountEditText.append(amountToConvert)

        calculateExchangeCalculateButton.clickAndRun().also {
            val idString = "calculateExchangeShowConvertedAmountTextView"
            activity.findViewByString<TextView>(idString).apply {
                val expectedText =
                    "$amountToConvert $convertFromText" + " = " + "$expectedConvertedAmount $convertToText"
                val actualText = this.text.toString().lowercase()
                Assert.assertEquals(idString, expectedText, actualText)
            }
        }
    }

    fun checkForErrorMessages(isEmptyAmount: Boolean, isSameCurrencySelected: Boolean) =
        with(test) {

            if (isEmptyAmount) {
                calculateExchangeCalculateButton.clickAndRun()
                val expectedToastMessage = "Enter amount"
                assertLastToastMessageEquals(
                    "Wrong Toast message for empty EditText at CalculateExchange",
                    expectedToastMessage
                )
            }
            if (isSameCurrencySelected) {
                calculateExchangeDropdownConvertFromSpinner.setSelection(0)
                calculateExchangeDropdownConvertToSpinner.setSelection(0)
                calculateExchangeEnterAmountEditText.setText("321")
                calculateExchangeCalculateButton.clickAndRun()
                val expectedToastMessage = "Cannot convert to same currency"
                assertLastToastMessageEquals(
                    "Wrong Toast message for same currency selected at CalculateExchange",
                    expectedToastMessage
                )

            }

        }

    fun setSpinnerCurrencySelection(convertFromText: String, convertToText: String) {

            var convertFrom = when (convertFromText.lowercase()) {
                "usd" -> 0
                "eur" -> 1
                "gbp" -> 2
                else -> {
                    throw Exception("Wrong currency selected or not found")
                }
            }
            calculateExchangeDropdownConvertFromSpinner.setSelection(convertFrom)

            var convertTo = when (convertToText.lowercase()) {
                "usd" -> 0
                "eur" -> 1
                "gbp" -> 2
                else -> {
                    throw Exception("Wrong currency selected or not found")
                }
            }
            calculateExchangeDropdownConvertToSpinner.setSelection(convertTo)

    }
}


