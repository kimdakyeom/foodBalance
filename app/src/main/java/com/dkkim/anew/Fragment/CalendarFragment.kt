package com.dkkim.anew.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dkkim.anew.R
import com.dkkim.anew.databinding.FragmentCalendarBinding
import kotlinx.android.synthetic.main.fragment_calendar.*


class CalendarFragment : Fragment() {
    lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val year = year
            val month = month + 1
            val dayOfMonth = dayOfMonth
            val bundle = Bundle()

            bundle.putInt("year", year)
            bundle.putInt("month", month)
            bundle.putInt("dayOfMonth", dayOfMonth)

            val calendarResultFragment = CalendarResultFragment()
            calendarResultFragment.arguments = bundle

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, calendarResultFragment)
                .addToBackStack(null)
                .commit()
        }

        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root

    }

}


