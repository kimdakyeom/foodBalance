package com.dkkim.anew.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dkkim.anew.Activity.FoodSearchActivity
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.R
import com.dkkim.anew.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding // 프래그먼트 바인딩

    private val retrofit = RetrofitClient.create() // 레트로핏 클라이언트 선언

    // 공공데이터 open api 디코딩키 선언
    private val foodNutriDecodingKey =
        "j/xkShPJBtxFbK+ahZ+zy8yx8hTGU36HJbFQ9ZK0/JNRG6yhX41qMmiyl73Z1VSpfFZUiK3DBt31s9qnfHqLEw=="

    override fun onCreateView( // onCreate 후에 화면을 구성할 때 호출
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? // Layout 가져오기
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false) // 레이아웃을 MainFragment에 붙히는 부분


        // 음식이름 검색시 액티비티
        binding.searchBtn.setOnClickListener {
            val foodName = binding.foodEdit.text.toString() // foodEdit에 입력한 string을 foodName에 넣기

            val intent = Intent(requireContext(), FoodSearchActivity()::class.java) // FoodSearchActivity로 화면 전환
            intent.putExtra("foodName", foodName) // foodName에 넣은 값 가지고 intent
            startActivityForResult(intent, 200) // 새 액티비티 열기 + 결과값 전달, requestCode:어떤 activity인지 식별하는 값
        }
        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }


    // main activity에서 sub activity를 호출해서 넘어갔다가 다시 main activity로 돌아옴
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { // data는 intent로 받음
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) { // FoodSearchActivity이면
            if (resultCode == Activity.RESULT_OK) { // 음식 검색결과 액티비티에에서 선택한 음식명

                // 받아온 데이터 이름, 1회 제공량, 칼로리, 탄, 단, 지 저장
                val service_Name = data?.getStringExtra("service_Name").toString()
                val service_weight = data?.getStringExtra("service_weight")?.toInt()
                val kcal = data?.getStringExtra("kcal")?.toDouble()
                val carbo = data?.getStringExtra("carbo")?.toDouble()
                val pro = data?.getStringExtra("pro")?.toDouble()
                val prov = data?.getStringExtra("prov")?.toDouble()
                val sugar = data?.getStringExtra("sugar")?.toDouble()

            }
        }
    }
}