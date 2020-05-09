package com.example.hovertextapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.hovertextapplication.databinding.FragmentSendmoneyBinding

/**
 * A simple [Fragment] subclass.
 */
class SendMoneyFragment : Fragment() {

    lateinit var binding: FragmentSendmoneyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sendmoney,container,false)

        return binding.root

    }

}
