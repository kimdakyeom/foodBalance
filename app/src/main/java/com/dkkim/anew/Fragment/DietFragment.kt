package com.dkkim.anew.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dkkim.anew.Model.FoodInfo
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

abstract class DietFragment : Fragment(), DietAdapter.OnItemClickListener {
    lateinit var dietAdapter: DietAdapter
    lateinit var binding: FragmentDietBinding
    var bundle = Bundle()

    private lateinit var firebaseAuth: FirebaseAuth

    private var FoodInfo = FoodInfo(null, null, null, null, null, null, null)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDietBinding.inflate(inflater, container, false)

        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")

        var layoutManager = LinearLayoutManager(context)
        var arrayList = arrayList<Any?>()

        val today = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN).format(today)

        binding.dietRecyclerView.apply{
            setHasFixedSize(true)
            setLayoutManager(layoutManager)

            // 아이템사이에 구분선 설정
            addItemDecoration(
                DividerItemDecoration(
                    binding.dietRecyclerView.context,
                    LinearLayoutManager(context).orientation
                )
            )

            // 리니어 레이아웃 형식으로
            layoutManager = LinearLayoutManager(context)
            // 어댑터 설정
            dietRecyclerView.adapter = adapter
        }

        mDatabase.child("UserAccount").child(Firebase.auth.currentUser!!.uid).child(simpleDateFormat).addValueEventListener(object : ValueEventListener {

            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()
                for (snapshotChild in snapshot.children) {

                    val FoodInfo = snapshot.getValue()

                    arrayList.add(FoodInfo)
                }
                dietAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "Failed to read value.", error.toException())
            }
        })

        dietAdapter = DietAdapter(arrayList(FoodInfo))
        dietRecyclerView.adapter
        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }


}