package org.hyperskill.simplebankmanager

import android.content.Intent
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.hyperskill.simplebankmanager.internals.screen.*
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class Stage5UnitTest : SimpleBankManagerUnitTest<MainActivity>(MainActivity::class.java) {

    private val DIALOG_BILL_TITLE_SUCCESS: String = "Bill info"
    private val DIALOG_BILL_TITLE_ERROR: String = "Error"
    private val DIALOG_BILL_MESSAGE_ERROR_WRONG_CODE: String = "Wrong code"
    private val DIALOG_BILL_MESSAGE_ERROR_NOT_ENOUGH_FUNDS: String = "Not enough funds"

    private val DIALOG_BILL_MESSAGE_ELECTRICITY: String =
        "Name: Electricity\nBillCode: ELEC\nAmount: 45.0"
    private val DIALOG_BILL_MESSAGE_WATER: String = "Name: Water\nBillCode: WTR\nAmount: 25.0"
    private val DIALOG_BILL_MESSAGE_GAS: String = "Name: Gas\nBillCode: GAS\nAmount: 20.0"

    private val BILL_CODE_ELECTRICITY: String = "ELEC"
    private val BILL_CODE_WATER: String = "WTR"
    private val BILL_CODE_GAS: String = "GAS"


    @Test
    fun test00_checkNavigationOnPayBills() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }
            PayBillsScreen(this)
        }
    }

    @Test
    fun test01_checkDialogDataCorrectBillCodeInput() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }

            PayBillsScreen(this).apply {
                inputBillCodeAndClickShowBillInfoButton(
                    billCode = BILL_CODE_ELECTRICITY,
                    expectedDialogTitle = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessage = DIALOG_BILL_MESSAGE_ELECTRICITY
                )
            }
        }
    }

    @Test
    fun test02_checkDialogDataIncorrectBillCode() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }

            PayBillsScreen(this).apply {
                inputBillCodeAndClickShowBillInfoButton(
                    billCode = "phone",
                    expectedDialogTitle = DIALOG_BILL_TITLE_ERROR,
                    expectedDialogMessage = DIALOG_BILL_MESSAGE_ERROR_WRONG_CODE
                )
            }
        }
    }

    @Test
    fun test03_payBillSuccess() {
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
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }

            PayBillsScreen(this).apply {

                inputBillCodeAndClickShowBillInfoButton(
                    billCode = BILL_CODE_WATER,
                    expectedDialogTitle = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessage = DIALOG_BILL_MESSAGE_WATER
                ).acceptBillPayment(
                    "Water", true,
                    expectedDialogTitleDialogHidden = "", expectedDialogMessageDialogHidden = "",
                    expectedDialogTitleDialogVisible = "", expectedDialogMessageDialogVisible = ""
                )

            }
            activity.clickBackAndRun()
            UserMenuScreen(this).apply {
                userMenuViewBalanceButton.clickAndRun()
            }
            ViewBalanceScreen(this).apply {
                assertBalanceAmountDisplay(
                    expectedBalance = "75.00\$",
                    caseDescription = "after payment of bill water"
                )
            }
        }
    }

    @Test
    fun test04_checkDialogDataCorrectBillCodeCancelBillPayment() {
        testActivity {
            LoginScreen(this).apply {
                assertLogin(
                    caseDescription = "default values"
                )
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }

            PayBillsScreen(this).apply {
                inputBillCodeAndClickShowBillInfoButton(
                    billCode = "phone",
                    expectedDialogTitle = DIALOG_BILL_TITLE_ERROR,
                    expectedDialogMessage = DIALOG_BILL_MESSAGE_ERROR_WRONG_CODE
                ).declineBillPayment()
            }
        }
    }

    // 1. test insufficient funds after big transaction
    //    // make sure that after confirm with insufficient funds old dialog is closed and a new one is opened
    @Test
    fun test05_afterTransactionBillPaymentInsufficientFunds() {
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
                transferFundsAmountEditText.setText("90")
                transferFundsAccountEditText.setText("sa9276")
                transferFundsButton.clickAndRun()
            }
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }
            PayBillsScreen(this).apply {
                inputBillCodeAndClickShowBillInfoButton(
                    billCode = BILL_CODE_ELECTRICITY,
                    expectedDialogTitle = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessage = DIALOG_BILL_MESSAGE_ELECTRICITY
                ).acceptBillPayment(
                    billName = "Electricity",
                    successfulPayment = false,
                    expectedDialogTitleDialogHidden = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessageDialogHidden = DIALOG_BILL_MESSAGE_ELECTRICITY,
                    expectedDialogTitleDialogVisible = DIALOG_BILL_TITLE_ERROR,
                    expectedDialogMessageDialogVisible = DIALOG_BILL_MESSAGE_ERROR_NOT_ENOUGH_FUNDS
                )

            }
        }
    }

    // 2. test insufficient funds with custom balance
    // - first bill payment successful, second insufficient funds
    @Test
    fun test06_withCustomBalanceInsufficientFundsAfterSecondBillPayment() {
        val username = "Elaine"
        val password = "9678"

        val args = Intent().apply {
            putExtra("username", username)
            putExtra("password", password)
            putExtra("balance", 55.32)
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
                userMenuPayBillsButton.clickAndRun()
            }
            PayBillsScreen(this).apply {
                inputBillCodeAndClickShowBillInfoButton(
                    billCode = BILL_CODE_WATER,
                    expectedDialogTitle = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessage = DIALOG_BILL_MESSAGE_WATER
                ).acceptBillPayment(
                    billName = "Water",
                    successfulPayment = true,
                    expectedDialogTitleDialogHidden = "",
                    expectedDialogMessageDialogHidden = "",
                    expectedDialogTitleDialogVisible = "",
                    expectedDialogMessageDialogVisible = ""
                )

                inputBillCodeAndClickShowBillInfoButton(
                    billCode = BILL_CODE_ELECTRICITY,
                    expectedDialogTitle = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessage = DIALOG_BILL_MESSAGE_ELECTRICITY
                ).acceptBillPayment(
                    billName = "Electricity",
                    successfulPayment = false,
                    expectedDialogTitleDialogHidden = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessageDialogHidden = DIALOG_BILL_MESSAGE_ELECTRICITY,
                    expectedDialogTitleDialogVisible = DIALOG_BILL_TITLE_ERROR,
                    expectedDialogMessageDialogVisible = DIALOG_BILL_MESSAGE_ERROR_NOT_ENOUGH_FUNDS
                )

            }

        }
    }


    // 3. test valid payment with custom bill
    @Test
    fun test07_customBillSuccessfulPayment() {
        val billInfoMap =
            mapOf(
                "PHONE" to Triple("Mobile phone", "PHONE", 80.0)
            )

        val args = Intent().apply {
            putExtra("billInfo", billInfoMap as java.io.Serializable)
            // TODO default balance is 0.00 if not passed as Intent
            putExtra("balance", 100.00)

        }

        testActivity(arguments = args) {
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
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }
            PayBillsScreen(this).apply {
                inputBillCodeAndClickShowBillInfoButton(
                    billCode = "PHONE",
                    expectedDialogTitle = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessage = "Name: Mobile phone\nBillCode: PHONE\nAmount: 80.0"
                ).acceptBillPayment(
                    "Mobile phone",
                    true,
                    expectedDialogTitleDialogHidden = "", expectedDialogMessageDialogHidden = "",
                    expectedDialogTitleDialogVisible = "", expectedDialogMessageDialogVisible = ""
                )

            }

        }

    }

    // 4. test insufficient funds with custom bill
    @Test
    fun test08_customBillInsufficientBalance() {
        val billInfoMap =
            mapOf(
                "CARINSURANCE" to Triple("Car insurance", "CARINSURANCE", 120.0)
            )

        val args = Intent().apply {
            putExtra("billInfo", billInfoMap as java.io.Serializable)
            // TODO default balance is 0.00 if not passed as Intent
            putExtra("balance", 100.00)

        }
        testActivity(arguments = args) {
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
            UserMenuScreen(this).apply {
                userMenuPayBillsButton.clickAndRun()
            }
            PayBillsScreen(this).apply {
                inputBillCodeAndClickShowBillInfoButton(
                    billCode = "CARINSURANCE",
                    expectedDialogTitle = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessage = "Name: Car insurance\nBillCode: CARINSURANCE\nAmount: 120.0"
                ).acceptBillPayment(
                    "Car insurance",
                    false,
                    expectedDialogTitleDialogHidden = DIALOG_BILL_TITLE_SUCCESS,
                    expectedDialogMessageDialogHidden = "Name: Car insurance\nBillCode: CARINSURANCE\nAmount: 120.0",
                    expectedDialogTitleDialogVisible = DIALOG_BILL_TITLE_ERROR,
                    expectedDialogMessageDialogVisible = DIALOG_BILL_MESSAGE_ERROR_NOT_ENOUGH_FUNDS
                )

            }

        }

    }
}





