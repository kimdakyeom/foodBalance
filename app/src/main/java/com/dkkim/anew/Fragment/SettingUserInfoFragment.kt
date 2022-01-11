package com.dkkim.anew.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dkkim.anew.databinding.FragmentSettingUserInfoBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SettingUserInfoFragment : Fragment() {

    lateinit var binding: FragmentSettingUserInfoBinding
    var uid: String = "" // 로그인한 사용자 uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        binding = FragmentSettingUserInfoBinding.inflate(inflater, container, false)


        // 저장되어있던 유저정보 불러와 세팅
        initUserInfo(uid)


        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // 확인버튼 클릭 시 회원정보 수정
        binding.userInfoSetBtn.setOnClickListener {
            // 유저 정보에 저장 - 파이어베이스에
            var name: String = binding.userNameEdit.text.toString()
            var sex: Boolean = binding.userSexFemale.isChecked // T: 여자, F:남자
            var birth: String = binding.userBirthEdit.text.toString()
            var height: String = binding.userHeightEdit.text.toString()
            var weight: String = binding.userHeightEdit.text.toString()
            updateUserInfo(uid, name, sex, birth, height, weight)

        }
        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }

    private fun updateUserInfo(
        uid: String,
        name: String,
        sex: Boolean,
        birth: String,
        height: String,
        weight: String
    ) {
        // 유저정보 업데이트 - Shared Preferences
    }

    private fun initUserInfo(uid: String) {
        // 기본정보 등록되어있을 시 파이어베이스에서 uid로 유저정보 받아오기 (이름, 이메일)
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email
            val emailVerified = user.isEmailVerified
            val uid = user.uid
            binding.userNameEdit.setText(name)
            binding.userEmail.setText(email)

            if (Firebase.auth.currentUser != null) {
                print(uid)
            }
        }



    }

}