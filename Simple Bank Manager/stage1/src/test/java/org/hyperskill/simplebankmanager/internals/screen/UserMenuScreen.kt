package org.hyperskill.simplebankmanager.internals.screen

import android.widget.Button
import android.widget.TextView
import org.hyperskill.simplebankmanager.MainActivity
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest

class UserMenuScreen(private val test: SimpleBankManagerUnitTest<MainActivity>) {

    val userMenuWelcomeTextView : TextView = with(test) {
        activity.findViewByString("userMenuWelcomeTextView")
    }
    val userMenuViewBalanceButton : Button = with(test) {
        activity.findViewByString("userMenuViewBalanceButton")
    }
    val userMenuTransferFundsButton : Button = with(test) {
        activity.findViewByString("userMenuTransferFundsButton")
    }
    val userMenuExchangeCalculatorButton : Button = with(test) {
        activity.findViewByString("userMenuExchangeCalculatorButton")
    }
    val userMenuPayBillsButton : Button = with(test) {
        activity.findViewByString("userMenuPayBillsButton")
    }
}