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
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.R
import com.dkkim.anew.databinding.FragmentDietBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import com.dkkim.anew.RecyclerView.DietAdapter
import kotlin.collections.arrayListOf as arrayList


class DietFragment : Fragment(), DietAdapter.OnItemClickListener {
    lateinit var mBinding: FragmentDietBinding
    private val binding get() = mBinding!!


    private var dietList = arrayList<FoodInfo>()
    var dietListDate = ""

    private val dietAdapter = DietAdapter(dietList)



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


        binding.dietRecyclerView.apply{
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = dietAdapter
        }
        dietAdapter.setOnItemClickListener(this)



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

                    dietAdapter.notifyDataSetChanged()

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
                    dietListDate = "$year-${month + 1}-$dayOfMonth"
                    Log.d("dietListDate", dietListDate)

                    mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(dietListDate)
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

                                dietAdapter.notifyDataSetChanged()

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


        binding.btnAdd.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, MainFragment())
                .addToBackStack(null)
                .commit()
        }




        return binding.root
    }


    private fun deleteFood(date: String) {
        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")
        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(date)
            .removeValue()
    }

    override fun onItemClick(view: View, data: FoodInfo, position: Int) {
        Log.d("itemClicked","itemClicked")
        PopupMenu(view.context, view, Gravity.END).apply {
            menuInflater.inflate(R.menu.popup, menu)
            setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_delete -> {
//                        deleteFood(binding.btnDate.text as String)
                        Log.d("popup", "아이템 삭제")
                        false
                    }
                    R.id.menu_cancel -> {
                        false
                    }
                    else -> {
                        Log.d("popup", "오류")
                        false
                    }
                }
            })
            show()
        }

    }
}