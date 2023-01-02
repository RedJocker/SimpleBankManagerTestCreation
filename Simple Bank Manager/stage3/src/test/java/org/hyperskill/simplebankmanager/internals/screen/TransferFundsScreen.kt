package org.hyperskill.simplebankmanager.internals.screen

import android.app.Activity
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest

class TransferFundsScreen<T : Activity>(private val test : SimpleBankManagerUnitTest<T>) {

    val transferFundsButton : Button = with(test) {
        val idString = "transferFundsButton"
        val expectedText = "transfer"
        activity.findViewByString<Button>(idString).apply {
            assertButtonText(idString, expectedText)
        }
    }
    val enterTransferAmountEditText : EditText = with(test) {
        val idString = "transferFundsEnterAmountEditText"
        val expectedHint = "enter amount"
        activity.findViewByString<EditText>(idString).apply {
            assertHintEditText(idString, expectedHint)
        }
    }
    val transferToTextView : TextView = with(test) {
        val idString = "transferFundsToAccountTextView"
        val expectedText = "transfer to"
        activity.findViewByString<TextView>(idString).apply {
            textViewAssertText(idString, expectedText)
        }
    }
    val transferFromTextView : TextView = with(test) {
        val idString = "transferFundsFromAccountTextView"
        val expectedText = "transfer from"
        activity.findViewByString<TextView>(idString).apply {
            textViewAssertText(idString, expectedText)
        }
    }
    val OtherAccountCardView : CardView = with(test) {
        activity.findViewByString("TransferFundsOtherAccountCardView")
    }
    val OtherAccountCardViewCardAccountNameHint : TextView = with(test) {
        val idString = "transferFundsCardNameOtherAccountTextView"
        val expectedHint = "card account name"
        activity.findViewByString<TextView>(idString).apply {
            assertHintTextView(idString, expectedHint)
        }
    }
    val OtherAccountCardViewCardNumber : TextView = with(test) {
        activity.findViewByString<TextView>("transferFundsCardAccountNumberOtherAccountTextView")
    }
    val userAccountCardView : CardView = with(test) {
        activity.findViewByString("TransferFundsUserAccountCardView")
    }
    val userAccountCardViewCardAccountNameHint: TextView = with(test) {
        val idString = "transferFundsCardNameTextView"
        val expectedHint = "card account name user"
        activity.findViewByString<TextView>(idString).apply {
            assertHintTextView(idString, expectedHint)
        }
    }

    val userAccountCardViewCardAccountNameAsUserAccountLogin: TextView = with(test) {
        val idString = "transferFundsCardNameTextView"
        val expectedText= "lara"
        activity.findViewByString<TextView>(idString).apply {
            textViewAssertText(idString, expectedText)
        }
    }
    val frameLayoutTop : FrameLayout = with(test) {
        activity.findViewByString("frameLayoutTop")
    }
    val frameLayoutBottom : FrameLayout = with(test) {
        activity.findViewByString("frameLayoutBottom")
    }
    val switchChangeTransferAccount : SwitchCompat = with(test) {
        activity.findViewByString("transferFundsSwitch")
    }



}