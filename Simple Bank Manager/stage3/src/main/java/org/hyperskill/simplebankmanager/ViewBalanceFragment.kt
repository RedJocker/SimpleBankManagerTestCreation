package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.hyperskill.simplebankmanager.databinding.FragmentViewBalanceBinding


class ViewBalanceFragment : Fragment() {

    lateinit var fragmentViewBalanceBinding: FragmentViewBalanceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        fragmentViewBalanceBinding = FragmentViewBalanceBinding.inflate(layoutInflater, container, false)
        fragmentViewBalanceBinding.viewBalanceShowBalanceTextView.text = "0.0$"

        return fragmentViewBalanceBinding.root
    }
}