package com.dkkim.anew.Model

data class ResultFoodList(
    val resultCode: String, // 결과코드
    val resultMsg: String, // 결과메시지
    val numOfRows: Int, // 한 페이지 결과수
    val pageNo: Int, // 페이지 번호
    val totalCount: Int, // 전체 결과 수
    val items: List<FoodInfoItem>,
)

data class FoodInfoItem(
    val DESC_KOR: String, // 식품이름
    val SERVING_WT: String, // 1회제공량 (g)
    val NUTR_CONT1: String, // 열량 (kcal)
    val NUTR_CONT2: String, // 탄수화물 (g)
    val NUTR_CONT3: String, // 단백질 (g)
    val NUTR_CONT4: String, // 지방 (g)
    val NUTR_CONT5: String, // 당류 (g)
    val NUTR_CONT6: String, // 나트륨 (mg)
    val NUTR_CONT7: String, // 콜레스테롤 (mg)
    val NUTR_CONT8: String, // 포화지방산 (g)
    val NUTR_CONT9: String, // 트랜스지방산 (g)
    val BGN_YEAR: Int, // 구축년도
    val ANIMAL_PLANT: String // 가공업체
)