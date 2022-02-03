package com.dkkim.anew.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.R
import com.dkkim.anew.RecyclerView.SettingItemAdapter
import com.dkkim.anew.databinding.FragmentDietBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_diet.*
import java.text.SimpleDateFormat
import java.util.*
import com.dkkim.anew.RecyclerView.DietAdapter as DietAdapter
import kotlin.collections.arrayListOf as arrayList

class DietFragment : Fragment() {
    lateinit var mBinding: FragmentDietBinding
    private val binding get() = mBinding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDietBinding.inflate(inflater, container, false)

        val dietList = arrayList(
            FoodInfo("김치", "한성", 34.0, 24.0, 35.0, 36.0, 45.5),
            FoodInfo("된장", "한성", 34.0, 24.0, 35.0, 36.0, 45.5)

        )
        binding.dietRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.dietRecyclerView.setHasFixedSize(true)
        binding.dietRecyclerView.adapter = DietAdapter(dietList)

        return binding.root
    }
}