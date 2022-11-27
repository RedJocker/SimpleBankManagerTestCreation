package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.simplebankmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), LoginValidator {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var username: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = savedInstanceState?.getString("username") ?: "Lara"
        password = savedInstanceState?.getString("password") ?: "1234"
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }

    override fun isValidLogin(username: String, password: String): Boolean {
        return this.username == username
                && this.password == password
    }
}