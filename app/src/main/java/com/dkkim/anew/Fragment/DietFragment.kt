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
import java.text.SimpleDateFormat
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

    var user = FoodInfo(null, null, null, null, null, null, null)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDietBinding.inflate(inflater, container, false)
        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")

        val dietAdpater = DietAdapter(dietList)
        val layout = LinearLayoutManager(context)


        mDatabase.child(Firebase.auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                dietList.clear()
                for (snapshotChild in snapshot.children) {
                    val getData = snapshotChild.child("Account").getValue(FoodInfo::class.java)
                    dietList.add(getData!!)
                }
                dietAdpater.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("DietFragment", "Failed to read value.", error.toException())
            }
        })

        dietRecyclerView.layoutManager = layout
        dietRecyclerView.adapter = dietAdpater
        binding.dietRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.dietRecyclerView.setHasFixedSize(true)


        return binding.root
    }
}