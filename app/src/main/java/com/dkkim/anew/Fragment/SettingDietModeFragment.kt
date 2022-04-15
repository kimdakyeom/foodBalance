package com.dkkim.anew.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dkkim.anew.R
import com.dkkim.anew.Util.MySharedPreferences
import com.dkkim.anew.databinding.FragmentSettingDietModeBinding
import com.dkkim.anew.databinding.FragmentSettingPushBinding

class SettingDietModeFragment : Fragment() {
    lateinit var binding: FragmentSettingDietModeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingDietModeBinding.inflate(inflater, container, false)


        initMode()

        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.dietModeBtn.setOnClickListener {
            when (binding.dietModeRg.checkedRadioButtonId) {
                R.id.minus_mode -> {
                    Toast.makeText(requireContext(), "감량 모드를 선택하셨습니다.", Toast.LENGTH_SHORT).show()
                    MySharedPreferences.setDietMode(requireContext(), "minus")
                }
                R.id.basic_mode -> {
                    Toast.makeText(requireContext(), "유지 모드를 선택하셨습니다.", Toast.LENGTH_SHORT).show()
                    MySharedPreferences.setDietMode(requireContext(), "basic")
                }
                R.id.add_mode -> {
                    Toast.makeText(requireContext(), "증량 모드를 선택하셨습니다.",Toast.LENGTH_SHORT).show()
                    MySharedPreferences.setDietMode(requireContext(), "add")
                }
            }
            parentFragmentManager.popBackStack()
        }


        return binding.root
    }
    fun initMode() {
        when(MySharedPreferences.getDietMode(requireContext())){
            "minus" -> {
                binding.minusMode.isChecked = true
                binding.basicMode.isChecked = false
                binding.addMode.isChecked = false
            }
            "basic" -> {
                binding.minusMode.isChecked = false
                binding.basicMode.isChecked = true
                binding.addMode.isChecked = false
            }
            "add" -> {
                binding.minusMode.isChecked = false
                binding.basicMode.isChecked = false
                binding.addMode.isChecked = true
            }
        }
    }
}