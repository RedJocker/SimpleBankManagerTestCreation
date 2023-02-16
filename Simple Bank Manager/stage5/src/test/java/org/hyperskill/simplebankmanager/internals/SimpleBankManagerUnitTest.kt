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

    fun Button.assertButtonText(idString: String, expectedText: String, ignoreCase: Boolean = true) {
        assertTextEquals("Wrong text on $idString", expectedText, text, ignoreCase)
    }

    fun EditText.assertHintEditText(idString: String, expectedHint: String, ignoreCase: Boolean = true) {
        assertTextEquals("Wrong hint on $idString", expectedHint, this.hint, ignoreCase)
    }
    fun TextView.assertText(idString: String, expectedText: String, ignoreCase: Boolean = true) {
        assertTextEquals("Wrong text on $idString", expectedText, this.text, ignoreCase)
    }

    fun TextView.assertTextWithCustomErrorMessage(
        errorMessage: String, expectedText: String, ignoreCase: Boolean = true) {

        assertTextEquals(errorMessage, expectedText, this.text, ignoreCase)
    }

    fun EditText.assertEditText(
        idString: String,
        expectedHint: String,
        expectedType: Int,
        typeString: String,
        ignoreCase: Boolean = true) {

        this.assertHintEditText(idString, expectedHint, ignoreCase)
        val actualInputType = this.inputType
        assertEquals(
            "Wrong inputType on $idString should be $typeString",
            expectedType,
            actualInputType
        )
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
        val actualTitle = shadowAlertDialog.title

        assertTextEquals(
            "Wrong AlertDialog title", expectedTitle, actualTitle, ignoreCase
        )
    }
    fun AlertDialog.assertDialogMessage(expectedMessage: String, ignoreCase: Boolean = false) {
        val shadowAlertDialog = shadowOf(this)
        val actualMessage = shadowAlertDialog.message
        assertTextEquals("Wrong AlertDialog message", expectedMessage, actualMessage, ignoreCase)
    }
    fun AlertDialog.assertDialogVisibility(caseDescription: String, expectedVisible: Boolean) {
        val isDialogVisible = this.isShowing
        val messageError = "Dialog should %s be visible %s".format(
            if(expectedVisible) "" else "not",
            caseDescription
        )
        assertEquals(messageError,isDialogVisible, expectedVisible)
    }

    private fun String.normalizeCase(ignoreCase: Boolean): String {
        return if (ignoreCase) this.lowercase() else this
    }

    private fun CharSequence.normalizeCase(ignoreCase: Boolean): String {
        return this.toString().normalizeCase(ignoreCase)
    }

    private fun assertTextEquals(
        errorMessage: String,
        expectedText: CharSequence,
        actualText: CharSequence,
        ignoreCase: Boolean = true
    )  {
        val (expectedTextNorm, actualTextNorm) = listOf(expectedText, actualText)
            .map { it.normalizeCase(ignoreCase) }
        assertEquals(errorMessage, expectedTextNorm, actualTextNorm)
    }
}