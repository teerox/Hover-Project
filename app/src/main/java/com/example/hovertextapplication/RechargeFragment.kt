package com.example.hovertextapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.hovertextapplication.databinding.FragmentRechargeBinding

/**
 * A simple [Fragment] subclass.
 */
class RechargeFragment : Fragment() {

    lateinit var binding: FragmentRechargeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_recharge,container,false)

        return binding.root

    }

}
