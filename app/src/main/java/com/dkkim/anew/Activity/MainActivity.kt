package com.dkkim.anew.Activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dkkim.anew.Fragment.*
import com.dkkim.anew.R
import com.dkkim.anew.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

// 하위클래스 주 생성자에서 상위클래스 생성자 호출
class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener { // AppCompatActivity를 상속받는 MainActivity NavigationBarView를 extends
    lateinit var binding: ActivityMainBinding // 뷰바인딩


    override fun onCreate(savedInstanceState: Bundle?) { // AppCompatActivity 속 onCreate fun
        super.onCreate(savedInstanceState) // AppCompatActivity의 onCreate 호출

        binding = ActivityMainBinding.inflate(layoutInflater) // 레이아웃을 MainActivity에 붙히는 부분
        setContentView(binding.root) // 화면에 뷰 활성

        binding.navigationBar.setOnItemSelectedListener(this) // item을 implement해서 override 함수로 구현

        val bundle = Bundle() // 프래그먼트에 값 전달하는 주머니
        // MainFragment로 변환 선언
        val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader,  MainFragment::class.java.name)

        replaceFragment(fragment, bundle) // 프래그먼트 전환 함수에 MainFragment와 전달할 값 주머니 넣기

    }

    // bottom navigation bar 클릭 이벤트
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val bundle = Bundle() // 프래그먼트에 값 전달하는 주머니

        when (item.itemId) {
            R.id.calendar -> { // 레이아웃에 calendar 선택시
                // CalendarFragment로 변환 선언
                val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, CalendarFragment::class.java.name)
                replaceFragment(fragment, bundle) // 프래그먼트 전환 함수에 MainFragment와 전달할 값 주머니 넣기
            }
            R.id.home -> {
                val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, MainFragment::class.java.name)
                replaceFragment(fragment, bundle) // 프래그먼트 전환 함수에 MainFragment와 전달할 값 주머니 넣기
            }
            R.id.diet -> {
                val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, DietFragment::class.java.name)
                replaceFragment(fragment, bundle) // 프래그먼트 전환 함수에 DietFragment와 전달할 값 주머니 넣기
            }
            R.id.setting -> {
                val fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, SettingFragment::class.java.name)
                replaceFragment(fragment, bundle) // 프래그먼트 전환 함수에 SettingFragment와 전달할 값 주머니 넣기
            }

        }
        return false
    }

    // 프래그먼트 전환 함수에 fragment와 bundle 선언
    private fun replaceFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle // 선언한 프래그먼트에 bundle 붙이기
        supportFragmentManager // activity에서 fragment로 전환시 사용
            .beginTransaction() // 프래그먼트 트랜잭션 시작
            .addToBackStack(null) // 스택에 프래그먼트 쌓기기
           .replace(R.id.main_frame, fragment) // (프래그먼트에 들어갈 프레임 레이아웃 ID, 전환될 프래그먼트)
            .commit() // 실행
    }
}