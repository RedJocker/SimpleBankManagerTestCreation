package org.hyperskill.simplebankmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BillInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val code = intent.getStringExtra("code")

        // retrieve bill info based on the code
        val billInfo = code?.let { getBillInfoByCode(it) }
        if (billInfo != null) {
            setResult(RESULT_OK, Intent().apply {
                putExtra("bill_info", billInfo)
            })
        } else {
            setResult(RESULT_CANCELED)
        }
        finish()
    }

    private fun getBillInfoByCode(code: String): Triple<String, String, Double>? {
        val billInfo = mapOf(
            "elec" to Triple("Electricity", "ELEC", 45.0),
            "gas" to Triple("Gas", "GAS", 20.0),
            "water" to Triple("Water", "WTR", 25.0)
        )

        return billInfo[code]
    }

    companion object {
        private const val RESULT_CANCELED = -1
        private const val RESULT_OK = 0
    }
}
