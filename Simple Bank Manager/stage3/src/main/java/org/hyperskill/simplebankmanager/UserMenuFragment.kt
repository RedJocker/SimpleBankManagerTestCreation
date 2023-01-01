package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.databinding.FragmentUserMenuBinding


class UserMenuFragment : Fragment() {

    lateinit var fragmentUserMenuBinding: FragmentUserMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fragmentUserMenuBinding = FragmentUserMenuBinding.inflate(layoutInflater, container, false)

        val username = arguments?.getString("username") ?: "user"
        val balance = arguments?.getString("balance") ?: "0.0"
        val otherAccountBalance = arguments?.getString("otherAccountBalance") ?: "0.0"
        fragmentUserMenuBinding.userMenuWelcomeTextView.text = "Welcome $username"

        val args = Bundle().apply {
            putString("username", username)
            putString("balance", balance)
            putString("otherAccountBalance", otherAccountBalance)
        }


        fragmentUserMenuBinding.userMenuViewBalanceButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_viewBalanceFragment, args)
        }

        fragmentUserMenuBinding.userMenuTransferFundsButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_userMenuFragment_to_transferFundsFragment,
                args
            )
        }

        arguments?.getString("balance")
        Log.d("UsermenuFragment", "arguments: $arguments")
        return fragmentUserMenuBinding.root
    }


}