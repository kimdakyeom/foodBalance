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
import androidx.annotation.RequiresApi
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_diet.*
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

    private lateinit var dateTextView: TextView
    private lateinit var leftImageView: ImageView
    private lateinit var rightImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDietBinding.inflate(inflater, container, false)
        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")

        val dietAdpater = DietAdapter(dietList)

        val today = System.currentTimeMillis()
        val todayDate = SimpleDateFormat("yyyy/M/d", Locale.KOREAN).format(today)

        binding.btnAdd.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, MainFragment())
                .addToBackStack(null)
                .commit()
        }

        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).child(todayDate).addValueEventListener(object : ValueEventListener {

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val today = System.currentTimeMillis()
        val todayDate = SimpleDateFormat("yyyy/M/d", Locale.KOREAN).format(today)
        val cal = Calendar.getInstance()

        dateTextView = view.findViewById(R.id.btn_date)
        leftImageView = view.findViewById(R.id.btn_left)
        rightImageView = view.findViewById(R.id.btn_right)

        var date: Date? = null

        dateTextView.text = todayDate

        class OnClickListener : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onClick(v: View?) {

                var str_getDate: String = dateTextView.text.toString()
                var date: Date? = null

                when (view.id) {
                    R.id.btn_date -> {
                        val dateSetListener = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfmonth ->
                            cal.set(Calendar.YEAR, year)
                            cal.set(Calendar.MONTH, month)
                            cal.set(Calendar.DAY_OF_MONTH, dayOfmonth)

                            val calDateFormat = SimpleDateFormat("yyyy/M/d", Locale.KOREAN)
                            btn_date.text = calDateFormat.format(cal.time)
                        }

                        DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),
                                cal.get(Calendar.DAY_OF_MONTH)).show()
                    }

                    R.id.btn_left -> {
                        // val date = LocalDate.parse(str_getDate, DateTimeFormatter.ISO_DATE)
                        val cal = Calendar.getInstance()
                        val df: DateFormat = SimpleDateFormat("yyyy/M/d")
                        date = df.parse(str_getDate)
                        cal.time = date
                        cal.add(Calendar.DATE, -1)

                        val beforeDay = SimpleDateFormat("yyyy/M/d", Locale.KOREAN)

                        btn_date.text = beforeDay.format(cal.time)
                    }
                }
            }

        }

    }

}