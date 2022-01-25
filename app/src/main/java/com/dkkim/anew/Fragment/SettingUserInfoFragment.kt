package com.dkkim.anew.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dkkim.anew.Model.UserAccount
import com.dkkim.anew.databinding.FragmentSettingUserInfoBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class SettingUserInfoFragment : Fragment() {

    lateinit var binding: FragmentSettingUserInfoBinding

    // UserAccount에 uid는 firebase.auth에서 가져옴
    var user = UserAccount(Firebase.auth.currentUser?.uid, null, null, null, null, null, null, null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i("firebase", Firebase.auth.uid.toString()) // firebase.auth의 uid 찍기

        binding = FragmentSettingUserInfoBinding.inflate(inflater, container, false)

        // 저장되어있던 유저정보 불러와 세팅
        initUserInfo(user.uid.toString())

        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 확인버튼 클릭 시 회원정보 수정
        binding.userInfoSetBtn.setOnClickListener {
            // 유저 정보에 저장 - 파이어베이스에
            var name: String = binding.userName.text.toString()
            var sex: Boolean = binding.userSexFemale.isChecked // T: 여자, F:남자
            var birth: String = binding.userBirthEdit.text.toString()
            var height: String = binding.userHeightEdit.text.toString()
            var weight: String = binding.userWeightEdit.text.toString()
            updateUserInfo(name, sex, birth, height, weight)

        }
        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }

    private fun updateUserInfo(
        name: String,
        sex: Boolean,
        birth: String,
        height: String,
        weight: String
    ) {
        val mDatabase = FirebaseDatabase.getInstance().getReference()

        val updateUserAccount = mapOf<String, Any>(
            "name" to name,
            "sex" to sex,
            "birth" to birth,
            "height" to height,
            "weight" to weight
        )

        // 하위 노드를 모두 업데이트
        mDatabase.child("UserAccount").child(user.uid.toString()).child("account").updateChildren(updateUserAccount)
    }

    private fun initUserInfo(uid: String) {
        // 기본정보 등록되어있을 시 파이어베이스에서 uid로 유저정보 받아오기 (이름, 이메일)
        val mDatabase = FirebaseDatabase.getInstance().getReference("UserAccount")
        mDatabase.child(user.uid.toString()).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                for (snapshotChild in snapshot.children) {

                    val user = snapshotChild.getValue()

                    System.out.println(user)

                    val birth = snapshotChild.child("birth").getValue().toString()
                    val email = snapshotChild.child("email").getValue().toString()
                    val height = snapshotChild.child("height").getValue().toString()
                    var name = snapshotChild.child("name").getValue().toString()
                    val pw1 = snapshotChild.child("pw1").getValue().toString()
                    var sex = snapshotChild.child("sex").getValue().toString()
                    val uid = snapshotChild.child("uid").getValue().toString()
                    val weight = snapshotChild.child("weight").getValue().toString()

                    System.out.println(birth)
                    System.out.println(name)

                    binding.userName.text = name
                    binding.userEmail.text = email
                    if (sex == "true") {
                        binding.userSexFemale.isChecked = true
                        binding.userSexMale.isChecked = false
                    } else {
                        binding.userSexMale.isChecked = true
                        binding.userSexFemale.isChecked = false
                    }
                    binding.userBirthEdit.setText(birth)
                    binding.userHeightEdit.setText(height)
                    binding.userWeightEdit.setText(weight)

                }
                //Log.d("Success", "Yas")
            }

            override fun onCancelled(error: DatabaseError) {
                //Log.w("Error", "Failed to read value.", error.toException())
            }
        })
    }
}