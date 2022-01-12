package com.dkkim.anew.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dkkim.anew.Fragment.RetrofitClient
import com.dkkim.anew.Model.FoodInfo
import com.dkkim.anew.Model.FoodInfoItem
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
    private val listItems = mutableListOf<FoodInfo>()
    private val foodResultAdapter = FoodResultAdapter(listItems)

    private var foodName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodName = intent.getStringExtra("foodName").toString()
        binding.foodEdit.setText(foodName)

        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@FoodSearchActivity, LinearLayoutManager.VERTICAL, false)
            adapter = foodResultAdapter
        }

        foodResultAdapter.setOnItemClickListener(this)

        binding.searchBtn.setOnClickListener {
            searchFoodCodeByFoodName(binding.foodEdit.text.toString())
        }

    }

    private fun addResultItem(items: ResultFoodList?) {
        listItems.clear()
        if (items?.items != null) {
            for (item in items.items) {
                val i = FoodInfo(
                    item.DESC_KOR,
                    item.SERVING_WT.toString().toInt(),
                    item.NUTR_CONT1.toString().toDouble(),
                    item.NUTR_CONT2.toString().toDouble(),
                    item.NUTR_CONT3.toString().toDouble(),
                    item.NUTR_CONT4.toString().toDouble(),
                    item.NUTR_CONT5.toString().toDouble()
                )
                listItems.add(i)
            }

            Log.d("listitem", listItems.toString())

        }
        foodResultAdapter.notifyDataSetChanged()
    }

    fun searchFoodCodeByFoodName(foodName: String) {
        retrofit.getFoodNutriInfo(foodCodeDecodingKey, "바나나", null,null,null, null, "json")
            .enqueue(object :
                Callback<ResultFoodList> {
                override fun onResponse(
                    call: Call<ResultFoodList>,
                    response: Response<ResultFoodList>
                ) {
                    Log.d("레트로핏", response.code().toString())

                    addResultItem(response.body())
                }

                override fun onFailure(call: Call<ResultFoodList>, t: Throwable) {
                    Log.d("레트로핏", "레트로핏실패: ${t.message}")
                }
            })
    }

    override fun onItemClick(view: View, data: FoodInfo, position: Int) {
        val intent = Intent()
        intent.putExtra("service_Name", data.food_Name)
        intent.putExtra("service_weight", data.serving_Weight)
        intent.putExtra("kcal", data.kcal)
        intent.putExtra("carbo", data.carbo)
        intent.putExtra("pro", data.pro)
        intent.putExtra("prov", data.prov)
        intent.putExtra("sugar", data.sugar)
        setResult(RESULT_OK, intent)
        finish()
    }

}