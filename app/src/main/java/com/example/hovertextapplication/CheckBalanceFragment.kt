package com.example.hovertextapplication


import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.hovertextapplication.databinding.FragmentCheckbalanceBinding
import com.hover.sdk.api.HoverParameters


/**
 * A simple [Fragment] subclass.
 */
class CheckBalanceFragment : Fragment() {

    lateinit var binding: FragmentCheckbalanceBinding

    private var bankSelected = ""
    private var accessBankId = "fba3cd13"
    private var accessBankIdComplete  = "8d9d4da8"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_checkbalance,container,false)
        binding.displayPart.visibility = View.GONE
        val bankSpinner: Spinner = binding.bankSpinner


        //Spinner Array of Banks
        ArrayAdapter.createFromResource(
            requireContext(),R.array.bank,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            bankSpinner.adapter = adapter
        }


        //Get All Accounts
        binding.getAccounts.setOnClickListener {
          val selectedItem  = bankSpinner.selectedItem.toString()
            if(selectedItem == "Access Bank"){
                bankSelected = accessBankId
            }
            val i = HoverParameters.Builder(requireContext())
                    .request(bankSelected)
                    .buildIntent()
                startActivityForResult(i, 0)
        }



        //Check Balance
        binding.checkBalance.setOnClickListener {
            val selectedItem  = bankSpinner.selectedItem.toString()
            if(selectedItem == "Access Bank"){
                bankSelected = accessBankIdComplete
            }
            val accountNumberId = binding.accountSelected.text.toString()

            val i = HoverParameters.Builder(requireContext())
                .request(bankSelected)
                .extra("accountSelected",accountNumberId)
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

        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(requireContext(), "Error: " + data?.getStringExtra("error"), Toast.LENGTH_LONG).show()
        }
    }

}
