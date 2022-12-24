package org.hyperskill.simplebankmanager

import android.os.Bundle
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
        fragmentUserMenuBinding.userMenuWelcomeTextView.text = "Welcome $username"



        fragmentUserMenuBinding.userMenuViewBalanceButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_viewBalanceFragment)
        }

        return fragmentUserMenuBinding.root
    }


}