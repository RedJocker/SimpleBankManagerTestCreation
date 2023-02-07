package org.hyperskill.simplebankmanager

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.hyperskill.simplebankmanager.databinding.FragmentPayBillsBinding
import android.widget.EditText
import android.widget.Button
import android.widget.Toast

class PayBillsFragment : Fragment() {


    private lateinit var binding: FragmentPayBillsBinding
    private lateinit var codeInput: EditText
    private lateinit var showBillInfoButton: Button

    var balanceSetter: BalanceSetter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPayBillsBinding.inflate(layoutInflater, container, false)

        codeInput = binding.codeInputEditText
        showBillInfoButton = binding.showBillInfoButton

        showBillInfoButton.setOnClickListener {
            openBillInfoActivity(codeInput.text.toString())
        }

        return binding.root
    }

    private fun openBillInfoActivity(code: String) {
        val billInfoIntent = Intent(requireContext(), BillInfoActivity::class.java).apply {
            putExtra("code", code)
        }
        startActivityForResult(billInfoIntent, REQUEST_CODE_BILL_INFO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_BILL_INFO && resultCode == RESULT_OK) {
            val billInfo =
                data!!.getSerializableExtra("bill_info") as Triple<*, *, *>
            val billName = billInfo.first
            val billCode = billInfo.second
            val billAmount = billInfo.third
            showConfirmBillInfoDialog(billName as String, billCode as String, billAmount as Double)
        } else if (resultCode == RESULT_CANCELED) {
            showErrorDialog("Code not found")
        }
    }

    private fun showConfirmBillInfoDialog(billName: String, billCode: String, billAmount: Double) {
        AlertDialog.Builder(requireContext())
            .setTitle("Bill Info")
            .setMessage("Name: $billName\n BillCode: $billCode\n Amount: $billAmount")
            .setPositiveButton("Confirm") { _, _ ->
                if (balanceSetter!!.hasFunds(billAmount)) {
                    balanceSetter!!.subtractBalance(billAmount)
                    Toast.makeText(
                        context,
                        "Payment for bill $billName, was successful",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showErrorDialog("Not enough funds")
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
            .show()
        Toast.makeText(
            context,
            "Payment for bill $billName, was successful",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Ok") { _, _ -> }
            .create()
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        balanceSetter = context as? BalanceSetter
    }

    override fun onDetach() {
        super.onDetach()
        balanceSetter = null
    }

    companion object {
        private const val REQUEST_CODE_BILL_INFO = 1
        private const val RESULT_CANCELED = -1
        private const val RESULT_OK = 0
    }


}
