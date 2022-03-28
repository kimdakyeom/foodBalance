package com.dkkim.anew.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dkkim.anew.databinding.FragmentBluetoothBinding


class BluetoothFragment : Fragment() {
    lateinit var binding: FragmentBluetoothBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBluetoothBinding.inflate(inflater, container, false)

        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        return binding.root
    }
}