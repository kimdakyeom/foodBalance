package com.dkkim.anew.Model

import android.widget.TextView

class FoodInfo(
    _food_Name: String?,
    _service_Name: String?,  // 가공업체
    _serving_Weight: Double?,
    _kcal: Double?, // 열량
    _carbo: Double?, // 탄수화물
    _pro: Double?, // 단백질
    _fat: Double?, // 지방
) {
    var food_Name: String? = _food_Name
        get() = field
        set(value) {
            field = value
        }
    var service_Name: String? = _service_Name
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