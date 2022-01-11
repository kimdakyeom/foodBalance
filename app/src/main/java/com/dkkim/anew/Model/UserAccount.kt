package com.dkkim.anew.Model

// 사용자 계정 정보 모델 클래스

class UserAccount(
    val uid: String?,
    var email: String?,
    var name: String?,
    var pwd1: String?,
    var sex: Boolean?,
    var birth: String?,
    var height: String?,
    var weight: String?
) {}