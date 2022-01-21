package com.dkkim.anew.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.dkkim.anew.Activity.FoodSearchActivity
import com.dkkim.anew.R
import com.dkkim.anew.databinding.FragmentCalendarBinding
import kotlinx.android.synthetic.main.fragment_calendar.*
import sun.bob.mcalendarview.MCalendarView
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.vo.DateData


class CalendarFragment : Fragment() {
    lateinit var binding: FragmentCalendarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        binding.calendarView.onDateClick(this)

        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root

    }


    private fun MCalendarView.onDateClick(calendarFragment: Any) {
        binding.calendarView.setOnDateClickListener(object : OnDateClickListener() {
            override fun onDateClick(view: View, date: DateData) {
                val bundle = Bundle()
                var day = date.day
                var month = date.month

                bundle.putString("month", month.toString())
                bundle.putString("day", day.toString())

                Log.d("월일", "$month/$day")

                val calendarResultfragment = CalendarResultFragment(date)

                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame, calendarResultfragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }
}


