package com.dkkim.anew.Model

data class ResultFoodList(
    val body: Body,
    val header: Header
)

data class Body( // Body에 있는 응답 메시지
    val items: List<Item>,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class Header( // Header에 있는 응답 메시지
    val resultCode: String,
    val resultMsg: String
)


data class Item( // Item 응답 메시지
    val ANIMAL_PLANT: String, // 가공업체
    val BGN_YEAR: String, // 구축년도
    val DESC_KOR: String, // 식품이름
    val NUTR_CONT1: String, // 열량(kcal)
    val NUTR_CONT2: String, // 탄수화물 (g)
    val NUTR_CONT3: String, // 단백질 (g)
    val NUTR_CONT4: String, // 지방 (g)
    val NUTR_CONT5: String, // 당류 (g)
    val NUTR_CONT6: String, // 나트륨 (mg)
    val NUTR_CONT7: String, // 콜레스테롤 (mg)
    val NUTR_CONT8: String, // 포화지방산 (g)
    val NUTR_CONT9: String, // 트랜스지방산 (g)
    val SERVING_WT: String // 1회제공량(g)
)