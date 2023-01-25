package org.hyperskill.simplebankmanager


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.simplebankmanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), BankManager {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var username: String
    lateinit var password: String
    lateinit var exchangeMap: Map<String, Map<String, Double>>
    var balance: Double = 100.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.extras?.getString("username") ?: "Lara"
        password = intent.extras?.getString("password") ?: "1234"
        balance = intent.extras?.getDouble("balance") ?: 100.0

        val defaultMap = mapOf(
            "EUR" to mapOf(
                "GBP" to 0.87,
                "USD" to 1.075
            ),
            "GBP" to mapOf(
                "EUR" to 1.14,
                "USD" to 1.14
            ),
            "USD" to mapOf(
                "EUR" to 1.00,
                "GBP" to 0.877
            )
        )

        exchangeMap = intent.extras
            ?.getSerializable("exchangeMap") as? Map<String, Map<String, Double>>
            ?: defaultMap

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

    override fun calculateExchange(from: String, to: String, amount: Double): Double {
        return amount * exchangeMap[from]!![to]!!
    }

//    override fun onBackPressed() {
//        //comment next line to test incorrect back button behaviour
//        //super.onBackPressed()
//    }
}