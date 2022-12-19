package org.hyperskill.simplebankmanager.internals.screen


import android.app.Activity
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.robolectric.shadows.ShadowToast

class LoginScreen<T: Activity>(private val test: SimpleBankManagerUnitTest<T>) {

    val loginUsername : EditText = with(test) {
        val id = "loginUsername"
        val loginUsernameEt = activity.findViewByString<EditText>(id)

        loginUsernameEt.assertEditText(
            id = id,
            expectedHint = "username",
            expectedType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME,
            typeString = "textPersonName"
        )
    }

    val loginPassword : EditText = with(test) {
        val id = "loginPassword"
        val view = activity.findViewByString<EditText>(id)

        view.assertEditText(
            id = id,
            expectedHint = "password",
            expectedType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD,
            typeString = "numberPassword"
        )
    }

    val loginButton : Button = with(test) {
            val idString = "loginButton"
            val button = activity.findViewByString<Button>(idString)

            val expectedText = "log in"
            val actualText = button.text.toString().lowercase()
            assertEquals("$idString has wrong text", expectedText, actualText)
            button
    }

    private fun EditText.assertEditText(id: String, expectedHint: String, expectedType: Int, typeString: String): EditText {
        val actualHint = this.hint.toString()
        assertEquals("$id has wrong hint value", expectedHint, actualHint)

        val actualInputType = this.inputType
        assertEquals("$id inputType should be $typeString", expectedType, actualInputType)

        return this
    }

    fun assertLogin(
        caseDescription: String,
        username: String = "Lara",
        password: String = "1234",
        isSucceeded: Boolean = true,
    ) {
        with(test) {
            loginUsername.setText(username)
            loginPassword.setText(password)

            val latestToast: Toast? = ShadowToast.getLatestToast()
            assertNull("There should be no toast messages before loginButton is clicked",
                latestToast)

            loginButton.clickAndRun()

            if(isSucceeded) {
                val message = "Wrong toast message after successful login with $caseDescription"
                assertLastToastMessageEquals(
                    message,
                    "logged in"
                )
            } else {
                val message = "Wrong toast message after unsuccessful login with $caseDescription"
                assertLastToastMessageEquals(
                    message,
                    "invalid credentials"
                )
            }
            ShadowToast.reset()
        }
    }
}