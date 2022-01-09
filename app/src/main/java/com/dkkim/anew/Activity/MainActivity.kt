package com.dkkim.anew.Activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dkkim.anew.Fragment.*
import com.dkkim.anew.R
import com.dkkim.anew.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding // 뷰바인딩


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationBar.setOnItemSelectedListener(this)

        // 기본설정 ( 메인프래그먼트 )
        val bundle = Bundle()
        // bundle.putString("authorization",authorization)
        val fragment = supportFragmentManager.fragmentFactory.instantiate(
            classLoader,
            MainFragment::class.java.name
        )
        replaceFragment(fragment, bundle)



    }

    // bottom navigation bar 클릭 이벤트
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val bundle = Bundle()
//        회원인증정보 프래그먼트에 붙여보내기
//        bundle.putString("authorization", authorization)

        when (item.itemId) {
            R.id.calendar -> {
                val fragment = supportFragmentManager.fragmentFactory.instantiate(
                    classLoader,
                    CalendarFragment::class.java.name
                )
                replaceFragment(fragment, bundle)
            }
            R.id.home -> {
                val fragment = supportFragmentManager.fragmentFactory.instantiate(
                    classLoader,
                    MainFragment::class.java.name
                )
                replaceFragment(fragment, bundle)
            }
            R.id.diet -> {
                val fragment = supportFragmentManager.fragmentFactory.instantiate(
                    classLoader,
                    DietFragment::class.java.name
                )
                replaceFragment(fragment, bundle)
            }
            R.id.setting -> {
                val fragment = supportFragmentManager.fragmentFactory.instantiate(
                    classLoader,
                    SettingFragment::class.java.name
                )
                replaceFragment(fragment, bundle)
            }

        }
        return false
    }

    // 프래그먼트 전환 함수
    private fun replaceFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_frame, fragment)
            .commit()
    }
}