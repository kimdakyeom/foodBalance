package com.dkkim.anew.Model

// 사용자 계정 정보 모델 클래스

class UserAccount(
    _uid: String?,
    _email: String?,
    _name: String?,
    _pwd1: String?,
    _sex: Boolean?,
    _birth: String?,
    _height: String?,
    _weight: String?
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
    var name: String? = _name
        get() = field
        set(value) {
            field = value
        }
    var pwd1: String? = _pwd1
        get() = field
        set(value) {
            field = value
        }
    var sex: Boolean? = _sex
        get() = field
        set(value) {
            field = value
        }
    var birth: String? = _birth
        get() = field
        set(value) {
            field = value
        }
    var height: String? = _height
        get() = field
        set(value) {
            field = value
        }
    var weight: String? = _weight
        get() = field
        set(value) {
            field = value
        }


}