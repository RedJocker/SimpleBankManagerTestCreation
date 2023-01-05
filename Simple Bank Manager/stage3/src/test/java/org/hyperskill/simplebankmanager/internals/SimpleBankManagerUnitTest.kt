package org.hyperskill.simplebankmanager.internals

import android.app.Activity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.junit.Assert.assertEquals

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

    fun EditText.assertEditText(idString: String, expectedHint: String, expectedType: Int, typeString: String) {
        val actualHint = this.hint.toString()
        assertEquals("Wrong hint on $idString", expectedHint, actualHint)

        val actualInputType = this.inputType
        assertEquals("Wrong inputType on $idString should be $typeString", expectedType, actualInputType)
    }
}