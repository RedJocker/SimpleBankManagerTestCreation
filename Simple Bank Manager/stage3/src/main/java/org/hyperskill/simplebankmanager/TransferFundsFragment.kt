package org.hyperskill.simplebankmanager

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.databinding.FragmentTransferFundsBinding
import java.math.BigDecimal


class TransferFundsFragment : Fragment() {

    private lateinit var fragmentTransferFundsBinding: FragmentTransferFundsBinding
    private lateinit var frameLayoutTop: FrameLayout
    private lateinit var frameLayoutBottom: FrameLayout
    private lateinit var topCardView: CardView
    private lateinit var bottomCardView: CardView
    private lateinit var switch: SwitchCompat

    private lateinit var balance: BigDecimal
    private lateinit var otherAccountBalance: BigDecimal
    private var isEnoughBalance: Boolean = false
    private lateinit var inputAmountToTransfer: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTransferFundsBinding =
            FragmentTransferFundsBinding.inflate(layoutInflater, container, false)

        fragmentTransferFundsBinding.transferFundsSwitch
        fragmentTransferFundsBinding.TransferFundsUserAccountCardView.setCardBackgroundColor(Color.RED)

        frameLayoutTop = fragmentTransferFundsBinding.frameLayoutTop
        frameLayoutBottom = fragmentTransferFundsBinding.frameLayoutBottom
        topCardView = fragmentTransferFundsBinding.TransferFundsOtherAccountCardView
        bottomCardView = fragmentTransferFundsBinding.TransferFundsUserAccountCardView
        switch = fragmentTransferFundsBinding.transferFundsSwitch
        val transferFundsAmount = fragmentTransferFundsBinding.transferFundsEnterAmountEditText
        val buttonTransferFunds = fragmentTransferFundsBinding.transferFundsButton

        val bankCardAccountName = fragmentTransferFundsBinding.transferFundsCardNameTextView
        val accountName = arguments?.getString("username") ?: bankCardAccountName.hint
        // append username to bank card name or hint if null
        fragmentTransferFundsBinding.transferFundsCardNameTextView.append(accountName)

        switchReplaceCards(switch)

        balance = (arguments?.getString("balance") ?: "0.0").toBigDecimal()
        otherAccountBalance =
            (arguments?.getString("otherAccountBalance") ?: "0.0").toBigDecimal()
        val args = Bundle()



        buttonTransferFunds.setOnClickListener {
            inputAmountToTransfer = transferFundsAmount.text.toString()

            if (inputAmountToTransfer.isBlank()) {
                isEnoughBalance = false
                Toast.makeText(context, "Enter amount", Toast.LENGTH_SHORT).show()
            } else {
                when (switch.isChecked) {
                    true -> {
                        transferFundsFromAccount()
                    }
                    false -> {
                        transferFundsToAccount()
                    }
                }
                if (isEnoughBalance) {
                    args.apply {
                        putString("username", arguments?.getString("username"))
                        putString("balance", balance.toString())
                        putString("otherAccountBalance", otherAccountBalance.toString())
                    }
                    findNavController().navigate(
                        R.id.action_transferFundsFragment_to_userMenuFragment,
                        args
                    )
                }

            }


        }
        Log.d("TransferFundsFragment", "arguments: $arguments")

        return fragmentTransferFundsBinding.root
    }

    private fun switchReplaceCards(switch: SwitchCompat) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Replace the top CardView with the bottom CardView
                frameLayoutTop.removeView(topCardView)
                if (bottomCardView.parent != null) {
                    val parent = bottomCardView.parent as ViewGroup
                    parent.removeView(bottomCardView)
                }
                frameLayoutTop.addView(bottomCardView)
                frameLayoutBottom.addView(topCardView)
            } else {
                // Replace the bottom CardView with the top CardView
                frameLayoutTop.removeView(bottomCardView)
                if (topCardView.parent != null) {
                    val parent = topCardView.parent as ViewGroup
                    parent.removeView(topCardView)
                }
                frameLayoutTop.addView(topCardView)
                frameLayoutBottom.addView(bottomCardView)
            }
        }
    }

    private fun transferFundsFromAccount() {
        if (balance >= inputAmountToTransfer.toBigDecimal()) {
            isEnoughBalance = true
            balance = balance.subtract(inputAmountToTransfer.toBigDecimal())
            otherAccountBalance =
                otherAccountBalance.add(inputAmountToTransfer.toBigDecimal())
            Toast.makeText(
                context,
                "$inputAmountToTransfer$ transferred from account successfully",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            isEnoughBalance = false
            Toast.makeText(
                context,
                "Not enough balance",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun transferFundsToAccount() {
        if (otherAccountBalance >= inputAmountToTransfer.toBigDecimal()) {
            isEnoughBalance = true
            otherAccountBalance =
                otherAccountBalance.subtract(inputAmountToTransfer.toBigDecimal())
            balance = balance.add(inputAmountToTransfer.toBigDecimal())
            Toast.makeText(
                context,
                "$inputAmountToTransfer$ transferred to account successfully",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            isEnoughBalance = false
            Toast.makeText(
                context,
                "Not enough balance",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}





