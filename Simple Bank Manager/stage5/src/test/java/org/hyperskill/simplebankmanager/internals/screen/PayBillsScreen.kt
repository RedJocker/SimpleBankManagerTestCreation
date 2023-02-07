package org.hyperskill.simplebankmanager.internals.screen

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.junit.Assert
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowAlertDialog
import org.robolectric.shadows.ShadowDialog
import org.robolectric.shadows.ShadowToast
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

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




    fun confirmInputCode(input: String) {
        payBillsCodeInputEditText.setText(input.toLowerCase())
        payBillsShowBillInfoButton.performClick()
        val payBillsDialog: AlertDialog = ShadowAlertDialog.getLatestAlertDialog()
        Assert.assertEquals("Bill info", payBillsDialog)
        }

    }



