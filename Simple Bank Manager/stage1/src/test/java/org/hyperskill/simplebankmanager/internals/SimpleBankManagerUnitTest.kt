package org.hyperskill.simplebankmanager.internals

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import org.junit.Assert

open class SimpleBankManagerUnitTest<T : Activity>(clazz: Class<T>) : AbstractUnitTest<T>(clazz) {

    private val helloTv: TextView by lazy {
        //activity.findViewById<TextView>(R.id.someInexistingId) // don't use findViewById, if id does not exist the code won't compile. We want to make as few assumptions as possible from the actual implementation, that is, the implementation is a "black box" for us
        val view = activity.findViewByString<TextView>("helloTv")

        // initialization assertions - if a view should always begin with some specific state better assert it here on initialization since @Test methods may be called in any order. This ensures the view will have the correct initial state for all @Test methods
        val messageInitialText = "The helloTv has a wrong initial text"
        val expectedInitialText = "hello new project"
        val actualInitialText = view.text.toString().lowercase()
        Assert.assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    private val submitBtn: Button by lazy {
        val view = activity.findViewByString<Button>("submitBtn")

        val messageInitialText = "The submitBtn has the wrong text"
        val expectedInitialText = "submit"
        val actualInitialText = view.text.toString().lowercase()
        Assert.assertEquals(messageInitialText, expectedInitialText, actualInitialText)

        view
    }

    private val unexistingView: View by lazy {
        activity.findViewByString("unexistingView")
    }

    private val wrongClassView: ImageButton by lazy {
        activity.findViewByString("submitBtn")
    }

    private val inputEt: EditText by lazy {
        activity.findViewByString("inputEt")
    }

}