package org.hyperskill.simplebankmanager.internals.screen

import android.app.Activity
import android.app.AlertDialog
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.robolectric.shadows.ShadowToast


class PayBillsScreen<T : Activity>(private val test: SimpleBankManagerUnitTest<T>) {
    val payBillsCodeInputEditText = with(test) {
        val idString = "payBillsCodeInputEditText"
        val expectedHint = "enter code"
        val expectedType = InputType.TYPE_CLASS_TEXT
        val typeString = "text"
        activity.findViewByString<EditText>(idString).apply {
            assertEditText(idString, expectedHint, expectedType, typeString)
        }
    }
    val payBillsShowBillInfoButton: Button = with(test) {
        val idString = "payBillsShowBillInfoButton"
        val expectedText = "show bill info"
        activity.findViewByString<Button>(idString).apply {
            assertButtonText(idString, expectedText)
        }
    }

    fun inputBillCodeAndClickShowBillInfoButton(
        billCode: String,
        expectedDialogTitle: String,
        expectedDialogMessage: String): AlertDialog = with(test) {

        payBillsCodeInputEditText.setText(billCode)
        payBillsShowBillInfoButton.clickAndRun()

        return getLatestDialog().apply {
            assertDialogVisibility(
            caseDescription = "After clicking payBillsShowBillInfoButton",
            expectedVisible = true
            )
            assertDialogTitle(expectedDialogTitle, ignoreCase = true)
            assertDialogMessage(expectedDialogMessage, ignoreCase = true)
        }
    }

    fun AlertDialog.acceptBillPayment(billName: String) = with(test) {

        val button = getButton(AlertDialog.BUTTON_POSITIVE)
            ?: throw AssertionError("Expected positive button on AlertDialog")
        button.clickAndRun()

        assertDialogVisibility(
            caseDescription = "after clicking OK on dialog to accept bill payment",
            expectedVisible = false
        )

        assertLastToastMessageEquals(
            "Wrong Toast message for successful bill payment",
            "Payment for bill $billName, was successful"
        )
        ShadowToast.reset()
    }
    fun AlertDialog.declineBillPayment() = with(test) {

        val button = getButton(AlertDialog.BUTTON_NEGATIVE) ?: throw AssertionError(
            "Expected negative button on AlertDialog"
        )
        button.clickAndRun()
        assertDialogVisibility(
            caseDescription = "after clicking CANCEL on dialog to accept bill payment",
            expectedVisible = false
        )
    }
}





