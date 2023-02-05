package org.hyperskill.simplebankmanager

import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.hyperskill.simplebankmanager.internals.screen.*
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.internal.runners.statements.Fail
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Stage4UnitTest : SimpleBankManagerUnitTest<MainActivity>(MainActivity::class.java) {


    @Test
    fun test00_checkNavigationOnCalculateExchange() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }

            CalculateExchangeScreen(this)
        }
    }


    @Test
    fun test01_convertEURtoGBP() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                calculateExchangeShowConvertedAmount(
                    "5067.0",
                    "eur",
                    "gbp",
                    "4408.29"
                ) // conversion is set to 2 decimal points
            }
        }
    }

    @Test
    fun test02_convertUSDtoEUR() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                calculateExchangeShowConvertedAmount(
                    "3424.0",
                    "usd",
                    "eur",
                    "3424.00"
                ) // conversion is set to 2 decimal points
            }
        }
    }

    @Test
    fun test03_convertGBPtoEUR() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                calculateExchangeShowConvertedAmount(
                    "345.0",
                    "gbp",
                    "eur",
                    "393.30"
                ) // conversion is set to 2 decimal points
            }
        }
    }

    @Test
    fun test04_CheckForErrorSameCurrenciesSelected() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                checkForErrorMessages(isEmptyAmount = true, isSameCurrencySelected = false)
            }
        }
    }

    @Test
    fun test05_CheckForErrorEmptyAmount() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                checkForErrorMessages(isEmptyAmount = false, isSameCurrencySelected = true)
            }
        }
    }

    @Test
    fun test06_CheckIfSameCurrencyCanBeSelected() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuExchangeCalculatorButton.clickAndRun()
            }
            CalculateExchangeScreen(this).apply {
                setSpinnerCurrencySelection("USD", "USD")
                val convertFrom = calculateExchangeDropdownConvertFromSpinner.selectedItem
                val convertTo = calculateExchangeDropdownConvertToSpinner.selectedItem
                    Assert.assertNotEquals("Currencies for" + "\"convert from\"" + " and " +"\"convert to\""
                    + "should not be selected as equal"
                        ,convertFrom,convertTo)
                }
            }

        }
    }







