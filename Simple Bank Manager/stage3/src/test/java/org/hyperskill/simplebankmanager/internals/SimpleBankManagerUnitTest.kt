package org.hyperskill.simplebankmanager.internals

import android.app.Activity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.junit.Assert
import org.robolectric.shadows.ShadowToast

open class SimpleBankManagerUnitTest<T : Activity>(clazz: Class<T>) : AbstractUnitTest<T>(clazz) {

    fun Button.assertButtonText(idString: String, expectedText: String) {
        val button = activity.findViewByString<Button>(idString)
        val actualText = button.text.toString().lowercase()
        Assert.assertEquals("$idString has wrong text", expectedText, actualText)
    }

    fun Button.assertGetLatestToastText(buttonId: String, message: String, expectedToast: String) {
        val actualToast = ShadowToast.getTextOfLatestToast().toString()
        Assert.assertEquals("$buttonId $message", expectedToast, actualToast)

    }

    fun EditText.assertHintEditText(idString: String, expectedHint: String) {
        val editTextId = activity.findViewByString<EditText>(idString)
        val actualHint = editTextId.hint.toString().lowercase()
        Assert.assertEquals("$idString has wrong hint", expectedHint, actualHint)

    }
    fun TextView.textViewAssertText(idString: String, expectedText: String) {
        val textViewId = activity.findViewByString<TextView>(idString)
        val actualText = textViewId.text.toString().lowercase()
        Assert.assertEquals("$idString has wrong text", expectedText, actualText)
    }

    fun TextView.assertHintTextView(idString: String, expectedHint: String) {
        val textViewId = activity.findViewByString<TextView>(idString)
        val actualHint = textViewId.hint.toString().lowercase()
        Assert.assertEquals("$textViewId has wrong hint", expectedHint, actualHint)

    }
}