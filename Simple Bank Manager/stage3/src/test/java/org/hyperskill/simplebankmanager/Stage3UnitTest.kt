package org.hyperskill.simplebankmanager

import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.hyperskill.simplebankmanager.internals.screen.LoginScreen
import org.hyperskill.simplebankmanager.internals.screen.TransferFundsScreen
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
    fun test01_checkViewBalanceFragmentComponents() {
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

    @Test
    fun test02_checkNavigationOnTransferFundsClick() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuTransferFundsButton.clickAndRun()
            }

        }
    }


    @Test
    fun test03_checkForTransferFundsFragmentComponents() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuTransferFundsButton.clickAndRun()
            }
            TransferFundsScreen(this)
        }
    }

    @Test
    fun test04_viewBalanceInitialFunds() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()
            }
            ViewBalanceScreen(this).assertEqualCurrentBalanceCheck("0.0$")
        }
    }

    @Test
    fun test05_emptyAmountTransferErrorToastText() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuTransferFundsButton.clickAndRun()
            }
            TransferFundsScreen(this).apply {
                transferFundsButton.clickAndRun()
                transferFundsButton.assertGetLatestToastText(
                    "transferFundsButton",
                    "has wrong Toast text when there is empty amount",
                    "Enter amount"
                )
            }
        }

    }


    @Test
    fun test06_transferFundsToAccountCheckToastText() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuTransferFundsButton.clickAndRun()
            }
            TransferFundsScreen(this).apply {
                enterTransferAmountEditText.append("100")
                transferFundsButton.clickAndRun().apply {
                    val expectedToast = "100$ transferred to account successfully"
                    assertLastToastMessageEquals(
                        "Wrong Toast text for transfer funds from account",
                        expectedToast
                    )
                }
            }

        }

    }

    @Test
    fun test07_transferToUserAccountSuccessful() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuTransferFundsButton.clickAndRun()
            }
            TransferFundsScreen(this).apply {
                enterTransferAmountEditText.append("100")
                transferFundsButton.clickAndRun()
            }
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()
            }
            ViewBalanceScreen(this).apply {
                assertEqualCurrentBalanceCheck("100.0$")
            }

        }

    }

    @Test
    fun test08_transferFromUserAccountErrorToastCheck() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(caseDescription = "default values")
            }
            UserMenuScreen(this).apply {
                userMenuTransferFundsButton.clickAndRun()
            }
            TransferFundsScreen(this).apply {
                switchChangeTransferAccount.clickAndRun()
                enterTransferAmountEditText.append("100")
                transferFundsButton.clickAndRun().also {
                    val expectedToast = "Not enough balance"
                    val message = "Wrong toast message, when not enough funds at account"
                    assertLastToastMessageEquals(message, expectedToast)
                }
            }

        }

    }

    @Test
    fun test09_transferToAndFromUserAccountSuccessful() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuTransferFundsButton.clickAndRun()
            }
            TransferFundsScreen(this).apply {
                enterTransferAmountEditText.append("430.70")
                transferFundsButton.clickAndRun()
            }
            UserMenuScreen(this).apply {
                userMenuTransferFundsButton.clickAndRun()
            }
            TransferFundsScreen(this).apply {
                switchChangeTransferAccount.clickAndRun()
                enterTransferAmountEditText.append("275.35")
                transferFundsButton.clickAndRun()
            }
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()
            }
            ViewBalanceScreen(this).apply {
                assertEqualCurrentBalanceCheck("155.35$")
            }


        }

    }

}




