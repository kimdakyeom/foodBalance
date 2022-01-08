package com.dkkim.anew.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dkkim.anew.Activity.LoginActivity
import com.dkkim.anew.Activity.MainActivity
import com.dkkim.anew.Model.SettingInfo
import com.dkkim.anew.R
import com.dkkim.anew.RecyclerView.SettingItemAdapter
import com.dkkim.anew.databinding.FragmentSettingBinding
import com.google.firebase.auth.FirebaseAuth


class SettingFragment : Fragment(), SettingItemAdapter.OnItemClickListener {
    lateinit var binding: FragmentSettingBinding
    private var firebaseAuth = FirebaseAuth.getInstance()

    // 설정에 들어갈 메뉴들
    private val settingList = arrayListOf(
        SettingInfo("개인정보 설정"),
        SettingInfo("평균 권장 섭취량"),
        SettingInfo("Q & A"),
        SettingInfo("로그아웃")
    )

    // 리사이클러뷰 어댑터
    private val settingItemAdapter = SettingItemAdapter(settingList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.settingRecyclerView.apply {
            // 아이템사이에 구분선 설정
            addItemDecoration(
                DividerItemDecoration(
                    binding.settingRecyclerView.context,
                    LinearLayoutManager(context).orientation
                )
            )
            // 리니어 레이아웃 형식으로
            layoutManager = LinearLayoutManager(context)
            // 어댑터 설정
            adapter = settingItemAdapter
        }
        // 리사이클러뷰 아이템 클릭 이벤트
        settingItemAdapter.setOnItemClickListener(this)

        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }

    // 각 프래그머트로 변경하는 코드
    override fun onItemClick(view: View, data: SettingInfo, position: Int) {
        when (data.name) {
            "개인정보 설정" -> {
                val bundle = Bundle()
                bundle.putString("uid", "사용자uid")
                replaceFragment(SettingUserInfoFragment(), bundle)
            }
            "평균 권장 섭취량" -> {
                val bundle = Bundle()
                bundle.putString("uid", "사용자uid")
                replaceFragment(SettingAvgNutriFragment(), bundle)
            }
            "Q & A" -> {
                val bundle = Bundle()
                bundle.putString("uid", "사용자uid")
                replaceFragment(SettingQnaFragment(), bundle)
            }
            "로그아웃" -> {
                Logout()
            }


        }
    }

    private fun Logout() {
        // 로그아웃 함수 구현
        firebaseAuth.signOut()
        val intent = Intent(getActivity(), LoginActivity::class.java)
        startActivity(intent)
        Toast.makeText(getActivity(), "로그아웃", Toast.LENGTH_SHORT)
            .show()
    }

    // 프래그먼트 변경 함수 (스택에 쌓이는 -> 뒤로가기 시 전 프래그먼트 뜸)
    private fun replaceFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, fragment)
            .addToBackStack(null)
            .commit()
    }
}