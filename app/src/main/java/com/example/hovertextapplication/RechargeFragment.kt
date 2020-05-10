package com.example.hovertextapplication

import android.app.Activity
import android.content.Context
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
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hovertextapplication.databinding.FragmentRechargeBinding
import com.hover.sdk.api.HoverParameters

/**
 * A simple [Fragment] subclass.
 */
class RechargeFragment : Fragment() {

    lateinit var binding: FragmentRechargeBinding
    private var bankSelected = ""
    private var accessBankId = "fba3cd13"
    private var accessBankIdComplete  = "1b56b350"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recharge,container,false)
        binding.displayPart.visibility = View.GONE


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

        //Recharge
        binding.recharge.setOnClickListener {
            val selectedItem  = binding.bankSpinner.selectedItem.toString()
            if(selectedItem == "Access Bank"){
                bankSelected = accessBankIdComplete
            }
            val accountNumberId = binding.accountSelected.text.toString()
            binding.accountSelected.text
            val amount = binding.account.text.toString()
            val phoneNumber = binding.phoneNumber.text.toString()

            val i = HoverParameters.Builder(requireContext())
                .request(bankSelected)
                .extra("accountId",accountNumberId)
                .extra("amount",amount)
                .extra("phoneNumber",phoneNumber)
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
            binding.displayPart.visibility = View.VISIBLE
            binding.getAccounts.visibility = View.GONE
            binding.accountList.text= check

            if (check != null) {
                if (check.contains("Connection problem")){
                    Toast.makeText(requireContext(),"Request Failed",Toast.LENGTH_LONG).show()
                    val action = RechargeFragmentDirections.actionRechargeFragmentToMainFragment()
                    findNavController().navigate(action)
                }
            }


            Log.e("Messages",check.toString())
        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(requireContext(), "Error: " + data?.getStringExtra("error"), Toast.LENGTH_LONG).show()
        }
    }
//Your request failed


}
