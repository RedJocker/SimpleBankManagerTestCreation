package org.hyperskill.simplebankmanager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.hyperskill.simplebankmanager.databinding.FragmentViewBalanceBinding
import java.text.NumberFormat
import java.util.Locale


class ViewBalanceFragment : Fragment() {


    private lateinit var fragmentViewBalanceBinding: FragmentViewBalanceBinding
    private var balanceSupplier: BalanceSupplier? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        fragmentViewBalanceBinding = FragmentViewBalanceBinding.inflate(layoutInflater, container, false)

        val balance = balanceSupplier!!.currentBalance()
        val balanceFormat = NumberFormat.getCurrencyInstance(Locale("en", "US")).format(balance)

        fragmentViewBalanceBinding.viewBalanceAmountTextView.text = balanceFormat
        return fragmentViewBalanceBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        balanceSupplier = context as? BalanceSupplier
    }

    override fun onDetach() {
        super.onDetach()
        balanceSupplier = null
    }
}