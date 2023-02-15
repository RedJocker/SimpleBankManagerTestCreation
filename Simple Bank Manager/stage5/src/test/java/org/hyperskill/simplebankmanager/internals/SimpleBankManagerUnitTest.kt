package org.hyperskill.simplebankmanager.internals

import android.app.Activity
import android.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.robolectric.Shadows.shadowOf

open class SimpleBankManagerUnitTest<T : Activity>(clazz: Class<T>) : AbstractUnitTest<T>(clazz) {

    fun Button.assertButtonText(idString: String, expectedText: String) {
        val actualText = this.text.toString().lowercase()
        assertEquals("Wrong text on $idString", expectedText, actualText)
    }

    fun EditText.assertHintEditText(idString: String, expectedHint: String, ignoreCase: Boolean = true) {
        val actualHint = if(ignoreCase) this.hint.toString().lowercase() else this.hint.toString()
        assertEquals("Wrong hint on $idString", expectedHint, actualHint)
    }
    fun TextView.assertText(idString: String, expectedText: String, ignoreCase: Boolean = true) {
        val actualText = if(ignoreCase) this.text.toString().lowercase() else this.text.toString()
        assertEquals("Wrong text on $idString", expectedText, actualText)
    }

    fun TextView.assertTextWithCustomErrorMessage(errorMessage: String, expectedText: String, ignoreCase: Boolean = true) {
        val actualText = if(ignoreCase) this.text.toString().lowercase() else this.text.toString()
        assertEquals(errorMessage, expectedText, actualText)
    }

    fun EditText.assertEditText(idString: String, expectedHint: String, expectedType: Int, typeString: String) {
        val actualHint = this.hint.toString()
        assertEquals("Wrong hint on $idString", expectedHint, actualHint)

        val actualInputType = this.inputType
        assertEquals("Wrong inputType on $idString should be $typeString", expectedType, actualInputType)
    }

    fun EditText.assertErrorText(errorMessage: String, expectedErrorText: String) {
        val actualErrorText = this.error?.toString()
        assertEquals(errorMessage, expectedErrorText, actualErrorText)
    }

    fun Spinner.assertSpinnerText(idString: String, expectedDropdownText: ArrayList<String>, ignoreCase: Boolean = true) {
        val items = ArrayList<String>()
        for (i in 0 until this.adapter.count) {
            items.add(this.adapter.getItem(i).toString())
        }
        val actualText = if(ignoreCase) items.toString().lowercase() else items.toString()
        assertEquals("Wrong text on $idString",  expectedDropdownText.toString(), actualText)
    }

    fun AlertDialog.assertDialogTitle(expectedTitle: String, ignoreCase: Boolean = false) {
        val shadowAlertDialog = shadowOf(this)
        val expectedTitleNorm = if(ignoreCase) expectedTitle.lowercase() else expectedTitle
        val actualTitle = shadowAlertDialog.title.toString().lowercase()
        val actualTitleNorm = if(ignoreCase) actualTitle.lowercase() else actualTitle

        assertEquals("Wrong AlertDialog title", expectedTitleNorm, actualTitleNorm)
    }
    fun AlertDialog.assertDialogMessage(expectedMessage: String, ignoreCase: Boolean = false) {
        val shadowAlertDialog = shadowOf(this)
        val expectedMessageNorm = if(ignoreCase) expectedMessage.lowercase() else expectedMessage
        val actualMessage = shadowAlertDialog.message.toString()
        val actualMessageNorm = if(ignoreCase) actualMessage.lowercase() else actualMessage

        assertEquals("Wrong AlertDialog message", expectedMessageNorm, actualMessageNorm)
    }
    fun AlertDialog.assertDialogVisibility(caseDescription: String, expectedVisible: Boolean) {
        val isDialogVisible = this.isShowing
        val messageError = "Dialog should %s be visible %s".format(
            if(expectedVisible) "" else "not",
            caseDescription
        )
        assertEquals(messageError,isDialogVisible, expectedVisible)
    }
}