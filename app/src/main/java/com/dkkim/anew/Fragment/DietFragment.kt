package com.dkkim.anew.Fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.R
import com.dkkim.anew.databinding.FragmentDietBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_diet.*
import sun.bob.mcalendarview.utils.CalendarUtil
import sun.bob.mcalendarview.utils.CalendarUtil.date
import java.text.SimpleDateFormat
import java.util.*
import com.dkkim.anew.RecyclerView.DietAdapter as DietAdapter
import kotlin.collections.arrayListOf as arrayList


class DietFragment : Fragment() {
    lateinit var mBinding: FragmentDietBinding
    private val binding get() = mBinding!!
    private lateinit var firebaseAuth: FirebaseAuth

    private val dietList = arrayList<FoodInfo>()
    lateinit var dietRecyclerView: RecyclerView

    private var selectedFood: FoodInfo? = null
    private var selectedFoodIndex = -1

    val dietAdpater = DietAdapter(dietList)
    var date: String? = ""

    var myear: Int = 0
    var mmonth: Int = 0
    var mday: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentDietBinding.inflate(inflater, container, false)
        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")

        val today = System.currentTimeMillis()
        val TodayDate = SimpleDateFormat("yyyy-M-d", Locale.KOREAN).format(today)

        val kcalArray = arrayList<Double>()
        var kcalsum: Double = 0.0


        binding.btnDate.text = TodayDate

        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(TodayDate)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    dietList.clear()
                    kcalArray.clear()

                    for (snapshotChild in snapshot.children) {
                        val getData = snapshotChild.getValue(FoodInfo::class.java)
                        val kcal = snapshotChild.child("kcal").getValue().toString().toDouble()

                        dietList.add(getData!!)

                        kcalArray.add(kcal)

                    }

                    for (i in kcalArray.indices) {
                        kcalsum += kcalArray[i]
                    }

                    dietAdpater.notifyDataSetChanged()

                    binding.dietKcal.text = kcalsum.toInt().toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("DietFragment", "Failed to read value.", error.toException())
                }
            })



        binding.btnDate.setOnClickListener {
            val cal = Calendar.getInstance()

            val kcalArray = arrayList<Double>()
            var kcalsum: Double = 0.0

            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    binding.btnDate.text = "$year-${month + 1}-$dayOfMonth"
                    val date = "$year-${month + 1}-$dayOfMonth"

                    myear = year
                    mmonth = month + 1
                    mday = dayOfMonth


                    mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(date)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {

                                dietList.clear()
                                kcalArray.clear()

                                for (snapshotChild in snapshot.children) {
                                    val getData = snapshotChild.getValue(FoodInfo::class.java)
                                    val kcal =
                                        snapshotChild.child("kcal").getValue().toString().toDouble()

                                    dietList.add(getData!!)

                                    kcalArray.add(kcal)

                                }

                                for (i in kcalArray.indices) {
                                    kcalsum += kcalArray[i]
                                }

                                dietAdpater.notifyDataSetChanged()

                                binding.dietKcal.text = kcalsum.toInt().toString()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Log.w("DietFragment", "Failed to read value.", error.toException())
                            }
                        })

                }

            DatePickerDialog(requireContext(),
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()

        }


        Toast.makeText(context, "$myear", Toast.LENGTH_SHORT)
            .show()

        binding.btnAdd.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, MainFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.dietRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.dietRecyclerView.setHasFixedSize(true)
        binding.dietRecyclerView.adapter = dietAdpater


        return binding.root
    }


//    fun deleteFood() {
//        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")
//        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(date)
//            .removeValue()
//
//    }
//
//
//    fun onItemClick(view: View, data: FoodInfo, position: Int) {
//        selectedFood = data
//        selectedFoodIndex = dietList.indexOf(data)
//
//        PopupMenu(view.context, view, Gravity.END).apply {
//            menuInflater.inflate(R.menu.popup, menu)
//            setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
//                when (it.itemId) {
//                    R.id.menu_delete -> {
//                        deleteFood()
//                        Log.d("popup", "아이템 삭제")
//                        false
//                    }
//                    else -> {
//                        Log.d("popup", "오류")
//                        false
//                    }
//                }
//            })
//        }
//    }
}