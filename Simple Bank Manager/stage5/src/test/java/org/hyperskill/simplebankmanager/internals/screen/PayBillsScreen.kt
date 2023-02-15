package org.hyperskill.simplebankmanager.internals.screen

import android.app.Activity
import android.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.robolectric.shadows.ShadowToast


class PayBillsScreen<T : Activity>(private val test: SimpleBankManagerUnitTest<T>) {
    val payBillsCodeInputEditText = with(test) {
        val idString = "payBillsCodeInputEditText"
        val expectedHint = "enter code"
        activity.findViewByString<EditText>(idString).apply {
            assertHintEditText(idString, expectedHint, true)
        }
    }
    val payBillsShowBillInfoButton: Button = with(test) {
        val idString = "payBillsShowBillInfoButton"
        val expectedText = "show bill info"
        activity.findViewByString<Button>(idString).apply {
            assertButtonText(idString, expectedText)
        }
    }

    fun confirmInputCode(input: String, dialogTitle: String, dialogMessage: String) = with(test) {
        payBillsCodeInputEditText.setText(input)
        payBillsShowBillInfoButton.clickAndRun().apply {
            val dialog = getLatestDialog()
            dialog.assertShadowDialogTitle(dialogTitle, ignoreCase = true)
            dialog.assertShadowDialogMessage(dialogMessage, ignoreCase = true)
        }
    }

    fun acceptBillPayment(billName: String) = with(test) {
        val dialog = getLatestDialog()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).clickAndRun()
        dialog.assertShadowDialogVisible(
            caseDescription = "after clicking OK on dialog to accept bill payment",
            expectedVisible = false
        )

        assertLastToastMessageEquals(
            "Wrong Toast message for successful bill payment",
            "Payment for bill $billName, was successful"
        )
        ShadowToast.reset()
    }
    fun declineBillPayment(billName: String) = with(test) {
        val dialog = getLatestDialog()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).clickAndRun()
        dialog.assertShadowDialogVisible(
            caseDescription = "after clicking CANCEL on dialog to accept bill payment",
            expectedVisible = false
        )
    }
}





