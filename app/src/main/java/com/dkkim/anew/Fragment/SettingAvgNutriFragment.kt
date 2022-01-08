package com.dkkim.anew.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dkkim.anew.databinding.FragmentSettingAvgNutriBinding


class SettingAvgNutriFragment : Fragment() {
    lateinit var binding: FragmentSettingAvgNutriBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingAvgNutriBinding.inflate(inflater, container, false)

        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        return binding.root
    }
}