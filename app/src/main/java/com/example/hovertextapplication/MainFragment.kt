package com.example.hovertextapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hovertextapplication.databinding.ActivityFirstPageBinding

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    lateinit var binding: ActivityFirstPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.activity_first_page,container,false)

        binding.checkbalance.setOnClickListener {
          val action =  MainFragmentDirections.actionMainFragmentToCheckBalanceFragment()
            findNavController().navigate(action)
        }

        binding.recharge.setOnClickListener {
            val action =  MainFragmentDirections.actionMainFragmentToRechargeFragment()
            findNavController().navigate(action)
        }


        binding.sendMoney.setOnClickListener {
            val action =  MainFragmentDirections.actionMainFragmentToSendMoneyFragment()
            findNavController().navigate(action)
        }


        return binding.root

        }


}
