package com.dkkim.anew.Model

data class ResultFoodCodeList(
    val response: Response
)

data class Response(
    val list: List<FoodInfoItem>,
    val page_No: Int,
    val rcdcnt: Int,
    val result_Code: String,
    val result_Msg: String,
    val total_Count: Int
)

data class FoodInfoItem(
    val fd_Code: String,
    val fd_Grupp_Nm: String,
    val fd_Nm: String,
    val fd_Wgh: String,
    val food_Cnt: String,
    val image_Address: String,
    val no: Int,
    val upper_Fd_Grupp_Nm: String
)