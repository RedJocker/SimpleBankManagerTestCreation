package org.hyperskill.simplebankmanager

import android.os.Bundle
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.hyperskill.simplebankmanager.internals.screen.LoginScreen
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Stage1UnitTest : SimpleBankManagerUnitTest<MainActivity>(MainActivity::class.java){


    @Test
    fun test00_checkLoginFragmentHasViews() {
        testActivity {
            LoginScreen(this)
        }
    }

    @Test
    fun test01_checkLoginWithDefaultValuesSucceed() {
        testActivity {
            val loginScreen = LoginScreen(this)
            loginScreen.assertLogin(
                caseDescription = "default values"
            )
        }
    }

    @Test
    fun test02_checkLoginWithDefaultValuesFailWithWrongName() {
        testActivity {
            val loginScreen = LoginScreen(this)
            loginScreen.assertLogin(
                caseDescription = "wrong username for default values",
                username = "John",
                isSucceeded = false
            )
        }
    }

    @Test
    fun test03_checkLoginWithDefaultValuesFailWithWrongPass() {
        testActivity {
            val loginScreen = LoginScreen(this)
            loginScreen.assertLogin(
                caseDescription = "wrong password for default values",
                password = "1111",
                isSucceeded = false
            )
        }
    }

    @Test
    fun test04_checkLoginWithCustomValuesSucceed() {
        val username = "Stella"
        val password = "0000"

        val args = Bundle().apply {
            putString("username", username)
            putString("password", password)
        }

        testActivity(savedInstanceState = args) {
            val loginScreen = LoginScreen(this)
            loginScreen.assertLogin(
                caseDescription = "custom values",
                username = username,
                password = password
            )
        }
    }

    @Test
    fun test05_checkLoginWithCustomValuesFailWithWrongName() {
        val username = "Stella"
        val password = "0000"

        val args = Bundle().apply {
            putString("username", username)
            putString("password", password)
        }

        testActivity(savedInstanceState = args) {
            val loginScreen = LoginScreen(this)
            loginScreen.assertLogin(
                caseDescription = "wrong username for custom values",
                username = "John",
                password = password,
                isSucceeded = false
            )
        }
    }

    @Test
    fun test06_checkLoginWithCustomValuesFailWithWrongPass() {
        val username = "Stella"
        val password = "0000"

        val args = Bundle().apply {
            putString("username", username)
            putString("password", password)
        }

        testActivity(savedInstanceState = args) {
            val loginScreen = LoginScreen(this)
            loginScreen.assertLogin(
                caseDescription = "wrong password for custom values",
                username = username,
                password = "9876",
                isSucceeded = false
            )
        }
    }
}