package com.dkkim.anew.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dkkim.anew.Activity.LoginActivity
import com.dkkim.anew.databinding.FragmentCalendarResultBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_calendar_result.*
import java.util.*

class CalendarResultFragment() : Fragment() {
    lateinit var binding: FragmentCalendarResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCalendarResultBinding.inflate(inflater, container, false)

        var year = arguments?.getInt("year")
        var month = arguments?.getInt("month")
        var dayOfMonth = arguments?.getInt("dayOfMonth")

        binding.monthDate.text = "${month}월 ${dayOfMonth}일"


        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")


        val kcalArray = arrayListOf<Double>()
        val carboArray = arrayListOf<Double>()
        val proArray = arrayListOf<Double>()
        val fatArray = arrayListOf<Double>()

        var kcalsum: Double = 0.0
        var carbosum: Double = 0.0
        var prosum: Double = 0.0
        var fatsum: Double = 0.0

        Log.i("TAG: year is", year.toString())
        Log.i("TAG: month is", month.toString())
        val simpleDateFormat = "${year}-${month}-$dayOfMonth"

        Log.i("TAG: date is", simpleDateFormat)

        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(simpleDateFormat)
            .addValueEventListener(
                object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {

                        kcalArray.clear()
                        for (snapshotChild in snapshot.children) {

                            val kcal = snapshotChild.child("kcal").getValue().toString().toDouble()
                            val carbo =
                                snapshotChild.child("carbo").getValue().toString().toDouble()
                            val pro = snapshotChild.child("pro").getValue().toString().toDouble()
                            val fat = snapshotChild.child("fat").getValue().toString().toDouble()

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
                        Log.w("DietFragment2", "Failed to read value.", error.toException())
                    }
                })

        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapshotChild in snapshot.children) {
                        val sex = snapshotChild.child("sex").getValue().toString()

                        Log.i("TAG: sex is", sex)

                        if (sex == "true") {
                            binding.calIntake.text = "3468"
                            binding.carIntake.text = "434"
                            binding.proIntake.text = "116"
                            binding.fatIntake.text = "96"

                            progress_cal.max = 3468
                            progress_car.max = 434
                            progress_pro.max = 116
                            progress_fat.max = 96

                            progress_cal.progress = kcalsum.toInt()
                            progress_car.progress = carbosum.toInt()
                            progress_pro.progress = prosum.toInt()
                            progress_fat.progress = fatsum.toInt()

                            binding.dailyCalIntake.text = kcalsum.toInt().toString()
                            binding.dailyCarIntake.text = carbosum.toInt().toString()
                            binding.dailyProIntake.text = prosum.toInt().toString()
                            binding.dailyFatIntake.text = fatsum.toInt().toString()

                            if (kcalsum.toInt() >= (1734)) {
                                progress_cal.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (carbosum.toInt() >= (217)) {
                                progress_car.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (prosum.toInt() >= (58)) {
                                progress_pro.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (fatsum.toInt() >= (48)) {
                                progress_fat.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            }

                        } else {
                            binding.calIntake.text = "4332"
                            binding.carIntake.text = "542"
                            binding.proIntake.text = "145"
                            binding.fatIntake.text = "120"

                            progress_cal.max = 4332
                            progress_car.max = 542
                            progress_pro.max = 145
                            progress_fat.max = 120

                            progress_cal.progress = kcalsum.toInt()
                            progress_car.progress = carbosum.toInt()
                            progress_pro.progress = prosum.toInt()
                            progress_fat.progress = fatsum.toInt()

                            if (kcalsum.toInt() >= (2166)) {
                                progress_cal.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (carbosum.toInt() >= (271)) {
                                progress_car.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (prosum.toInt() >= (73)) {
                                progress_pro.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (fatsum.toInt() >= (60)) {
                                progress_fat.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            }
                        }

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("DietFragment1", "Failed to read value.", error.toException())
                }

            })

        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

// 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }
}
