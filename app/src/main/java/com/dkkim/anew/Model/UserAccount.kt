package com.dkkim.anew.Model

// 사용자 계정 정보 모델 클래스

class UserAccount(
    _uid: String?,
    _email: String?,
    _pwd1: String?,
) {
    var uid: String? = _uid
        get() = field
        set(value) {
            field = value
        }
    var email: String? = _email
        get() = field
        set(value) {
            field = value
        }
    var pwd1: String? = _pwd1
        get() = field
        set(value) {
            field = value
        }

}
