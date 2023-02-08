package org.hyperskill.simplebankmanager.internals.screen

import android.app.Activity
import android.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowAlertDialog
import java.util.*


class PayBillsScreen<T : Activity>(private val test: SimpleBankManagerUnitTest<T>) {
    val payBillsCodeInputEditText = with(test) {
        val idString = "codeInputEditText"
        val expectedHint = "enter code"
        activity.findViewByString<EditText>(idString).apply {
            assertHintEditText(idString, expectedHint, true)
        }
    }
    val payBillsShowBillInfoButton: Button = with(test) {
        val idString = "showBillInfoButton"
        val expectedText = "show bill info"
        activity.findViewByString<Button>(idString).apply {
            assertButtonText(idString, expectedText)
        }
    }

    fun confirmInputCode(input: String, dialogTitle: String, dialogMessage: String) = with(test) {
        payBillsCodeInputEditText.setText(input)
        payBillsShowBillInfoButton.clickAndRun().apply {
            val dialog = getLatestDialog()
            val shadowDialog: ShadowAlertDialog = shadowOf(dialog)
            shadowDialog.assertShadowDialogTitle(dialogTitle, lowerCase = true)
            shadowDialog.assertShadowDialogMessage(dialogMessage, lowerCase = true)
        }
    }

    fun acceptBillPayment(billName: String) = with(test) {
        val dialog = getLatestDialog()
        val shadowDialog: ShadowAlertDialog = shadowOf(dialog)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).clickAndRun()
        shadowDialog.assertShadowDialogVisible(dialog.isShowing, expectedVisible = false)

        assertLastToastMessageEquals(
            "Wrong Toast message for successful bill payment",
            "Payment for bill $billName, was successful"
        )
    }
    fun declineBillPayment(billName: String) = with(test) {
        val dialog = getLatestDialog()
        val shadowDialog: ShadowAlertDialog = shadowOf(dialog)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).clickAndRun()
        shadowDialog.assertShadowDialogVisible(dialog.isShowing, expectedVisible = false)

    }

}





