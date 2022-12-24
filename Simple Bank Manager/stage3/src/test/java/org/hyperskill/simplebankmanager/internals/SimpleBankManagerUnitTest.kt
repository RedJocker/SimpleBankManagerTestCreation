package org.hyperskill.simplebankmanager.internals

import android.app.Activity
import android.widget.Button
import org.junit.Assert

open class SimpleBankManagerUnitTest<T : Activity>(clazz: Class<T>) : AbstractUnitTest<T>(clazz) {

    fun Button.assertButtonText(idString: String, expectedText: String) {
        val button = activity.findViewByString<Button>(idString)
        val actualText = button.text.toString().lowercase()
        Assert.assertEquals("$idString has wrong text", expectedText, actualText)
    }
}