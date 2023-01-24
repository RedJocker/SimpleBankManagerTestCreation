package org.hyperskill.simplebankmanager.internals.screen

import android.app.Activity
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.junit.Assert.assertEquals

class CalculateExchangeScreen<T : Activity>(private val test: SimpleBankManagerUnitTest<T>) {

    companion object {
        val expectedDropdownText = arrayListOf("eur", "gbp", "usd")
    }

    val calculateExchangeLabelFromTextView = with(test) {
        val idString = "calculateExchangeLabelFromTextView"
        val expectedText = "convert from"
        activity.findViewByString<TextView>(idString).apply {
            assertText(idString, expectedText)
        }
    }

    val calculateExchangeLabelToTextView = with(test) {
        val idString = "calculateExchangeLabelToTextView"
        val expectedText = "convert to"
        activity.findViewByString<TextView>(idString).apply {
            assertText(idString, expectedText)
        }
    }

    val calculateExchangeConvertFromSpinner = with(test) {
        val idString = "calculateExchangeFromSpinner"
        activity.findViewByString<Spinner>(idString).apply {
            assertSpinnerText(idString, expectedDropdownText)
        }
    }

    val calculateExchangeConvertToSpinner = with(test) {
        val idString = "calculateExchangeToSpinner"
        activity.findViewByString<Spinner>(idString).apply {
            assertSpinnerText(idString, expectedDropdownText)
        }
    }

    val calculateExchangeDisplayTextView = with(test) {
        val idString = "calculateExchangeDisplayTextView"
        activity.findViewByString<TextView>(idString)
    }

    val calculateExchangeAmountEditText = with(test) {
        val idString = "calculateExchangeAmountEditText"
        val expectedType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        val typeString = "numberDecimal"
        activity.findViewByString<EditText>(idString).apply {
            assertEditText(idString, "Enter amount", expectedType, typeString)
        }
    }

    val calculateExchangeButton = with(test) {
        val idString = "calculateExchangeButton"
        val expectedText = "calculate"
        activity.findViewByString<Button>(idString).apply {
            assertText(idString, expectedText)
        }
    }


    fun assertDisplayConvertedAmount(
        amountToConvert: String,
        convertFromText: String,
        convertToText: String,
        expectedConvertedAmount: String
    ) = with(test) {

        setSpinnerCurrencySelection(convertFromText,convertToText)
        calculateExchangeAmountEditText.append(amountToConvert)

        calculateExchangeButton.clickAndRun()

        calculateExchangeDisplayTextView.apply {
            val expectedText =
                "$amountToConvert $convertFromText" + " = " + "$expectedConvertedAmount $convertToText"
            val actualText = this.text.toString().lowercase()
            val messageDisplayError = "calculateExchangeDisplayTextView has wrong text " +
                    "on conversion from $convertFromText to $convertToText,"
            assertEquals(messageDisplayError, expectedText, actualText)
        }
    }

    fun checkForErrorMessages(
        isEmptyAmount: Boolean = false, isSameCurrencySelected: Boolean = false) = with(test) {

        if (isEmptyAmount) {
            calculateExchangeButton.clickAndRun()
            val expectedToastMessage = "Enter amount"
            assertLastToastMessageEquals(
                "Wrong Toast message for empty EditText at CalculateExchange",
                expectedToastMessage
            )
        }
        if (isSameCurrencySelected) {
            calculateExchangeConvertFromSpinner.setSelection(0)
            calculateExchangeConvertToSpinner.setSelection(0)
            calculateExchangeAmountEditText.setText("321")
            calculateExchangeButton.clickAndRun()
            val expectedToastMessage = "Cannot convert to same currency"
            assertLastToastMessageEquals(
                "Wrong Toast message for same currency selected at CalculateExchange",
                expectedToastMessage
            )
        }
    }

    fun setSpinnerCurrencySelection(convertFromText: String, convertToText: String) {

        val convertFrom = when (convertFromText.lowercase()) {
            "eur" -> 0
            "gbp" -> 1
            "usd" -> 2
            else -> throw Exception("Wrong currency selected or not found")
        }

        calculateExchangeConvertFromSpinner.setSelection(convertFrom)

        val convertTo = when (convertToText.lowercase()) {
            "eur" -> 0
            "gbp" -> 1
            "usd" -> 2
            else -> throw Exception("Wrong currency selected or not found")
        }
        calculateExchangeConvertToSpinner.setSelection(convertTo)
    }
}


