package com.dkkim.anew.Model

class FoodInfo(
    var food_Name: String,
    var service_Name: String, // 가공업체
    var serving_Weight: Double?,
    var kcal: Double?, // 열량
    var carbo: Double?, // 탄수화물
    var pro: Double?, // 단백질
    var fat: Double?, // 지방
)