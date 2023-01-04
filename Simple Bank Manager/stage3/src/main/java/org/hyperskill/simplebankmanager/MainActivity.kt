package org.hyperskill.simplebankmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.simplebankmanager.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity(), LoginFragment.LoginValidator {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var username: String
    lateinit var password: String
    lateinit var balance: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.extras?.getString("username") ?: "Lara"
        password = intent.extras?.getString("password") ?: "1234"
        balance = intent.extras?.getString("balance") ?: "0.0"
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }

    override fun isValidLogin(username: String, password: String): Boolean {
        return this.username == username
                && this.password == password
    }
}