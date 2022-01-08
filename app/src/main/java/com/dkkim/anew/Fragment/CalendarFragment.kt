package com.dkkim.anew.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.dkkim.anew.databinding.FragmentCalendarBinding


class CalendarFragment : Fragment(), CalendarView.OnDateChangeListener {
    lateinit var binding: FragmentCalendarBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)

        binding.calendarView.setOnDateChangeListener(this)


        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        // 날짜에 해당하는 식단 리스트 불러와서 리사이클러뷰에 붙이기
        getDietList(year, month, dayOfMonth)
    }

    private fun getDietList(year: Int, month: Int, dayOfMonth: Int) {
        // 서버에서 날짜에 해당하는 식단 리스트 불러오기
//        val dietList = arrayListOf<>()

//        리사이클러뷰 어댑터에 붙이기
//        setDietList(dietList)
    }
}