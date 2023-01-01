package org.hyperskill.simplebankmanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.Extensions.showToast
import org.hyperskill.simplebankmanager.databinding.FragmentLoginBinding
import java.math.BigDecimal

class LoginFragment : Fragment() {

    interface LoginValidator {
        fun isValidLogin(username: String, password: String): Boolean
    }

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    var loginValidator: LoginValidator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        fragmentLoginBinding.loginButton.setOnClickListener {
            val username = fragmentLoginBinding.loginUsername.text.toString()
            val password = fragmentLoginBinding.loginPassword.text.toString()
            val balance = "0.0"
            val otherAccountBalance = "1000"

            if(loginValidator?.isValidLogin(username, password) == true) {
                showToast("logged in")
                val args = Bundle().apply {
                    putString("username", username)
                    putString("balance", balance)
                    putString("otherAccountBalance", otherAccountBalance)
                }
                findNavController()
                    .navigate(R.id.action_loginFragment_to_userMenuFragment, args)
            } else {
                showToast("invalid credentials")
            }
        }

        return fragmentLoginBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginValidator = context as? LoginValidator
    }

    override fun onDetach() {
        super.onDetach()
        loginValidator = null
    }
}