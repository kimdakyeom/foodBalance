package com.dkkim.anew.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dkkim.anew.Activity.FoodSearchActivity
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.Model.UserAccount
import com.dkkim.anew.R
import com.dkkim.anew.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_diet.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class MainFragment: Fragment() {
    private val data: Intent? = null
    lateinit var binding: FragmentMainBinding // 프래그먼트 바인딩
    private val retrofit = RetrofitClient.create() // 레트로핏 클라이언트 선언
    private lateinit var firebaseAuth: FirebaseAuth

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

        firebaseAuth = FirebaseAuth.getInstance()

        binding.saveBtn.setOnClickListener {

            putInfo(data)
        }
        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }

    // main activity에서 sub activity를 호출해서 넘어갔다가 다시 main activity로 돌아옴
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    { // data는 intent로 받음
        super.onActivityResult(requestCode, resultCode, data)
        val bundle = Bundle()

        if (requestCode == 200) { // FoodSearchActivity이면
            if (resultCode == Activity.RESULT_OK) { // 음식 검색결과 액티비티에에서 선택한 음식명
                // 받아온 데이터 이름, 1회 제공량, 칼로리, 탄, 단, 지 저장
                var kcal = data?.getDoubleExtra("kcal", 0.0)
                val carbo = data?.getDoubleExtra("carbo", 0.0)
                val pro = data?.getDoubleExtra("pro", 0.0)
                val fat = data?.getDoubleExtra("fat", 0.0)

                Log.d("kcal", kcal.toString())
                Log.d("carbo", carbo.toString())
                Log.d("pro", pro.toString())
                Log.d("fat", fat.toString())

                binding.calG.text = kcal.toString()
                binding.carG.text = carbo.toString()
                binding.proG.text = pro.toString()
                binding.fatG.text = fat.toString()

                super.onActivityResult(requestCode, resultCode, data)

            }
        }
    }

    private fun putInfo(data: Intent?){
        val user: FirebaseUser? = firebaseAuth.currentUser

        val today = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN).format(today)

        val food_Name = data?.getStringExtra("food_Name").toString()
        val service_Name = data?.getStringExtra("service_Name").toString()
        val service_Weight = data?.getDoubleExtra("service_Weight", 0.0)
        val kcal = data?.getDoubleExtra("kcal", 0.0)
        val carbo = data?.getDoubleExtra("carbo", 0.0)
        val pro = data?.getDoubleExtra("pro", 0.0)
        val fat = data?.getDoubleExtra("fat", 0.0)

        val foodInfo = FoodInfo(
            food_Name,
            service_Name,
            service_Weight,
            kcal,
            carbo,
            pro,
            fat
        )

        val db: FirebaseDatabase = FirebaseDatabase.getInstance()
        val reference: DatabaseReference = db.getReference("User")
        reference.child(user!!.uid).child(simpleDateFormat).setValue(foodInfo)
    }
}
