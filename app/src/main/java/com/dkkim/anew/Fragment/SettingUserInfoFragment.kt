package com.dkkim.anew.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dkkim.anew.databinding.FragmentSettingUserInfoBinding


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
            var isFemale: Boolean = binding.userSexFemale.isChecked // T: 여자, F:남자
            var birth: String = binding.userBirthEdit.text.toString()
            var height: Float = binding.userHeightEdit.text.toString().toFloat()
            var weight: Float = binding.userHeightEdit.text.toString().toFloat()
            updateUserInfo(uid,name,isFemale,birth,height,weight)

        }
        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }

    private fun updateUserInfo(uid: String, name: String, sex: Boolean, birth: String, height: Float, weight: Float) {
        // 유저정보 업데이트 - 파이어베이스에
    }

    private fun initUserInfo(uid: String) {
        // 기본정보 등록되어있을 시 파이어베이스에서 uid로 유저정보 받아오기 (이름, 성별, 생년월일, 신장, 몸무게)
    }

}