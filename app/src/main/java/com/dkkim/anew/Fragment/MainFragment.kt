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
import com.dkkim.anew.Activity.FoodResultActivity
import com.dkkim.anew.Activity.FoodSearchActivity
import com.dkkim.anew.Model.ResultFoodNutri
import com.dkkim.anew.Model.ResultGetFoodCode
import com.dkkim.anew.R
import com.dkkim.anew.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder


class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding

    private val retrofit = RetrofitClient.create()

    private var selectedFoodCode: String = ""
    private var selectedFoodName: String = ""

    private val foodNutriDecodingKey =
        "j/xkShPJBtxFbK+ahZ+zy8yx8hTGU36HJbFQ9ZK0/JNRG6yhX41qMmiyl73Z1VSpfFZUiK3DBt31s9qnfHqLEw=="

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        // 식품군류 스피너 세팅
        setSpinnerCategory(resources.getStringArray(R.array.food_categories).toMutableList())

        // 음식이름 검색시 액티비티
        binding.searchBtn.setOnClickListener {
            var keyword = binding.foodEdit.text.toString()

            val intent = Intent(requireContext(), FoodSearchActivity()::class.java)
            intent.putExtra("keyword", keyword)
            startActivityForResult(intent, 200)
        }


        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {
                // 음식 검색결과 액티비티엥서 선택한 음식명 코드
                val selectedFoodCode = data?.getStringExtra("selectedFoodCode").toString()
                getFoodNutriByFoodCode(selectedFoodCode)
            }
        }
    }

    private fun getFoodNutriByFoodCode(selectedFoodCode: String) {
        retrofit.getFoodNutriInfo(foodNutriDecodingKey, selectedFoodCode)
            .enqueue(object : Callback<ResultFoodNutri> {
                override fun onResponse(
                    call: Call<ResultFoodNutri>,
                    response: Response<ResultFoodNutri>
                ) {
                    Log.d("레트로핏", selectedFoodCode)
                    Log.d("레트로핏", response.code().toString())
                    binding.resultText.text = response.raw().toString()

                }

                override fun onFailure(call: Call<ResultFoodNutri>, t: Throwable) {
                    Log.d("레트로핏", "레트로핏실패: ${t.message}")
                }

            })
    }

    private fun setSpinnerCategory(
        nameList: MutableList<String>
    ) {
        val spinnerAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    R.layout.support_simple_spinner_dropdown_item,
                    nameList
                )
            }

        binding.foodCateSpinner.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    Toast.makeText(
                        requireContext(),
                        "${nameList[position]}을 선택하였습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(requireContext(), "선택을 취소하였습니다.", Toast.LENGTH_SHORT).show()
                }

            }
        }


    }

}