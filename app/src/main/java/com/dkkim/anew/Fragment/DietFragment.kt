package com.dkkim.anew.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dkkim.anew.databinding.FragmentDietBinding

class DietFragment : Fragment() {
    lateinit var binding: FragmentDietBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDietBinding.inflate(inflater, container, false)

        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }
}