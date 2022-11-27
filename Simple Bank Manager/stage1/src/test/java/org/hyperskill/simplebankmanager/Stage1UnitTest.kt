package org.hyperskill.simplebankmanager

import org.hyperskill.simplebankmanager.internals.SimpleBankManagerUnitTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class Stage1UnitTest : SimpleBankManagerUnitTest<MainActivity>(MainActivity::class.java){


    @Test
    fun checkHelloTvInitialState() {
        testActivity {

        }
    }
}