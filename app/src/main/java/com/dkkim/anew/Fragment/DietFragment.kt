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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
    val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")

    private val dietList = arrayList<FoodInfo>()
    lateinit var dietRecyclerView: RecyclerView

    val today = System.currentTimeMillis()
    val simpleDateFormat = SimpleDateFormat("yyyy-M-d", Locale.KOREAN).format(today)
    val TodayDate = SimpleDateFormat("yyyy-M-d", Locale.KOREAN).format(today)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        mBinding = FragmentDietBinding.inflate(inflater, container, false)

        val dietAdpater = DietAdapter(dietList)

        val dateText = binding.btnDate.text.toString()

        binding.btnDate.text = TodayDate

        binding.btnDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    btn_date.text = "${year}-${month + 1}-${dayOfMonth}"
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

        binding.dietRecyclerView.btn_delete.setOnClickListener {
            onDeleteContent()
        }

        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(simpleDateFormat)
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

        binding.dietRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.dietRecyclerView.setHasFixedSize(true)
        binding.dietRecyclerView.adapter = dietAdpater


        return binding.root
    }

    private fun onDeleteContent() {
        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(simpleDateFormat).removeValue().addOnSuccessListener {
            Toast.makeText(context, "Successfully delete", Toast.LENGTH_SHORT).show()

        }

    }

}