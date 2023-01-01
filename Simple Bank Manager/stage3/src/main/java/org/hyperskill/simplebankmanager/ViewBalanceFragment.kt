package org.hyperskill.simplebankmanager

import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.databinding.FragmentViewBalanceBinding


class ViewBalanceFragment : Fragment() {

    lateinit var fragmentViewBalanceBinding: FragmentViewBalanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        fragmentViewBalanceBinding = FragmentViewBalanceBinding.inflate(layoutInflater, container, false)

        val balance = arguments?.getString("balance") ?: "0.0"
        Log.d("ViewBalanceFragment", "arguments: $arguments")

        fragmentViewBalanceBinding.viewBalanceShowBalanceTextView.text = "$balance$"


        return fragmentViewBalanceBinding.root
    }

}