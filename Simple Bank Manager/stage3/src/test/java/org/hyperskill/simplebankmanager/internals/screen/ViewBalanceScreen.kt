package org.hyperskill.simplebankmanager.internals.screen

import android.app.Activity
import android.widget.TextView
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.junit.Assert


class ViewBalanceScreen<T : Activity>(private val test: SimpleBankManagerUnitTest<T>) {

    val viewBalanceTextBalanceTextView: TextView = with(test) {
        activity.findViewByString("viewBalanceTextBalanceTextView")
    }

    val viewBalanceShowBalanceTextView: TextView = with(test) {
        activity.findViewByString("viewBalanceShowBalanceTextView")
    }

    fun assertEqualViewBalanceShowBalanceTextViewHint() {
        val expectedHint = "0.0$"
        val actualHint = viewBalanceShowBalanceTextView.hint
        Assert.assertEquals(expectedHint, actualHint)

    }
    fun assertEqualCurrentBalanceCheck(expectedBalance : String) {
        val actualBalance = viewBalanceShowBalanceTextView.text.toString()
        Assert.assertEquals("Wrong balance at the ViewBalance screen"
            ,expectedBalance,actualBalance)
    }

}