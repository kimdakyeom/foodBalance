package com.dkkim.anew.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.databinding.FragmentCalendarResultBinding
import com.dkkim.anew.databinding.FragmentDietBinding
import com.dkkim.anew.databinding.FragmentSettingQnaBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_calendar_result.*
import sun.bob.mcalendarview.vo.DateData
import java.text.SimpleDateFormat
import java.util.*

class CalendarResultFragment(var date: DateData) : Fragment() {
    lateinit var binding: FragmentCalendarResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarResultBinding.inflate(inflater, container, false)

        var day = date.day
        var month = date.month

        binding.monthDate.text = "$month/$day"
        Log.d("월일", "$month/$day")


        progress()

        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root

    }

    private fun progress() {

        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")


        val kcalArray = arrayListOf<Double>()
        val carboArray = arrayListOf<Double>()
        val proArray = arrayListOf<Double>()
        val fatArray = arrayListOf<Double>()

        var kcalsum : Double = 0.0
        var carbosum : Double = 0.0
        var prosum : Double = 0.0
        var fatsum : Double = 0.0


        val today = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN).format(today)

        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(simpleDateFormat).addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    kcalArray.clear()
                    for (snapshotChild in snapshot.children) {
                        val sex = snapshotChild.child("sex").getValue().toString()
                        val userAccount = snapshotChild.getValue()
                        val kcal = snapshotChild.child("kcal").getValue().toString().toDouble()
                        val carbo = snapshotChild.child("carbo").getValue().toString().toDouble()
                        val pro = snapshotChild.child("pro").getValue().toString().toDouble()
                        val fat = snapshotChild.child("fat").getValue().toString().toDouble()

                        if(sex == "true") {
                            progress_cal.max = 3468
                            progress_car.max = 434
                            progress_pro.max = 116
                            progress_fat.max = 96
                        }
                        else {
                            progress_cal.max = 4332
                            progress_car.max = 542
                            progress_pro.max = 145
                            progress_fat.max = 120
                        }

                        kcalArray.add(kcal)
                        carboArray.add(carbo)
                        proArray.add(pro)
                        fatArray.add(fat)


                    }

                    for (i in kcalArray.indices) {
                        kcalsum += kcalArray[i]
                    }

                    for (i in carboArray.indices) {
                        carbosum += carboArray[i]
                    }

                    for (i in proArray.indices) {
                        prosum += proArray[i]
                    }

                    for (i in fatArray.indices) {
                        fatsum += fatArray[i]
                    }

                    println(kcalArray)
                    println(kcalsum)
                    println(carboArray)
                    println(carbosum)
                    println(proArray)
                    println(prosum)
                    println(fatArray)
                    println(fatsum)

//                    Log.i("TAG: sex is", sex)
//                    Log.i("TAG: kcal is", kcal)

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("DietFragment", "Failed to read value.", error.toException())
                }
            })
    }

}