package org.hyperskill.simplebankmanager

import android.content.Intent
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
import org.robolectric.shadows.ShadowToast

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

            ViewBalanceScreen(this)
        }
    }


    @Test
    fun test01_checkViewBalanceDefaultValues() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()
            }
            ViewBalanceScreen(this).apply {
                assertBalanceAmountDisplay(
                    expectedBalance = "100.00\$",
                    caseDescription = "with default initial balance values"
                )
                activity.clickBackAndRun()
            }

            try {
                UserMenuScreen(this).apply {
                    userMenuViewBalanceButton.clickAndRun()
                }
            } catch (error: AssertionError) {
                throw AssertionError("After clicking back button on ViewBalance screen UserMenu screen should be displayed")
            }

        }
    }

    @Test
    fun test02_checkViewBalanceCustomValues() {

        val username = "Elaine"
        val password = "9678"

        val args = Intent().apply {
            putExtra("username", username)
            putExtra("password", password)
            putExtra("balance", 30.0)
        }

        testActivity(arguments = args) {
            LoginScreen(this).apply {
                assertLogin(
                    username = username,
                    password = password,
                    caseDescription = "custom values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()
            }
            ViewBalanceScreen(this).apply {
                assertBalanceAmountDisplay(
                    expectedBalance = "30.00\$",
                    caseDescription = "with custom initial balance values"
                )
            }
        }
    }

    @Test
    fun test03_checkNavigationOnTransferFundsClick() {
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
    fun test04_checkEmptyAmountTransferError() {
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
                transferFundsAccountEditText.setText("sa1234")
                transferFundsAmountEditText.setText("")
                transferFundsButton.clickAndRun()
                transferFundsAmountEditText.assertErrorText(
                    errorMessage = "If transferFundsAmountEditText has empty amount it should set error property,",
                    expectedErrorText = "Invalid amount"
                )
            }
        }
    }

    @Test
    fun test05_checkZeroAmountTransferError() {
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
                transferFundsAccountEditText.setText("sa1234")
                transferFundsAmountEditText.setText("0")
                transferFundsButton.clickAndRun()
                transferFundsAmountEditText.assertErrorText(
                    errorMessage = "If transferFundsAmountEditText has zero amount it should set error property,",
                    expectedErrorText = "Invalid amount"
                )
            }
        }
    }

    @Test
    fun test06_checkEmptyAccountTransferError() {
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
                transferFundsAmountEditText.setText("20")
                transferFundsAccountEditText.setText("")
                transferFundsButton.clickAndRun()
                transferFundsAccountEditText.assertErrorText(
                    errorMessage = "If transferFundsAccountEditText is empty it should set error property,",
                    expectedErrorText = "Invalid account number"
                )
            }
        }
    }

    @Test
    fun test07_checkIncorrectAccountTransferError() {
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
                val incorrectAccounts = listOf(
                    "ab1234",
                    "sa999",
                    "sa12345",
                    "ca565",
                    "ca98765",
                    "121234",
                    "saa555",
                    "caa444"
                )
                incorrectAccounts.forEach {
                    transferFundsAmountEditText.setText("20")
                    transferFundsAccountEditText.setText(it)
                    transferFundsButton.clickAndRun()
                    transferFundsAccountEditText.assertErrorText(
                        errorMessage = "If transferFundsAccountEditText is empty it should set error property,",
                        expectedErrorText = "Invalid account number"
                    )
                }
            }
        }
    }

    @Test
    fun test08_checkInsufficientFundsToastWithDefaultBalance() {
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
                val largeAmountsWithoutDecimals = listOf("101", "1000", "54345")
                largeAmountsWithoutDecimals.forEach { largeAmount ->
                    transferFundsAmountEditText.setText(largeAmount)
                    transferFundsAccountEditText.setText("ca1234")
                    transferFundsButton.clickAndRun()
                    assertLastToastMessageEquals(
                        errorMessage = "When the account does not have sufficient funds a toast message is expected",
                        expectedMessage = "Not enough funds to transfer ${largeAmount}.00\$"
                    )
                }
                val largeAmountsWithDecimals = listOf("100.10", "2000.05", "300.54", "200.00")
                largeAmountsWithDecimals.forEach { largeAmount ->
                    transferFundsAmountEditText.setText(largeAmount)
                    transferFundsAccountEditText.setText("sa9276")
                    transferFundsButton.clickAndRun()
                    assertLastToastMessageEquals(
                        errorMessage = "When the account does not have sufficient funds a toast message is expected",
                        expectedMessage = "Not enough funds to transfer ${largeAmount}\$"
                    )
                }
                activity.clickBackAndRun()
            }

            try {
                UserMenuScreen(this).apply {
                    userMenuViewBalanceButton.clickAndRun()
                }
            } catch (error: AssertionError) {
                throw AssertionError("After clicking back button on TransferFunds screen UserMenu screen should be displayed")
            }

            ViewBalanceScreen(this).apply {
                viewBalanceShowBalanceTextView.assertTextWithCustomErrorMessage(
                    errorMessage = "After unsuccessful transfer balance should not change on viewBalanceShowBalanceTextView",
                    expectedText = "100.00\$"
                )
            }
        }
    }

    @Test
    fun test09_checkInsufficientFundsToastWithCustomBalance() {

        val username = "Elaine"
        val password = "9678"

        val args = Intent().apply {
            putExtra("username", username)
            putExtra("password", password)
            putExtra("balance", 200.12)
        }

        testActivity(arguments = args) {
            LoginScreen(this).apply {
                assertLogin(
                    username = username,
                    password = password,
                    caseDescription = "custom values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuTransferFundsButton.clickAndRun()
            }
            TransferFundsScreen(this).apply {
                val largeAmountsWithoutDecimals = listOf("201", "1000", "54345")
                largeAmountsWithoutDecimals.forEach { largeAmount ->
                    transferFundsAmountEditText.setText(largeAmount)
                    transferFundsAccountEditText.setText("ca1234")
                    transferFundsButton.clickAndRun()
                    assertLastToastMessageEquals(
                        errorMessage = "When the account does not have sufficient funds a toast message is expected",
                        expectedMessage = "Not enough funds to transfer ${largeAmount}.00\$"
                    )
                }
                val largeAmountsWithDecimals = listOf("200.15", "2000.05", "300.54", "300.00")
                largeAmountsWithDecimals.forEach { largeAmount ->
                    transferFundsAmountEditText.setText(largeAmount)
                    transferFundsAccountEditText.setText("sa9276")
                    transferFundsButton.clickAndRun()
                    assertLastToastMessageEquals(
                        errorMessage = "When the account does not have sufficient funds a toast message is expected",
                        expectedMessage = "Not enough funds to transfer ${largeAmount}\$"
                    )
                }
                activity.clickBackAndRun()
            }
            try {
                UserMenuScreen(this).apply {
                    userMenuViewBalanceButton.clickAndRun()
                }
            } catch (error: AssertionError) {
                throw AssertionError("After clicking back button on TransferFunds screen UserMenu screen should be displayed")
            }
            ViewBalanceScreen(this).apply {
                viewBalanceShowBalanceTextView.assertTextWithCustomErrorMessage(
                    errorMessage = "After unsuccessful transfer balance should not change on viewBalanceShowBalanceTextView",
                    expectedText = "200.12\$"
                )
            }
        }
    }

    @Test
    fun test10_checkSuccessfulTransferToastMessageAndReturnToUserMenu() {

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
                transferFundsAmountEditText.setText("50.10")
                transferFundsAccountEditText.setText("ca3435")
                transferFundsButton.clickAndRun()
                assertLastToastMessageEquals(
                    errorMessage = "When transfer is successful a toast message is expected",
                    expectedMessage = "Transferred 50.10\$ to account ca3435"
                )
            }
            try {
                UserMenuScreen(this).apply {
                    userMenuViewBalanceButton.clickAndRun()
                }
            } catch (error: AssertionError) {
                throw AssertionError("When transfer is successful user should be automatically redirected to UserMenu screen")
            }

            ViewBalanceScreen(this).apply {
                viewBalanceShowBalanceTextView.assertTextWithCustomErrorMessage(
                    errorMessage = "After successful transfer balance should change on viewBalanceShowBalanceTextView",
                    expectedText = "49.90\$"
                )
            }
        }
    }

    @Test
    fun test11_checkUnsuccessfulTransferAfterSuccessfulTransfer() {
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
                transferFundsAmountEditText.setText("50.10")
                transferFundsAccountEditText.setText("ca3435")
                transferFundsButton.clickAndRun()
                assertLastToastMessageEquals(
                    errorMessage = "When transfer is successful a toast message is expected",
                    expectedMessage = "Transferred 50.10\$ to account ca3435"
                )
            }
            try {
                UserMenuScreen(this).apply {
                    userMenuTransferFundsButton.clickAndRun()
                }
            } catch (error: AssertionError) {
                throw AssertionError("When transfer is successful user should be automatically redirected to UserMenu screen")
            }

            TransferFundsScreen(this).apply {
                ShadowToast.reset()
                transferFundsAmountEditText.setText("50.10")
                transferFundsAccountEditText.setText("ca3435")
                transferFundsButton.clickAndRun()
                assertLastToastMessageEquals(
                    errorMessage = "When transfer is successful a toast message is expected",
                    expectedMessage = "Not enough funds to transfer 50.10\$"
                )
            }
        }
    }
}




