package com.dkkim.anew.Fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dkkim.anew.Activity.FoodSearchActivity
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.Model.UserAccount
import com.dkkim.anew.R
import com.dkkim.anew.RecyclerView.SettingItemAdapter
import com.dkkim.anew.databinding.FragmentDietBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.item_diet.*
import kotlinx.android.synthetic.main.item_diet.view.*
import sun.bob.mcalendarview.utils.CalendarUtil
import sun.bob.mcalendarview.utils.CalendarUtil.date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import com.dkkim.anew.RecyclerView.DietAdapter as DietAdapter
import kotlin.collections.arrayListOf as arrayList


class DietFragment : Fragment() {
    lateinit var mBinding: FragmentDietBinding
    private val binding get() = mBinding!!
    private lateinit var firebaseAuth: FirebaseAuth

    private val dietList = arrayList<FoodInfo>()
    lateinit var dietRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentDietBinding.inflate(inflater, container, false)
        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")

        val dietAdpater = DietAdapter(dietList)

        val today = System.currentTimeMillis()
        val TodayDate = SimpleDateFormat("yyyy-M-d", Locale.KOREAN).format(today)

        binding.btnDate.text = TodayDate

        binding.btnDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                    btn_date.text = "$year-${month + 1}-$dayOfMonth"
                    val date = "$year-${month + 1}-$dayOfMonth"

                    mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(date)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                dietList.clear()
                                for (snapshotChild in snapshot.children) {
                                    val getData = snapshotChild.getValue(FoodInfo::class.java)

                                    dietList.add(getData!!)
                                }
                                dietAdpater.notifyDataSetChanged()
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

        binding.btnLeft.setOnClickListener {
            val day = Calendar.getInstance()
            day.add(Calendar.DATE, -1)
            val beforeDay = SimpleDateFormat("yyyy-M-d", Locale.KOREAN).format(day.time)
            System.out.println(beforeDay)
        }

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

}