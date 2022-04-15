package com.dkkim.anew.Fragment

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dkkim.anew.Util.MySharedPreferences
import com.dkkim.anew.databinding.FragmentCalendarResultBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_calendar_result.*

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

                        val dietMode = MySharedPreferences.getDietMode(requireContext())

                        Log.i("TAG: sex is", sex)

                        var max_cal_f = 3468
                        var max_car_f = 434
                        var max_pro_f = 116
                        var max_fat_f = 96

                        var max_cal_m = 4332
                        var max_car_m = 542
                        var max_pro_m = 145
                        var max_fat_m = 120

                        when(dietMode) {
                            "minus" -> {
                                max_cal_f *= 0.8 as Int
                                max_car_f *= 0.8 as Int
                                max_pro_f *= 0.8 as Int
                                max_fat_f *= 0.8 as Int
                                max_cal_m *= 0.8 as Int
                                max_car_m *= 0.8 as Int
                                max_pro_m *= 0.8 as Int
                                max_fat_m *= 0.8 as Int
                            }
                            "add" -> {
                                max_cal_f *= 1.2 as Int
                                max_car_f *= 1.2 as Int
                                max_pro_f *= 1.2 as Int
                                max_fat_f *= 1.2 as Int
                                max_cal_m *= 0.8 as Int
                                max_car_m *= 0.8 as Int
                                max_pro_m *= 0.8 as Int
                                max_fat_m *= 0.8 as Int
                            }
                        }

                        if (sex == "true") {
                            binding.calIntake.text = max_cal_f.toString()
                            binding.carIntake.text = max_car_f.toString()
                            binding.proIntake.text = max_pro_f.toString()
                            binding.fatIntake.text = max_fat_f.toString()



                            progress_cal.max = max_cal_f
                            progress_car.max = max_car_f
                            progress_pro.max = max_pro_f
                            progress_fat.max = max_fat_f

                            progress_cal.progress = kcalsum.toInt()
                            progress_car.progress = carbosum.toInt()
                            progress_pro.progress = prosum.toInt()
                            progress_fat.progress = fatsum.toInt()

                            binding.dailyCalIntake.text = kcalsum.toInt().toString()
                            binding.dailyCarIntake.text = carbosum.toInt().toString()
                            binding.dailyProIntake.text = prosum.toInt().toString()
                            binding.dailyFatIntake.text = fatsum.toInt().toString()

                            if (kcalsum.toInt() >= (max_cal_f/2)) {
                                progress_cal.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (carbosum.toInt() >= (max_car_f/2)) {
                                progress_car.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (prosum.toInt() >= (max_pro_f/2)) {
                                progress_pro.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (fatsum.toInt() >= (max_fat_f/2)) {
                                progress_fat.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            }

                        } else {
                            binding.calIntake.text = max_cal_m.toString()
                            binding.carIntake.text = max_car_m.toString()
                            binding.proIntake.text = max_pro_m.toString()
                            binding.fatIntake.text = max_fat_m.toString()

                            progress_cal.max = max_cal_m
                            progress_car.max = max_car_m
                            progress_pro.max = max_pro_m
                            progress_fat.max = max_fat_m

                            progress_cal.progress = kcalsum.toInt()
                            progress_car.progress = carbosum.toInt()
                            progress_pro.progress = prosum.toInt()
                            progress_fat.progress = fatsum.toInt()

                            if (kcalsum.toInt() >= (max_cal_m/2)) {
                                progress_cal.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (carbosum.toInt() >= (max_car_m/2)) {
                                progress_car.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (prosum.toInt() >= (max_pro_m/2)) {
                                progress_pro.progressTintList =
                                    ColorStateList.valueOf(Color.rgb(248, 72, 72))
                            } else if (fatsum.toInt() >= (max_fat_m/2)) {
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
