package com.dkkim.anew.Model

data class ResultFoodList(
    val body: Body,
    val header: Header
)

data class Body(
    val items: List<Item>,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class Header(
    val resultCode: String,
    val resultMsg: String
)


data class Item(
    val ANIMAL_PLANT: String,
    val BGN_YEAR: String,
    val DESC_KOR: String,
    val NUTR_CONT1: String,
    val NUTR_CONT2: String,
    val NUTR_CONT3: String,
    val NUTR_CONT4: String,
    val NUTR_CONT5: String,
    val NUTR_CONT6: String,
    val NUTR_CONT7: String,
    val NUTR_CONT8: String,
    val NUTR_CONT9: String,
    val SERVING_WT: String
)