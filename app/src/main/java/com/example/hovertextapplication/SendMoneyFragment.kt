package com.example.hovertextapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.hovertextapplication.databinding.FragmentSendmoneyBinding
import com.hover.sdk.api.HoverParameters
import kotlinx.android.synthetic.main.fragment_sendmoney.*

/**
 * A simple [Fragment] subclass.
 */
class SendMoneyFragment : Fragment() {

    lateinit var binding: FragmentSendmoneyBinding
    private var bankSelected = ""
    private var accessBankId = "fba3cd13"
    private var bankToSend  = "aa737a5e"
    private var accessBankIdComplete  = "4038aef3"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sendmoney,container,false)
        binding.displayPart.visibility = View.GONE
        binding.finalPart.visibility = View.GONE

        //Spinner Array of Banks
        ArrayAdapter.createFromResource(
            requireContext(),R.array.bank,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.bankSpinner.adapter = adapter
        }

        //Get All Accounts
        binding.getAccounts.setOnClickListener {
            val selectedItem  = binding.bankSpinner.selectedItem.toString()
            if(selectedItem == "Access Bank"){
                bankSelected = accessBankId
            }
            val i = HoverParameters.Builder(requireContext())
                .request(bankSelected)
                .buildIntent()
            startActivityForResult(i, 0)
        }


        //Get Receiver Account Details
        binding.getAccountsToSend.setOnClickListener {
            val selectedItem  = binding.bankSpinner.selectedItem.toString()
            if(selectedItem == "Access Bank"){
                bankSelected = bankToSend
            }
            val accountNumberId = binding.accountSelected.text.toString()
            binding.accountSelected.text
            val amount = binding.amount.text.toString()
            val accountNumber = binding.accountNumber.text.toString()

            val i = HoverParameters.Builder(requireContext())
                .request(bankSelected)
                .extra("accountId",accountNumberId)
                .extra("amount",amount)
                .extra("NUBAN",accountNumber)
                .buildIntent()
            startActivityForResult(i, 0)
        }


        //Transfer Button Click
        binding.transfer.setOnClickListener {
            val selectedItem  = binding.bankSpinner.selectedItem.toString()
            if(selectedItem == "Access Bank"){
                bankSelected = accessBankIdComplete
            }
            val accountNumberId = binding.accountSelected.text.toString()
            binding.accountSelected.text
            val amount = binding.amount.text.toString()
            val accountNumber = binding.accountNumber.text.toString()
            val accountToSend = binding.accountToSendId.text.toString()

            val i = HoverParameters.Builder(requireContext())
                .request(accessBankIdComplete)
                .extra("accountID",accountNumberId)
                .extra("amount",amount)
                .extra("NUBAN",accountNumber)
                .extra("accountIdToSend",accountToSend)
                .buildIntent()
            startActivityForResult(i, 0)
        }

        return binding.root

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val sessionTextArr = data?.getStringArrayExtra("session_messages")
            val check = sessionTextArr?.get(sessionTextArr.size - 1)
            if (check != null) {
                when {
                    check.contains("Select Bank") -> {
                        // binding.displayPart.visibility = View.VISIBLE
                        binding.finalPart.visibility = View.VISIBLE
                        binding.getAccountsToSend.visibility = View.GONE
                    }
                    check.contains("Invalid account") -> {
                        binding.displayPart.visibility = View.GONE
                        binding.getAccounts.visibility = View.VISIBLE
                        Toast.makeText(requireContext(),"Kindly Reinitiate Transaction again",Toast.LENGTH_LONG).show()
                    }
                    check.contains("Select your") -> {
                        binding.displayPart.visibility = View.VISIBLE
                        binding.getAccounts.visibility = View.GONE
                        Toast.makeText(requireContext(),"Kindly Select an Option",Toast.LENGTH_LONG).show()
                    }
                }

                if (check.contains("Your request failed") ){
                    Toast.makeText(requireContext(),"Request Failed",Toast.LENGTH_LONG).show()
                    val action = SendMoneyFragmentDirections.actionSendMoneyFragmentToMainFragment()
                    findNavController().navigate(action)




                } else if (check.contains("Successful!")){
                    binding.displayPart.visibility = View.GONE
                    binding.finalPart.visibility = View.GONE
                    binding.getAccounts.visibility = View.VISIBLE
                    Toast.makeText(requireContext(),"Transfer Successful",Toast.LENGTH_LONG).show()
                }

            }

            binding.accountList.text= check

            Log.e("Messages",check.toString())


        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(requireContext(), "Error: " + data?.getStringExtra("error"), Toast.LENGTH_LONG).show()
        }
    }
}
