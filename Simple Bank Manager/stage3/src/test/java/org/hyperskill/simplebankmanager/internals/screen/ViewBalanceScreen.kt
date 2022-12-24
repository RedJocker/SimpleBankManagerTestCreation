package org.hyperskill.simplebankmanager.internals.screen

import android.app.Activity
import android.widget.Button
import android.widget.TextView
import org.hyperskill.simplebankmanager.Stage2UnitTest
import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.junit.Assert


class ViewBalanceScreen<T : Activity>(private val test: SimpleBankManagerUnitTest<T>) {

    val viewBalanceTextBalanceTextView: TextView = with(test) {
        activity.findViewByString<TextView>("viewBalanceTextBalanceTextView")
    }

    val viewBalanceShowBalanceTextView: TextView = with(test) {
        activity.findViewByString("viewBalanceShowBalanceTextView")
    }

    fun assertEqualViewBalanceShowBalanceTextViewHint() {
        val expectedHint = "0.0$"
        val acutalHint = viewBalanceShowBalanceTextView.hint
        Assert.assertEquals(expectedHint, acutalHint)

    }

}