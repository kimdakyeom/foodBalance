package com.dkkim.anew.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dkkim.anew.Activity.FoodResultActivity
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
    private val retrofit = RetrofitCilent.create()
    private var pageNumber = 0
    private final val size = 20
    private final val serviceKey = "GYbh7D2DFLK834K3R0f009ILwoUOVS2FjkM7JkOVpvbt7iNpeYKdlenp8wf3rEldx3Jt75r8z9zLByTqdJdzCA%3D%3D"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.foodSearchBtn.setOnClickListener {
            var foodName: String = binding.foodNameEdit.text.toString()
            searchByFoodName(foodName) // 음식이름으로 음식코드 조회
        }

        // 식품군류 스피너 세팅
        setSpinnerCategory(resources.getStringArray(R.array.food_categories).toMutableList())
        binding.foodSearchBtn.setOnClickListener {

            // api

            var food_name: String = binding.foodNameEdit.text.toString()
            val urlBuilder =
                StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1") /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8").toString() + "=서비스키") /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("desc_kor", "UTF-8").toString() + "=" + URLEncoder.encode(food_name, "UTF-8")) /*식품이름*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8").toString() + "=" + URLEncoder.encode("1", "UTF-8")) /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8").toString() + "=" + URLEncoder.encode("30", "UTF-8")) /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("bgn_year", "UTF-8").toString() + "=" + URLEncoder.encode("", "UTF-8")) /*구축년도*/
            urlBuilder.append("&" + URLEncoder.encode("animal_plant", "UTF-8").toString() + "=" + URLEncoder.encode("", "UTF-8")) /*가공업체*/
            urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8").toString() + "=" + URLEncoder.encode("json", "UTF-8")) /*응답데이터 형식(xml/json) Default: xml*/

            val url = URL(urlBuilder.toString())
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.setRequestMethod("GET")
            conn.setRequestProperty("Content-type", "application/json")
            System.out.println("Response code: " + conn.getResponseCode())
            val rd: BufferedReader
            if (conn.responseCode >= 200 && conn.responseCode <= 300) {
                rd = BufferedReader(InputStreamReader(conn.getInputStream()))
            } else {
                rd = BufferedReader(InputStreamReader(conn.getErrorStream()))
            }
            val sb = StringBuilder()
            var line: String?
            while (rd.readLine().also { line = it } != null) {
                sb.append(line)
            }
            rd.close()
            conn.disconnect()
            println(sb.toString())
        }
        // 프래그먼트에선 return 문이 코드 마지막에 와야 함
        return binding.root
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
    fun searchByFoodName(food_name: String){
        retrofit.getFoodCode(serviceKey, pageNumber, size, null, food_name).enqueue(object :
            Callback<List<ResultGetFoodCode>> {
            override fun onResponse(
                call: Call<List<ResultGetFoodCode>>,
                response: Response<List<ResultGetFoodCode>>,
            ) {

                if (response.isSuccessful && response.code() == 200 && response.body()?.isEmpty() == false) {
                    // 음식 이름 조회 결과 액티비티로 전환
                    val intent = Intent(requireContext(), FoodResultActivity()::class.java)
                    startActivity(intent)

                } else {
                    // 검색결과 없습니다 토스트 메세지 띄우기
                    Toast.makeText(
                        context, "검색결과가 없습니다.\n다시 시도해주세요", Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<List<ResultGetFoodCode>>, t: Throwable) {
                // 검색결과 없습니다 토스트 메세지 띄우기
                Toast.makeText(
                    context, "검색결과가 없습니다.\n다시 시도해주세요", Toast.LENGTH_SHORT
                ).show()
            }

        })
    }



}