package com.dkkim.anew.Model

import android.widget.TextView

data class FoodInfo(
    val _food_Name: String? = "",
    val _serving_Weight: Double? = 0.0,
    val _kcal: Double? = 0.0, // 열량
    val _carbo: Double? = 0.0, // 탄수화물
    val _pro: Double? = 0.0, // 단백질
    val _fat: Double? = 0.0, // 지방
)
{
    var food_Name: String? = _food_Name
        get() = field
        set(value) {
            field = value
        }

    var serving_Weight: Double? = _serving_Weight
        get() = field
        set(value) {
            field = value
        }
    var kcal: Double? = _kcal
        get() = field
        set(value) {
            field = value
        }
    var carbo: Double? = _carbo
        get() = field
        set(value) {
            field = value
        }
    var pro: Double? = _pro
        get() = field
        set(value) {
            field = value
        }
    var fat: Double? = _fat
        get() = field
        set(value) {
            field = value
        }
}