package com.dkkim.anew.Fragment

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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class SettingUserInfoFragment : Fragment() {

    lateinit var binding: FragmentSettingUserInfoBinding

    // Realtime Database에서 Data를 읽기위해 DatabaseReference 인스턴스 선언
    val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("UserAccount")

    // UserAccount에 uid는 firebase.auth에서 가져옴
    var user = UserAccount(Firebase.auth.uid, null, null, null, null, null, null, null)

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
        val updateUserAccount = mapOf<String, Any>(
            "uid" to user.uid.toString(),
            "email" to user.email.toString(),
            "name" to name,
            "pwd1" to user.pwd1.toString(),
            "sex" to sex,
            "birth" to birth,
            "height" to height,
            "weight" to weight
        )

        // 하위 노드를 모두 업데이트
        reference.child("").child(updateUserAccount["uid"].toString()).updateChildren(updateUserAccount)
    }

    private fun initUserInfo(uid: String) {
        // 기본정보 등록되어있을 시 파이어베이스에서 uid로 유저정보 받아오기 (이름, 이메일)

        reference.get().addOnSuccessListener {
            val map = it.child("").children.iterator().next().value as HashMap<*, *>

            user = UserAccount(
                map["uid"].toString(),
                map["email"].toString(),
                map["name"].toString(),
                map["pwd1"].toString(),
                map["sex"] as Boolean,
                map["birth"].toString(),
                map["height"].toString(),
                map["weight"].toString()
            )
            binding.userName.setText(user.name)
            binding.userEmail.text = user.email

            if (user.sex != null) {
                binding.userSexFemale.isChecked = user.sex!!
                binding.userSexMale.isChecked = !user.sex!!
            }

            binding.userBirthEdit.setText(user.birth)
            binding.userHeightEdit.setText(user.height)
            binding.userWeightEdit.setText(user.weight)

        }


    }

}