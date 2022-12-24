package org.hyperskill.simplebankmanager

import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.hyperskill.simplebankmanager.internals.screen.LoginScreen
import org.hyperskill.simplebankmanager.internals.screen.UserMenuScreen
import org.hyperskill.simplebankmanager.internals.screen.ViewBalanceScreen
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Stage3UnitTest : SimpleBankManagerUnitTest<MainActivity>(MainActivity::class.java) {

    @Test
    fun test00_checkNavigationOnViewBalanceButtonClick() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()

            }
        }
    }



    @Test
    fun test00_checkViewBalanceFragmentComponents() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()

            }
        }
        ViewBalanceScreen(this)
    }



}
