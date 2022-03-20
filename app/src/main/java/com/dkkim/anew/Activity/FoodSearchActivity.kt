package com.dkkim.anew.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dkkim.anew.Fragment.RetrofitClient
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.Model.ResultFoodList
import com.dkkim.anew.RecyclerView.FoodResultAdapter
import com.dkkim.anew.databinding.ActivityFoodSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodSearchActivity : AppCompatActivity(), FoodResultAdapter.OnItemClickListener {
    lateinit var binding: ActivityFoodSearchBinding

    private val foodCodeDecodingKey =
        "GYbh7D2DFLK834K3R0f009ILwoUOVS2FjkM7JkOVpvbt7iNpeYKdlenp8wf3rEldx3Jt75r8z9zLByTqdJdzCA=="
    private val retrofit = RetrofitClient.create()
    private val listItems = mutableListOf<FoodInfo>() // 읽기/쓰기가 가능한 list
    private val foodResultAdapter = FoodResultAdapter(listItems) // foodResult recycleview를 위한 어댑터

    private var foodName = "" // foodName 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFoodSearchBinding.inflate(layoutInflater) // FoodSearchActivity 바인딩
        setContentView(binding.root)

        binding.recyclerView.apply { // rectcleview 바인딩
            layoutManager = // FoodSearchActivity에 LinearLayout 수평으로 보여주기
                LinearLayoutManager(this@FoodSearchActivity, LinearLayoutManager.VERTICAL, false)
            adapter = foodResultAdapter // foodResultAdapter 붙이기
        }

        foodResultAdapter.setOnItemClickListener(this) // foodResultAdapter에서 setOnItemClickListener

        binding.searchBtn.setOnClickListener { // searchBtn 클릭시
            searchFoodCodeByFoodName(binding.foodEdit.text.toString()) // foodEdit에 입력된 text를 searchFoodCodeByFoodName 함수에 넣고 실행
        }
    }

    private fun addResultItem(items: ResultFoodList?) {
        listItems.clear() // listItems 리스트 초기화
        if (items?.body?.items != null) { // 검색결과가 있으면
            for (item in items.body.items) { // items.body.items 객체를 하나씩 item에 담기
                val i = FoodInfo(
                    item.DESC_KOR,
                    item.SERVING_WT.toDouble(),
                    item.NUTR_CONT1.toDouble(),
                    item.NUTR_CONT2.toDouble(),
                    item.NUTR_CONT3.toDouble(),
                    item.NUTR_CONT4.toDouble()
                )
                listItems.add(i) // listItem에 FoodInfo 차례대로 담기
            }

            Log.d("listitem", listItems.toString()) // listItem에 들어있는 item들 보기

        }
        foodResultAdapter.notifyDataSetChanged() // recyclerView의 리스트를 업데이트
    }

    fun searchFoodCodeByFoodName(foodName: String) {
        // retrofit으로 파라미터 값 전달
        retrofit.getFoodNutriInfo(foodCodeDecodingKey, foodName, null, null, null, null, "json")
            .enqueue(object : // 파라미터 값을 enqueue
                Callback<ResultFoodList> { // 결과값을 Callback으로 넘겨받음
                override fun onResponse( // 통신했을 때 값 돌려받음
                    call: Call<ResultFoodList>, // ResultFoodList 값 요청
                    response: Response<ResultFoodList> // ResultFoodList 값 받음
                ) {
                    Log.d("레트로핏", response.code().toString()) // 레트로핏 응답 성공시 response code 반환

                    addResultItem(response.body()) // body 응답을 addResultItem 함수에 전달
                }

                override fun onFailure(call: Call<ResultFoodList>, t: Throwable) { // 레트로핏 응답 실패시
                    Log.d("레트로핏", "레트로핏실패: ${t.message}") // 실패 메시지 반환
                }
            })
    }

    // Item 클릭시
    override fun onItemClick(view: View, data: FoodInfo, position: Int) {
        val intent = Intent() // 화면 전환
        // 데이터 가지고 intent
        intent.putExtra("food_Name", data.food_Name)
        intent.putExtra("service_Weight", data.serving_Weight)
        intent.putExtra("kcal", data.kcal)
        intent.putExtra("carbo", data.carbo)
        intent.putExtra("pro", data.pro)
        intent.putExtra("fat", data.fat)
        setResult(RESULT_OK, intent) // 호출된 Activity에 결과 돌려주기
        finish() // Activity 종료

    }

}