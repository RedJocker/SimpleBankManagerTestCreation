package org.hyperskill.simplebankmanager


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.simplebankmanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), BankManager {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var username: String
    lateinit var password: String
    var balance: Double = 100.0
    lateinit var billInfo: Triple<String, String, Double>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.extras?.getString("username") ?: "Lara"
        password = intent.extras?.getString("password") ?: "1234"
        balance = intent.extras?.getDouble("balance") ?: 100.0

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }

    override fun isValidLogin(username: String, password: String): Boolean {
        return this.username == username
                && this.password == password
    }

    override fun currentBalance(): Double {
        return balance
    }

    override fun hasFunds(amount: Double): Boolean {
        return balance >= amount
    }

    override fun subtractBalance(amount: Double) {
        balance -= amount
    }

    override fun isValidAccount(account: String): Boolean {
        return "^[sc]a\\d{4}$".toRegex().matches(account)
    }

//    override fun onBackPressed() {
//        //comment next line to test incorrect back button behaviour
//        //super.onBackPressed()
//    }

    override fun getBillInfoByCode(code: String): Triple<String, String, Double>? {
        val billInfo = mapOf(
            "elec" to Triple("Electricity", "ELEC", 45.0),
            "gas" to Triple("Gas", "GAS", 20.0),
            "water" to Triple("Water", "WTR", 25.0)
        )
        return if (billInfo[code] != null) billInfo[code] else null
    }
}