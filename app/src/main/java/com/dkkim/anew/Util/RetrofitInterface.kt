package com.dkkim.anew.Util

import com.dkkim.anew.Model.ResultGetFoodCode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitInterface {
    @GET("getKoreanFoodList")
    fun getFoodCode(
        @Query("serviceKey") service_key: String,
        @Query("Page_No") page_no: Int,
        @Query("Page_Size") page_size: Int,
        @Query("food_Group_Code") food_group_code: String? = null,
        @Query("food_Name") food_name: String? = null
    ): Call<List<ResultGetFoodCode>>
}

