package com.dkkim.anew.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dkkim.anew.R
import com.dkkim.anew.databinding.FragmentBluetoothBinding
import com.dkkim.anew.databinding.FragmentSettingPushBinding

class SettingPushFragment : Fragment() {
    lateinit var binding: FragmentSettingPushBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingPushBinding.inflate(inflater, container, false)


         binding.backBtn.setOnClickListener {
             parentFragmentManager.popBackStack()
         }

        binding.pushSetBtn.setOnClickListener {
            val isChecked = binding.pushSwitch.isChecked // true:알림허용, false:알림비허용
        }

        return binding.root
    }
}