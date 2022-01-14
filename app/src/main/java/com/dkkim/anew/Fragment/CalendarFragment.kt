package com.dkkim.anew.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.dkkim.anew.R
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
        // 날짜에 해당하는 섭취 식단 리스트
        val bundle = Bundle()
        bundle.putString("month", month.toString())
        bundle.putString("dayOfMonth", dayOfMonth.toString())

        Log.d("월일", "$month/$dayOfMonth")

        val calendarResultfragment = CalendarResultFragment(month, dayOfMonth)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, calendarResultfragment)
            .addToBackStack(null)
            .commit()


    }

    private fun getDietList(year: Int, month: Int, dayOfMonth: Int) {
        // 서버에서 날짜에 해당하는 식단 리스트 불러오기
//        val dietList = arrayListOf<>()

//        리사이클러뷰 어댑터에 붙이기
//        setDietList(dietList)
    }

}